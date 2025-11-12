package com.cheatsheet;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import com.cheatsheet.Util.XmlManager;

public class Main {
    private static final Logger log = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        // 🔴 NIVEL ALTO: CRITICO O URGENTE
        // BUG: se usa para indicar un error confirmado en el codigo que debe corregirse
        // FIXME: se usa para marcar una parte del codigo que necesita reparacion o
        // ajuste
        // XXX: se usa para marcar codigo sospechoso o potencialmente peligroso, que
        // funciona pero no es del todo correcto o confiable
        // IMPORTANT: se usa para resaltar partes criticas o sensibles del codigo que
        // requieren especial atencion

        // 🟠 NIVEL MEDIO-ALTO: ATENCION / MANTENIMIENTO

        // TODO: se usa para indicar tareas pendientes o funcionalidades que aun no se
        // han implementado
        // REVIEW: se usa para pedir que otro desarrollador revise esta parte del codigo
        // o la logica usada
        // WARNING: se usa para avisar de un posible problema o riesgo en la ejecucion
        // TEMP: se usa para marcar codigo temporal que sera eliminado o reemplazado mas
        // adelante
        // UNDONE: se usa para indicar algo que estaba hecho pero se ha revertido o
        // dejado incompleto

        // 🟡 NIVEL MEDIO-BAJO: MEJORA O EFICIENCIA
        // OPTIMIZE: se usa para señalar codigo que podria mejorarse en rendimiento o
        // eficiencia
        // HACK: se usa para marcar una solucion rapida o provisional que deberia
        // reescribirse correctamente en el futuro

        // 🟢 NIVEL BAJO: INFORMATIVO / DOCUMENTAL
        // NOTE: se usa para añadir informacion o aclaraciones importantes sobre el
        // funcionamiento del codigo
        // QUESTION: se usa para dejar una duda o pregunta sobre alguna parte del codigo
        // que requiere confirmacion
        // DEPRECATED: se usa para marcar codigo que ya no deberia usarse y que sera
        // eliminado en versiones futuras

        // Orden de los logs
        log.trace("Mensaje TRACE - nivel mas bajo, para seguimiento detallado del flujo");
        log.debug("Mensaje DEBUG - informacion util para depuracion");
        log.info("Mensaje INFO - eventos informativos o confirmacion de que todo va bien");
        log.warn("Mensaje WARN - aviso de una situacion inesperada o posible problema");
        log.error("Mensaje ERROR - error que impide que algo se ejecute correctamente");

        XmlManager xmlManager = new XmlManager();
        Node nodoRaiz = getRaiz(new File("test.xml"));
        log.trace(nodoRaiz.toString());
        List<Node> nodos = xmlManager.recuperarNodosRecursivo(nodoRaiz, "child3");
        if (nodos != null) {
            for (Node node : nodos) {
                log.trace(node.toString());
                System.out.println(node.toString());
            }
        } else {
            log.warn("No se recuperaron nodos");
        }
    }

    public static Node getRaiz(File archivo) {
        if (!archivo.exists()) {
            return null;
        } else {
            System.out.println("Ruta: " + archivo.getAbsolutePath());
            System.out.println("Tamaño: " + archivo.length());
        }
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = null;
        Document documento = null;

        Node nodoRaiz = null;

        dbf.setValidating(true);
        dbf.setIgnoringElementContentWhitespace(true);

        try {
            db = dbf.newDocumentBuilder();
            documento = db.parse(archivo);
            org.w3c.dom.Element elementoRaiz = documento.getDocumentElement();
            nodoRaiz = elementoRaiz;
        } catch (IOException e) {
            log.error(e.getMessage());
        } catch (ParserConfigurationException | SAXException e) {
            log.error(e.getMessage());
        }
        return nodoRaiz;
    }
}