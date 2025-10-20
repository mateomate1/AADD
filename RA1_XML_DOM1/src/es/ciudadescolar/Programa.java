package es.ciudadescolar;

import es.ciudadescolar.util.XmlManager;
import java.io.File;
import java.util.List;

public class Programa {
    public static void main(String[] args) throws Exception {
        List<String> alumnosRecuperados = XmlManager.recuperarExpedientes(new File("alumnos2.xml"));
        for (String alumno : alumnosRecuperados) {
            System.out.println(alumno);
        }
    }
}
