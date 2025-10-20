import java.io.File;
import java.util.List;

import es.ciudadescolar.util.Alumno;
import es.ciudadescolar.util.XmlManager;

public class App {
   
    public static void main(String[] args) throws Exception {
        
        List<Alumno> alumnosRecuperados = XmlManager.procesarXMLAlumnos(new File("alumnos3.xml"), new File("alumnos3.xsd"));

        if (alumnosRecuperados.isEmpty())
        {
            System.out.println("No se han recuperado alumnos del xml");
            System.exit(1);
        }

        for (Alumno al:alumnosRecuperados)
        {
            System.out.println(al);
        }

        XmlManager.generarXmlAlumnos(alumnosRecuperados, new File("alumnos4.xml"), new File("alumnos4.xsd"));
  
    }
}
