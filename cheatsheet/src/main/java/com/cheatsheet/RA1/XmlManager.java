package com.cheatsheet.RA1;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentType;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class XmlManager {
    private final Logger log = LoggerFactory.getLogger(XmlManager.class);

    private File ficheroXml;
    private File ficheroXsd;
    private File ficheroDtd;

    public XmlManager() {
    }

    public void setFicheroXml(File ficheroXml) {
        this.ficheroXml = ficheroXml;
        log.trace("Gestor de xml inicializado con exito");
    }

    public void setFicheroXml(String path) {
        this.ficheroXml = new File(path);
        log.trace("Gestor de xml inicializado con exito");
    }

    public void setFicheroXsd(File ficheroXsd) {
        this.ficheroXsd = ficheroXsd;
        log.trace("Gestor de xml inicializado con exito");
    }

    public void setFicheroXsd(String path) {
        this.ficheroXsd = new File(path);
        log.trace("Gestor de xml inicializado con exito");
    }

    public void setFicheroDtd(File ficheroDtd) {
        this.ficheroDtd = ficheroDtd;
        log.trace("Gestor de xml inicializado con exito");
    }

    public void setFicheroDtd(String path) {
        this.ficheroDtd = new File(path);
        log.trace("Gestor de xml inicializado con exito");
    }

    /**
     * Parseo de un XML validado con un DTD
     *
     * @return
     */
    public void parsearXmlDtd() {
        // Elementos necesarios para generar el elemento Raiz
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = null;
        Document documento = null; // Inportante que sea de org.w3c

        dbf.setValidating(true);// Esto solo se activa si se valida contra un dtd sino es false
        dbf.setIgnoringElementContentWhitespace(true); // ignorar nodos con espacios en blanco (sin información útil)

        try {
            db = dbf.newDocumentBuilder();
            // Personalizacion de los errores de validacion (No es obligatorio)
            db.setErrorHandler(new XmlErrorHandler());
            documento = db.parse(ficheroXml);
            parsearXmlDocument(documento);
        } catch (ParserConfigurationException e) {
            log.error(e.getMessage());
        } catch (SAXException e) {
            log.error(e.getMessage());
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    /**
     * Parseo de un XML validado con un XSD
     *
     * @return
     */
    public void parsearXmlXsd() {
        // Elementos necesarios para generar el elemento Raiz
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = null;
        Document documento = null; // Inportante que sea de org.w3c

        dbf.setValidating(false);// Como no se valida contra un DTD sino contra un XSD es false(por defecto ya es
                                 // false)
        dbf.setNamespaceAware(true);// Considerar los namespaces
        dbf.setIgnoringElementContentWhitespace(true);// Ignorar los espacios en blanco del XML

        SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        Schema schema = null;

        try {
            schema = sf.newSchema(ficheroXsd);
            dbf.setSchema(schema);
            db = dbf.newDocumentBuilder();
            // Personalizacion de los errores de validacion (No es obligatorio)
            db.setErrorHandler(new XmlErrorHandler());
            documento = db.parse(ficheroXml); // Desde aqui se parsea igual que si se validara contra un DTD
            parsearXmlDocument(documento);
        } catch (SAXException e) {
            log.error(e.getMessage());
        } catch (ParserConfigurationException | IOException e) {
            log.error(e.getMessage());
        }
    }

    /**
     * Metodo que recibe un documento parseado y especifica el elemento buscado
     *
     * @param documento
     */
    public void parsearXmlDocument(Document documento) {
        // Se recoge el elemento raiz que contiene todo el Xml desde el document
        Element elementoRaiz = documento.getDocumentElement();
        // Se recogen los subnodos que recoge
        NodeList listaNodos = elementoRaiz.getChildNodes();
        // Iteramos sobre el NodeList, al no ser un iterable no podemos usar foreach
        for (int i = 0; i < listaNodos.getLength(); i++) {
            Node nodo = listaNodos.item(i);
            // Buscamos que sea un nodo de tipo Element
            if (nodo.getNodeType() == Node.ELEMENT_NODE) {
                // Buscamos el nodo con el nombre que buscamos
                if (nodo.getNodeName().equals("Nombre del nodo buscado")) {
                    Element nodoElemento = (Element) nodo;
                    // Estructura en la q me baso para recoger los datos:
                    // <Element Atributo = "Valor"> Nodo contenido </Element>
                    String nodoAtributo = nodoElemento.getAttribute("Atributo");
                    String nodoContenido = null;
                    nodoContenido = nodoElemento.getNodeValue();
                    nodoContenido = nodoElemento.getTextContent();
                    // El nodo hijo podria ser otro element
                    Node contenidoComoNodo = nodoElemento.getFirstChild();
                    if (contenidoComoNodo.getNodeType() == Node.TEXT_NODE)
                        nodoContenido = contenidoComoNodo.getNodeValue();
                }
            }
        }
    }

    public Node recuperarNodoRecursivo(Node nodoInicial, String nombreNodo) {
        Node resultado = null;
        if (nodoInicial.getNodeName().equals(nombreNodo))
            return nodoInicial;
        else {
            if (nodoInicial.getNodeType() == Node.ELEMENT_NODE) {
                Element elementoInicial = (Element) nodoInicial;
                NodeList hijos = elementoInicial.getChildNodes();
                for (int i = 0; i < hijos.getLength(); i++) {
                    Node hijo = hijos.item(i);
                    resultado = recuperarNodoRecursivo(hijo, nombreNodo);
                    if (resultado != null)
                        return resultado;
                }
            } else if (nodoInicial.getNodeType() == Node.TEXT_NODE)
                return null;
        }
        return resultado;
    }

    public List<Node> recuperarNodosRecursivo(Node nodoInicial, String nombreNodo) {
        List<Node> resultado = null;
        if (nodoInicial.getNodeType() == Node.ELEMENT_NODE) {
            Element elementoInicial = (Element) nodoInicial;
            NodeList hijos = elementoInicial.getChildNodes();
            for (int i = 0; i < hijos.getLength(); i++) {
                Node hijo = hijos.item(i);
                resultado = recuperarNodosRecursivo(hijo, nombreNodo);
                if (hijo.getNodeType() == Node.TEXT_NODE) {
                    return null;
                } else {
                    List<Node> recuperadosRecursivos = recuperarNodosRecursivo(hijo, nombreNodo);
                    resultado.addAll(recuperadosRecursivos);
                }
            }
        } else if (nodoInicial.getNodeType() == Node.TEXT_NODE)
            return null;
        return resultado;
    }

    /**
     * Metodo que genera un objeto Document que contiene una estructura Xml a partir
     * de una lista de objetos
     *
     * @param objetos
     */
    public void generarXmlDocument(List<Object> objetos) {
        DocumentBuilderFactory dbf = null;
        DocumentBuilder db = null;
        Document documento = null;

        Element elementObjeto = null;
        Element elementObjetos = null;

        dbf = DocumentBuilderFactory.newInstance();
        try {
            db = dbf.newDocumentBuilder();
            // Personalizacion de los errores de validacion (No es obligatorio)
            db.setErrorHandler(new XmlErrorHandler());

            documento = db.newDocument();

            // siempre creamos elemento y lo añadimos al árbol (2 acciones)
            Element raiz = documento.createElement("Raiz");

            // Añadimos el elemento raiz a el documento
            documento.appendChild(raiz);

            // <Objetos>
            // <Objeto Atributo="valor">Contenido</Objeto>
            // ...
            // </Objetos>
            elementObjetos = documento.createElement("Objetos");
            for (Object objeto : objetos) {
                // Estructura resultante:
                // <Objeto Atributo="valor">Contenido</Objeto>
                elementObjeto = documento.createElement("Objeto");
                elementObjeto.setAttribute("Atributo", "valor");
                elementObjeto.setTextContent("Contenido");

                // Añadimos el element objeto a el elemento padre objetos
                elementObjetos.appendChild(elementObjeto);
            }
            generarXmlDtd(documento);
        } catch (ParserConfigurationException e) {
            log.error(e.getMessage());
        }
    }

    /**
     * Metodo que recive una estructura de xml pasada como objeto Element y lo
     * vuelca a un fichero Xml validado por un fichero dtd
     *
     * @param documento
     */
    public void generarXmlDtd(Document documento) {
        /*
         * ya tenemos lista nuestra estructura DOM en memoria, es decir que ya esta
         * generado el documento como objeto. Puedo por tanto volcarla al fichero XML
         */

        /*
         * En este ejemplo de clase, el fichero de salida deberá validarse contra un DTD
         * La forma en la que indicaremos la validación contra DTD será distinta de la
         * que usaremos más adelante cuando validemos contra XSD (schema)
         */

        TransformerFactory tf = null;
        Transformer t = null;
        DOMSource ds = null;
        StreamResult sr = null;
        DOMImplementation domImp = null;
        DocumentType docType = null;

        // Reiniciamos el xml
        if (ficheroXml.exists()) {
            ficheroXml.delete();
        }

        tf = TransformerFactory.newInstance();

        try {
            t = tf.newTransformer();
            ds = new DOMSource(documento);
            sr = new StreamResult(new FileWriter(ficheroXml));
            // Como nuestro XML de salida queremos que se valide contra un DTD
            domImp = documento.getImplementation();
            docType = domImp.createDocumentType("doctype", null, ficheroDtd.getName());
            t.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, docType.getSystemId());
            t.setOutputProperty(OutputKeys.METHOD, "xml");
            t.setOutputProperty(OutputKeys.VERSION, "1.0");
            t.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            t.setOutputProperty(OutputKeys.INDENT, "yes");
            t.transform(ds, sr);
        } catch (TransformerConfigurationException e) {
            log.error(e.getMessage());
        } catch (IOException | TransformerException e) {
            log.error(e.getMessage());
        }
    }

    public void generarXmlXsd(Document documento) {
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

        // Reiniciamos el xml
        if (ficheroXml.exists()) {
            ficheroXml.delete();
        }

        tf = TransformerFactory.newInstance();

        try {
            t = tf.newTransformer();
            ds = new DOMSource(documento);
            sr = new StreamResult(new FileWriter(ficheroXml));
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
            log.error(e.getMessage());
        } catch (IOException | TransformerException e) {
            log.error(e.getMessage());
        }
    }

    public void resumenRecuperarDatosDTD() {
        try {
            // Elementos necesarios para generar el elemento Raiz
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = null;
            Document documento = null; // Inportante que sea de org.w3c

            dbf.setValidating(true);// Esto solo se activa si se valida contra un dtd sino es false
            dbf.setIgnoringElementContentWhitespace(true); // ignorar nodos con espacios en blanco (sin información
                                                           // útil)

            db = dbf.newDocumentBuilder(); // El error handler se agregaria aqui
            documento = db.parse(ficheroXml);
            Element elementoRaiz = documento.getDocumentElement();
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    public void resumenRecuperarDatos() {
        boolean isXsd = true;
        boolean isDtd = true;
        try {
            // Elementos necesarios para generar el elemento Raiz
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

            if (isXsd) {
                SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
                Schema schema = null;
                schema = sf.newSchema(ficheroXsd);
                dbf.setSchema(schema);
            }
            if (isDtd) {
                dbf.setValidating(false);// Esto solo se activa si se valida contra un dtd sino es false
                dbf.setNamespaceAware(true);// Considerar los namespaces

            }
            dbf.setIgnoringElementContentWhitespace(true); // ignorar nodos con espacios en blanco (sin información
                                                           // útil)


            DocumentBuilder db = dbf.newDocumentBuilder(); // El error handler se agregaria aqui
            Document documento = db.parse(ficheroXml);
            Element elementoRaiz = documento.getDocumentElement();
        } catch (Exception e) {
            // TODO: handle exception
        }
    }
}
