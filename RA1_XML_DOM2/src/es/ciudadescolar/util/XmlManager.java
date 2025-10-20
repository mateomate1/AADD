package es.ciudadescolar.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentType;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class XmlManager {
    public static List<Alumno> procesarXMLAlumnos(File ficheroXML) {
        List<Alumno> alumnos = new ArrayList<>();
        Alumno alumno = null;
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

        dbf.setValidating(true);
        dbf.setIgnoringElementContentWhitespace(true);

        try {
            DocumentBuilder db = dbf.newDocumentBuilder();

            db.setErrorHandler(new AlumnoErrorHandler()); // Para personalizar el control de los errores de vadilacion

            Document documento = db.parse(ficheroXML);

            Element elementoRaiz = documento.getDocumentElement(); // Obtenemos el elemento raiz del XML alumnos: "alumno"

            NodeList listaNodosAlumno = elementoRaiz.getChildNodes();

            for (int i = 0; i < listaNodosAlumno.getLength(); i++) {
                Node nodoAlumno = listaNodosAlumno.item(i);
                // sabemos que los nodos alumnos son elementos (Element)
                if (nodoAlumno.getNodeType() == Node.ELEMENT_NODE) {
                    Element elementoAlumno = (Element) nodoAlumno;

                    Node nodoExpediente = elementoAlumno.getFirstChild();
                    String expediente = nodoExpediente.getTextContent();
                    Node nodoNombre = nodoExpediente.getNextSibling(); // IMPORTANT!! Para usar nextSibling hay que usar el nodo no el elemento padre
                    String nombre = nodoNombre.getTextContent();
                    Node nodoEdad = elementoAlumno.getLastChild();
                    int edad = Integer.parseInt(nodoEdad.getTextContent());

                    alumno = new Alumno(expediente, nombre, edad);
                    alumnos.add(alumno);
                }
            }

            System.out.println("Se disponen de [" + elementoRaiz.getChildNodes().getLength() + "] alumnos");
        } catch (ParserConfigurationException | SAXException | IOException e) {
            System.err.println("Error durante el parseo del XML: " + e.getMessage());
        } finally {

        }

        return alumnos;
    }

    public static void generarAlumnos(List<Alumno> alumnos) {
        /*
         * 1- Creo la estructura DOM en la memoria RAM
         * 2- Vuelco la estructura DOM a fichero XML
         */

        DocumentBuilderFactory dbf = null;
        DocumentBuilder db = null;
        Document documento = null;
        Element elementoAlumno = null;
        Element elementoEdad = null;

        try {
            dbf = DocumentBuilderFactory.newDefaultInstance();
            db = dbf.newDocumentBuilder();
            db.setErrorHandler(new AlumnoErrorHandler());

            documento = db.newDocument();

            Element raiz = documento.createElement("Estudiantes");
            documento.appendChild(raiz);

            for (Alumno al : alumnos) {
                elementoAlumno = documento.createElement("alumno");
                elementoAlumno.setAttribute("exp", al.getExpediente());
                elementoAlumno.setAttribute("nom", al.getNombre());
                raiz.appendChild(elementoAlumno);

                elementoEdad = documento.createElement("edad");
                elementoEdad.setTextContent(al.getEdad().toString());
                elementoAlumno.appendChild(elementoEdad);
                raiz.appendChild(elementoAlumno);
            }
            TransformerFactory tf = null;
            Transformer t = null;
            DOMSource ds = null;
            StreamResult sr = null;
            DOMImplementation domImp = null;
            DocumentType docType = null;

            try {
                tf = TransformerFactory.newInstance();

                t = tf.newTransformer();

                ds = new DOMSource(documento);

                sr = new StreamResult(new FileWriter("alumnos2.xml"));

                // Como nuestro XML de salida queremos que se valide contra un DTD (alumnos2.dtd)
                domImp = documento.getImplementation();

                docType = domImp.createDocumentType("doctype", null, "alumnos2.dtd");

                t.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, docType.getSystemId());

                t.setOutputProperty(OutputKeys.METHOD, "xml");

                t.setOutputProperty(OutputKeys.VERSION, "1.0");

                t.setOutputProperty(OutputKeys.ENCODING, "UTF-8");

                t.setOutputProperty(OutputKeys.INDENT, "yes");

                t.transform(ds, sr);

            } catch (TransformerConfigurationException e) {
                System.out.println("Error generando XML");
            } catch (FileNotFoundException e) {
                System.out.println("Error en el volcado del árbol DOM sobre el fichero XML");
            } catch (TransformerException e) {
                System.out.println("Error durante en el volcado del árbol DOM sobre el fichero XML");
            } catch (IOException e) {
                System.out.println("Error durante en el volcado del árbol DOM sobre el fichero XML");
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    public static List<String> recuperarExpedientes(File alumnosXML){
        List<String> expedientes = new ArrayList<>();
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setValidating(true);
        dbf.setIgnoringElementContentWhitespace(true);
        try {
            System.out.println("Hola");
            DocumentBuilder db = dbf.newDocumentBuilder();

            db.setErrorHandler(new AlumnoErrorHandler()); // Para personalizar el control de los errores de vadilacion

            Document documento = db.parse(alumnosXML);

            Element elementoRaiz = documento.getDocumentElement(); // Obtenemos el elemento raiz del XML alumnos: "alumno"

            NodeList listaNodosAlumno = elementoRaiz.getChildNodes();

            for (int i = 0; i < listaNodosAlumno.getLength(); i++) {
                Node nodoAlumno = listaNodosAlumno.item(i);
                // sabemos que los nodos alumnos son elementos (Element)
                if (nodoAlumno.getNodeType() == Node.ELEMENT_NODE) {
                    Element elementoAlumno = (Element) nodoAlumno;
                    String expediente = elementoAlumno.getAttribute("exp");
                    expedientes.add(expediente);
                    System.out.println(expediente);
                }
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
        return expedientes;
    }

    public static List<Integer> getMediaEdad(File ficheroXml, File ficheroXsd){
        List<Integer> edades = new ArrayList<>();
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setValidating(false);
        dbf.setNamespaceAware(true);
        dbf.setIgnoringElementContentWhitespace(true);
        SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        Schema schema = null;

        try {
            schema = sf.newSchema(ficheroXsd);
            dbf.setSchema(schema);
            DocumentBuilder db = dbf.newDocumentBuilder();
            db.setErrorHandler(new AlumnoErrorHandler());
            Document documento = db.parse(ficheroXml);
            Element elementoRaiz = documento.getDocumentElement();
            NodeList NodosEdades = elementoRaiz.getElementsByTagName("edad");
            for (int i = 0; i < 10; i++) {
                edades.add(Integer.valueOf(NodosEdades.item(i).getTextContent()));
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
        return edades;
    }

}
