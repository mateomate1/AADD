package com.cheatsheet.RA3;

public class QUERRYS {
    // En esta consulta Clase es el nombre de la clase con las anotaciones de
    // hybernate
    // Atributo es el nombre del atributo tal como esta en la clase
    // Y :var es el nombre que va a ser sustituida por el valor que se desee con el
    // metodo setParameter(str, obj)
    public static final String ejemploConsultaTipada = "SELECT c FROM Clase c WHERE c.Atributo = :var";

}
