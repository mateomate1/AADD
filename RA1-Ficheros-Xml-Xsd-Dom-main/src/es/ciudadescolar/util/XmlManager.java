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

    public static List<Alumno> procesarXMLAlumnos(File ficheroXml, File ficheroXsd) {
        List<Alumno> alumnos = new ArrayList<Alumno>();

        Alumno alumno = null;

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

        dbf.setValidating(false); // Cuando el xml a procesar se valide contra un XSD (schema) he de fijar el
                                  // parámetro FALSE

        // Para validar correctamente contra un XSD necesito que se consideren los
        // namespaces.
        // vamos a disponer de los siguientes atributos en el elemento raiz del XML:
        // xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
        // xsi:noNamespaceSchemaLocation="alumnos3.xsd">

        dbf.setNamespaceAware(true);

        dbf.setIgnoringElementContentWhitespace(true); // ignorar nodos con espacios en blanco (sin información útil)

        SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);

        Schema schema = null;

        try {
            schema = sf.newSchema(ficheroXsd);

            dbf.setSchema(schema);

            DocumentBuilder db = dbf.newDocumentBuilder();

            db.setErrorHandler(new AlumnoErrorHandler()); // Para personalizar el control de los errores de validación

            Document documento = db.parse(ficheroXml);

            Element elementoRaiz = documento.getDocumentElement(); // obtenemos el elemento Raiz del xml alumnos:
                                                                   // "alumnos"

            // System.out.println("Se disponen de
            // ["+elementoRaiz.getChildNodes().getLength()+"] alumnos");

            NodeList listaNodosAlumno = elementoRaiz.getChildNodes();

            for (int i = 0; i < listaNodosAlumno.getLength(); i++) {
                Node nodoAlumno = listaNodosAlumno.item(i);

                // sabemos que los nodos alumno son elementos (Element)
                if (nodoAlumno.getNodeType() == Node.ELEMENT_NODE) {
                    Element elementoAlumno = (Element) nodoAlumno;

                    Node nodoExpediente = elementoAlumno.getFirstChild();
                    // Node nodoNombre = nodoExpediente.getNextSibling();
                    Node nodoNombre = nodoAlumno.getLastChild().getPreviousSibling();
                    // Node nodoEdad = nodoAlumno.getLastChild();
                    Node nodoEdad = nodoExpediente.getNextSibling().getNextSibling();

                    // alumno = new Alumno(nodoNombre.getTextContent(),
                    // nodoExpediente.getFirstChild().getNodeValue(),
                    // Integer.parseInt(nodoEdad.getTextContent()));
                    alumno = new Alumno(nodoNombre.getTextContent(), nodoExpediente.getTextContent(),
                            Integer.parseInt(nodoEdad.getTextContent()));
                    alumnos.add(alumno);
                }
            }

        } catch (ParserConfigurationException e) {
            System.err.println("Error durante el parseo de XML: " + e.getMessage());
        } catch (SAXException e) {
            System.err.println("Error durante el parseo de XML: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Error durante el parseo de XML: " + e.getMessage());
        }

        return alumnos;
    }

    public static void generarXmlAlumnos(List<Alumno> alumnos, File outputXmlFile, File xsdOutputXml) {
        /*
         * 1º - Creo la estructura DOM en la memoria RAM
         * 2º - Vuelco la estructura DOM a fichero XML
         */

        DocumentBuilderFactory dbf = null;
        DocumentBuilder db = null;
        Document documento = null;

        Element elementoAlumno = null;
        Element elementoEdad = null;

        try {
            dbf = DocumentBuilderFactory.newInstance();
            db = dbf.newDocumentBuilder();
            db.setErrorHandler(new AlumnoErrorHandler());

            documento = db.newDocument();

            // siempre creamos elemento y lo añadimos al árbol (2 acciones)
            Element raiz = documento.createElement("estudiantes");

            // la validación contra XSD supone añadir estos dos atributos al elemento raiz
            // del xml
            raiz.setAttribute("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
            raiz.setAttribute("xsi:noNamespaceSchemaLocation", xsdOutputXml.getName());

            documento.appendChild(raiz);

            for (Alumno al : alumnos) {
                elementoAlumno = documento.createElement("alumno");
                elementoAlumno.setAttribute("nombre", al.getNombre());
                elementoAlumno.setAttribute("exp", al.getExpediente());
                elementoAlumno.setAttribute("edad", al.getEdad().toString());

                elementoEdad = documento.createElement("edad");
                elementoEdad.setTextContent(al.getEdad().toString());
                elementoAlumno.appendChild(elementoEdad);
                raiz.appendChild(elementoAlumno);
            }

            // ya tenemos lista nuestra estructura DOM en memoria. Puedo por tanto volcarla
            // al fichero XML

            /*
             * En este ejemplo de clase, el fichero de salida deberá llamarse alumnos4.xml y
             * validarse contra un XSD (Schema) llamado alumnos4.xsd
             * La forma en la que indicaremos la validación contra XSD es distinta de la que
             * ya usamos cuando validemos contra DTD
             */

            TransformerFactory tf = null;
            Transformer t = null;
            DOMSource ds = null;
            StreamResult sr = null;

            // DOMImplementation domImp = null;
            // DocumentType docType = null;

            try {
                // en cada ejecución quiero recrear de nuevo el fichero.
                if (outputXmlFile.exists()) {
                    outputXmlFile.delete();
                }

                tf = TransformerFactory.newInstance();

                t = tf.newTransformer();

                ds = new DOMSource(documento);

                sr = new StreamResult(new FileWriter(outputXmlFile));

                // Como nuestro XML de salida NO queremos que se valide contra un DTD, esto de
                // aquí lo quitamos o comentamos
                // domImp = documento.getImplementation();
                // docType = domImp.createDocumentType("doctype", null, dtdOutputXml.getName());
                // t.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, docType.getSystemId());

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

        } catch (ParserConfigurationException e) {
            System.err.println("Error durante la generación del XML: " + e.getMessage());

        }

    }

}
