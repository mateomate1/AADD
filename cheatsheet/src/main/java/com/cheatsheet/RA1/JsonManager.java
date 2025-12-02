package com.cheatsheet.RA1;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.exc.StreamWriteException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.ValueNode;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class JsonManager {
    private final Logger log = LoggerFactory.getLogger(JsonManager.class);

    private File ficheroEntrada;
    private File ficheroSalida;

    private String patronFechaOut = "yyyy-MM-dd";
    private String patronFechaIn = "dd/MM/yyyy";
    private DateTimeFormatter formatoOut = DateTimeFormatter.ofPattern(patronFechaOut);
    private DateTimeFormatter formatoIn = DateTimeFormatter.ofPattern(patronFechaIn);

    public JsonManager() {
    }

    public void setFicheroEntrada(File ficheroEntrada) {
        this.ficheroEntrada = ficheroEntrada;
        log.trace("Gestor de json inicializado con exito");
    }

    public void setFicheroEntrada(String ficheroEntrada) {
        this.ficheroEntrada = new File(ficheroEntrada);
        log.trace("Gestor de json inicializado con exito");
    }

    public void setFicheroSalida(File ficheroSalida) {
        this.ficheroSalida = ficheroSalida;
        log.trace("Gestor de json inicializado con exito");
    }

    public void setFicheroSalida(String ficheroSalida) {
        this.ficheroSalida = new File(ficheroSalida);
        log.trace("Gestor de json inicializado con exito");
    }

    public void parsearJson() {
        // Para recuperar fechas es necesario usar .registerModule(new
        // JavaTimeModule()), aunque se puede recoger como texto y parsear luego
        ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());
        JsonNode nodoRaiz = null;
        try {
            // Se inicializa el nodoRaiz leyendo el archivo/texto/byte[]/etc que contenga
            // codigo en json con ObjectMapper.readTree(archivo)
            nodoRaiz = mapper.readTree(ficheroEntrada);

            JsonNode nodo; // Pueden ser ValueNode, ArrayNode o ObjectNode

            // Estos son los subtipos del JsonNode
            ValueNode nodoValor; // Valores de un objeto Json, usualmente no se usa porque se puede recuperar
                                 // directamente como JsonNode el valor
            ArrayNode nodoArray; // Arrays Json, se abren y cierran con [], contiene ObjectNode o ValueNode
            ObjectNode nodoObjeto; // Objetos Json, se abren y cierran con {}, contiene ValueNode o ArrayNode

            // El metodo get solo devuelve JsonNode, que puede ser o no un ValueNode,
            // ArrayNode o ObjectNode, habra que castearlo
            nodo = nodoRaiz.get("Nombre nodo/clave"); // Recupera el valor/nodo asociado a la clave/nombre
            nodo = nodoRaiz.get(0); // Recupera el valor/nodo de la posicion indicada

            // Metodos booleanos para saber el contenido del JsonNode, existen mas pero
            // estos son los mas comunes
            nodo.isNull();
            nodo.isArray();
            nodo.isObject();
            nodo.isValueNode();
            nodo.isContainerNode(); // Devuelve si el nodo es un contenedor de nodos(es decir Array u Objeto)
            nodo.isTextual();
            nodo.isNumber();
            nodo.isBoolean();

            JsonNode subnodo = nodoRaiz.get("Clave");
            if (subnodo.isNull()) {
                log.warn("Este nodo estaria vacio"); // Si la clave no existe el nodo podria ser null, aunq tmb si el
                                                     // valor del nodo es null
            } else if (subnodo.isContainerNode()) { // Si es un ContainerNode tiene que ser ArrayNode o ObjectNode
                if (subnodo.isArray()) {
                    nodoArray = (ArrayNode) subnodo; // Sabiendo que es un ArrayNode lo podemos castear
                } else if (subnodo.isObject()) {
                    nodoObjeto = (ObjectNode) subnodo; // Sabiendo que es un ObjectNode lo podemos castear
                }
            } else if (subnodo.isValueNode()) {
                nodoValor = (ValueNode) subnodo; // Sabiendo que es un ValueNode lo podemos castear

                // Si es un ValueNode se puede operar directamente como JsonNode o como
                // ValueNode, principalmente lo recuperaremos con:
                nodoValor.get(0).asBoolean();
                nodoValor.get(0).asInt();
                nodoValor.get(0).asText();

                LocalDate nodoFecha = LocalDate.parse(nodoValor.get(0).asText(), formatoIn); // Asi se puede recoger el
                                                                                             // valor de la fecha
            }

            // Por ultimo tmb es posible iterar sobre un JsonNode con un foreach
            for (JsonNode subsubnodo : subnodo) {
                // Aqui podremos operar con todos los nodos que contenga del tipo que sea
            }

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * Metodo para añadir de forma personalizada una serie de objetos en un Json
     *
     * @param objetos
     */
    public void volcadoJson(List<Object> objetos) {
        ObjectMapper mapper = new ObjectMapper();

        ObjectNode nodoRaiz = mapper.createObjectNode();// El nodoRaiz casi siepre sera un ObjectNode

        ObjectNode nodoObjeto = mapper.createObjectNode();
        ArrayNode nodoArray = mapper.createArrayNode();
        // No se suelen crear ValueNode porque los contenedores ya tienen la opcion de
        // crearlos directamente, es mas facil que crear el ValueNode

        // con estos metodos se añaden valores a los nodos contenedores
        nodoArray.add("valor/nodo");// Añade un valor al ArrayNode
        nodoArray.add(nodoObjeto); // Añade un objeto
        nodoObjeto.put("nombre/clave", "valor/nodo"); // Añade un ValueNode
        // el metodo set añade un nodo contenedor a un ObjectNode
        nodoObjeto.set("clave/nombre", nodoArray);
        nodoObjeto.set("clave/nombre", nodoObjeto);

        // Por ultimo el volcado a fichero:
        try {
            mapper.writerWithDefaultPrettyPrinter().writeValue(ficheroSalida, nodoRaiz);
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    /**
     * Método para delegar en Jackson la serialización de una colección de
     * instancias de la clase Alumno en un fichero Json, con este metodo habria
     * que darle las propiedades al objeto a serializar
     *
     * @param objetos
     */
    public void VolcadoJsonSimple(List<Object> objetos) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.writerWithDefaultPrettyPrinter().writeValue(ficheroSalida, objetos);
        } catch (StreamWriteException | DatabindException e) {
            log.error("Error durante la escritura JSON: " + e.getMessage());
        } catch (IOException e) {
            log.error("Error durante la escritura JSON: " + e.getMessage());
        }
    }
}