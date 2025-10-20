package es.ciudadescolar.util;

import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

public class AlumnoErrorHandler implements ErrorHandler{
    /**
     * El ErrorHandler por defecto NO detiene el parseo del xml
     *
     * warning -> avisa de posibles problemas pero no criticos. Puede tener sentido que siga procesando
     * error -> error que podria ser recuperable. No detiene el parseo salvo que nosotros se lo indiquemos
     * fatal error -> error no recuperable
     */
    @Override
    public void warning(SAXParseException exception) throws SAXException {
        System.out.println("Alerta! ["+exception.getLineNumber()+"]["+exception.getMessage()+"]");
    }

    @Override
    public void error(SAXParseException exception) throws SAXException {
        System.err.println("Error! ["+exception.getLineNumber()+"]["+exception.getMessage()+"]");
        throw exception;
    }

    @Override
    public void fatalError(SAXParseException exception) throws SAXException {
        System.err.println("Fatal Error! ["+exception.getLineNumber()+"]["+exception.getMessage()+"]");
        throw exception;
    }

}
