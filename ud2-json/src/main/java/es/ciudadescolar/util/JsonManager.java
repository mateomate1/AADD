package es.ciudadescolar.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.exc.StreamWriteException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import es.ciudadescolar.instituto.Alumno;

public class JsonManager {
    private static final Logger log = LoggerFactory.getLogger(JsonManager.class);

    private static ObjectMapper mapper = new ObjectMapper();

    public static List<Alumno> parseoJsonAlumnos(File ficheroJson) {
        List<Alumno> listaAlumnos = new ArrayList<>();

        if (!ficheroJson.exists()) {
            log.warn("El fichero Json no existe: " + ficheroJson.getPath());
        } else if (!ficheroJson.canRead()) {
            log.warn("El fichero Json no es legible: " + ficheroJson.getPath());
        }

        JsonNode nodoRaiz = null;
        JsonNode nodoAlumnos = null;
        try {
            nodoRaiz = mapper.readTree(ficheroJson);

            if (nodoRaiz.isObject()) {
                log.info("centro: " + nodoRaiz.get("centro").asText());
                log.info("codigo: " + nodoRaiz.get("codigo").asInt());
                log.info("curso: " + nodoRaiz.get("curso").asText());

                nodoAlumnos = nodoRaiz.get("alumnos");
                if (nodoAlumnos.isArray()) {
                    Alumno alumno = null;
                    for (JsonNode al : nodoAlumnos) {
                        alumno = new Alumno();
                        alumno.setExpediente(al.get("expediente").asText());
                        alumno.setNombre(al.get("nombre").asText());
                        alumno.setEdad(Integer.valueOf(al.get("expediente").asInt()));

                        listaAlumnos.add(alumno);
                    }
                }
            }

        } catch (IOException e) {
            log.error("Error durante el parseo del Json: " + e.getMessage());
        }
        return listaAlumnos;
    }

    // Opc 1: creacion dinamica del fichero Json
    public static void crearJsonAlumnos1(List<Alumno> alumnos, File ficheroSalidaJson) {
        ArrayNode arrayAlumnos = mapper.createArrayNode();
        ObjectNode nodoAlumno = null;

        if ((alumnos != null) && (!alumnos.isEmpty())) {
            for (Alumno al : alumnos) {
                nodoAlumno = mapper.createObjectNode();
                nodoAlumno.put("nombre", al.getNombre());
                nodoAlumno.put("expediente", al.getExpediente());
                nodoAlumno.put("edad", al.getEdad().intValue()); // No seria necesario el .intValue()

                arrayAlumnos.add(nodoAlumno);
            }

            try {
                mapper.writerWithDefaultPrettyPrinter().writeValue(ficheroSalidaJson, arrayAlumnos);
            } catch (StreamWriteException e) {

            } catch (DatabindException e) {

            } catch (IOException e) {
                System.out.println("");
            }
        }
    }

    // Opc 2: Serializacion del Jackson
    public static void crearJsonAlumnos2(List<Alumno> alumnos, File ficheroSalidaJson) {
        
    }
}
