package com.cheatsheet.Util;

import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

public class XmlErrorHandler implements ErrorHandler{

    @Override
    public void warning(SAXParseException exception) throws SAXException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'warning'");
    }

    @Override
    public void error(SAXParseException exception) throws SAXException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'error'");
    }

    @Override
    public void fatalError(SAXParseException exception) throws SAXException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'fatalError'");
    }

}
