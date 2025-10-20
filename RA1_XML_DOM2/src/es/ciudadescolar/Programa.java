package es.ciudadescolar;

import es.ciudadescolar.util.XmlManager;
import java.io.File;
import java.util.List;

public class Programa {
    public static void main(String[] args) throws Exception {
        List<Integer> edades = XmlManager.getMediaEdad(new File("alumnos4.xml"), new File("alumnos4.xsd"));
        Integer i = 0;
        for (Integer edad : edades) {
            System.out.println(edad);
            i+=edad;
        }
        System.out.println("la media es "+ i/(edades.size()));
    }
}
