package es.ciudadescolar.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import es.ciudadescolar.instituto.Alumno;

public class JsonManager {
    private static final Logger log = LoggerFactory.getLogger(JsonManager.class);

    private static ObjectMapper mapper = new ObjectMapper();

    public static List<Alumno> parseoJsonAlumnos(File ficheroJson) {
        List<Alumno> listaAlumnos = new ArrayList<>();

        if(!ficheroJson.exists()){
            log.warn("El fichero Json no existe: " + ficheroJson.getPath());
        } else if (!ficheroJson.canRead()){
            log.warn("El fichero Json no es legible: " + ficheroJson.getPath());
        } 

        JsonNode nodoRaiz = null;
        try {
            nodoRaiz = mapper.readTree(ficheroJson);

            if(nodoRaiz.isObject()){
                log.info("centro: "+nodoRaiz.get("centro").asText());
            }
            
        } catch (IOException e) {
            log.error("Error durante el parseo del Json: "+e.getMessage());
        }
        return null;
    }
}
