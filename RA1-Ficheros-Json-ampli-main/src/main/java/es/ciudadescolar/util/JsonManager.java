package es.ciudadescolar.util;

import java.io.File;
import java.io.IOException;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.core.exc.StreamWriteException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import es.ciudadescolar.instituto.Alumno;
import es.ciudadescolar.instituto.Instituto;

public class JsonManager {
    private static final Logger LOG = LoggerFactory.getLogger(JsonManager.class);

    private static ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());

    // mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

    private static String patronFecha = "yyyy-MM-dd";
    private static DateTimeFormatter formato = DateTimeFormatter.ofPattern(patronFecha);

    public static List<Alumno> parsearJsonAlumnos(File ficheroJson) {
        List<Alumno> listaAlumnos = new ArrayList<Alumno>();

        if (!ficheroJson.exists()) {
            LOG.warn("El fichero Json no existe: " + ficheroJson.getAbsolutePath());
        }
        if (!ficheroJson.canRead()) {
            LOG.warn("El fichero Json no es legible: " + ficheroJson.getAbsolutePath());
        }

        JsonNode nodoRaiz = null;
        JsonNode nodoAlumnos = null;

        try {
            nodoRaiz = mapper.readTree(ficheroJson);

            if (nodoRaiz.isObject()) {
                LOG.info("centro: " + nodoRaiz.get("centro").asText());
                LOG.info("codigo: " + nodoRaiz.get("codigo").asInt());
                LOG.info("curso: " + nodoRaiz.get("curso").asText());

                nodoAlumnos = nodoRaiz.get("alumnos");
                if (nodoAlumnos.isArray()) {
                    Alumno alumno = null;
                    for (JsonNode al : nodoAlumnos) {
                        alumno = new Alumno();
                        alumno.setExpediente(al.get("expediente").asText());
                        alumno.setEdad(Integer.valueOf(al.get("edad").asInt()));
                        alumno.setNombre(al.get("nombre").asText());
                        LocalDate fechaNac = LocalDate.parse(al.get("fecha").asText(), formato);
                        alumno.setFecha_nacimiento(fechaNac);

                        listaAlumnos.add(alumno);
                    }
                }
            }
        } catch (IOException e) {
            LOG.error("Error durante el parseo del Json:" + e.getMessage());
        } catch (DateTimeParseException e) {
            LOG.error("Error durante el parseo del Json. Formato de fecha inesperado: " + e.getMessage() + " Se esperaba: " + patronFecha);
        }

        return listaAlumnos;

    }

    public static List<Alumno> parsearJsonAlumnos2(File ficheroJson) {
        List<Alumno> listaAlumnos = new ArrayList<Alumno>();

        if (!ficheroJson.exists()) {
            LOG.warn("El fichero Json no existe: " + ficheroJson.getAbsolutePath());
        }
        if (!ficheroJson.canRead()) {
            LOG.warn("El fichero Json no es legible: " + ficheroJson.getAbsolutePath());
        }

        try {
            listaAlumnos = mapper.readValue(ficheroJson, new TypeReference<List<Alumno>>() {});
        } catch (StreamReadException e) {
            LOG.error("Error durante el parseo del Json delegado en Jackson:" + e.getMessage());
        } catch (DatabindException e) {
            LOG.error("Error durante el parseo del Json delegado en Jackson:" + e.getMessage());
        } catch (IOException e) {
            LOG.error("Error durante el parseo del Json delegado en Jackson:" + e.getMessage());
        }
        ;

        return listaAlumnos;

    }

    public static Instituto parsearJsonInstituto(File ficheroJson) {
        if (!ficheroJson.exists()) {
            LOG.warn("El fichero Json no existe: " + ficheroJson.getAbsolutePath());
        }
        if (!ficheroJson.canRead()) {
            LOG.warn("El fichero Json no es legible: " + ficheroJson.getAbsolutePath());
        }
        Instituto instituto = null;
        try {
            instituto = mapper.readValue(ficheroJson, Instituto.class);
        } catch (StreamReadException e) {
            LOG.error("Error durante el parseo del Json delegado en Jackson:" + e.getMessage());
        } catch (DatabindException e) {
            LOG.error("Error durante el parseo del Json delegado en Jackson:" + e.getMessage());
        } catch (IOException e) {
            LOG.error("Error durante el parseo del Json delegado en Jackson:" + e.getMessage());
        }
        ;

        return instituto;

    }

    /**
     * Método para volcar "dinámicamente" una colección de instancias de la clase
     * Alumno en un fichero Json
     *
     * @param alumnos
     * @param ficheroSalidaJson
     */
    public static void crearJsonAlumnos(List<Alumno> alumnos, File ficheroSalidaJson) {

        ArrayNode arrayAlumnos = mapper.createArrayNode();

        ObjectNode nodoAlumno = null;

        if ((alumnos != null) && (!alumnos.isEmpty())) {
            for (Alumno al : alumnos) {
                nodoAlumno = mapper.createObjectNode();
                nodoAlumno.put("nombre", al.getNombre());
                nodoAlumno.put("expediente", al.getExpediente());
                nodoAlumno.put("edad", al.getEdad().intValue());
                nodoAlumno.put("fechaNacimiento", al.getFecha_nacimiento().format(formato));

                arrayAlumnos.add(nodoAlumno);
            }

            try {
                mapper.writerWithDefaultPrettyPrinter().writeValue(ficheroSalidaJson, arrayAlumnos);
            } catch (StreamWriteException e) {
                LOG.error("Error durante la escritura JSON: " + e.getMessage());
            } catch (DatabindException e) {
                LOG.error("Error durante la escritura JSON: " + e.getMessage());
            } catch (IOException e) {
                LOG.error("Error durante la escritura JSON: " + e.getMessage());
            } catch (DateTimeException e) {
                LOG.error("Error durante la escritura JSON: " + e.getMessage());
            }

        }
    }

    /**
     * Método para delegar en Jackson la serialización de una colección de
     * instancias de la clase Alumno en un fichero Json
     *
     * @param alumnos
     * @param ficheroSalidaJson
     */
    public static void crearJsonAlumnos2(List<Alumno> alumnos, File ficheroSalidaJson) {
        try {
            mapper.writerWithDefaultPrettyPrinter().writeValue(ficheroSalidaJson, alumnos);
        } catch (StreamWriteException e) {
            LOG.error("Error durante la escritura JSON: " + e.getMessage());
        } catch (DatabindException e) {
            LOG.error("Error durante la escritura JSON: " + e.getMessage());
        } catch (IOException e) {
            LOG.error("Error durante la escritura JSON: " + e.getMessage());
        }
    }
}