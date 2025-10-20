package es.ciudadescolar;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase que gestiona la escritura y lectura de objetos de la clase Modulo en un fichero de texto
 *
 * @author Mateo Ayarra
 * @version 1.0
 */
public class GestorTxt {

    File fichero;

    /**
     * Metodo de clase que inicializa la clase GestorTxt, recibe un String correspondiente a la ubicaciuon del fichero a escribir y genera un atributo de clase de tipo File
     *
     * @param path
     */
    public GestorTxt(String path) {
        this.fichero = new File(path);
    }

    /**
     * Metodo de clase que recupera los modulos de un fichero de texto con el formato: Módulo profesional|horas semanales|profesor|curso|contraseña AV
     *
     * @return
     */
    public List<Modulo> getModulos() {
        FileReader fr = null;
        BufferedReader br = null;
        List<Modulo> modulos = new ArrayList<>();
        String linea = null;
        String[] datos = null;

        try {
            fr = new FileReader(fichero);
            br = new BufferedReader(fr);
            linea = br.readLine();
            while (linea != null) {
                datos = linea.split("\\|"); // El caracter | es un operadort logico, es necesario poner \\| para que use el caracter en si
                Modulo modulo = new Modulo();
                modulo.setModulo(datos[0]);
                modulo.setHoras(Double.parseDouble(datos[1]));
                modulo.setProfesor(datos[2]);
                modulo.setCurso(Integer.parseInt(datos[3]));
                modulo.setContrasena(datos[4]);
                modulos.add(modulo);
                System.out.println(modulo);
                linea = br.readLine();
            }
        } catch (FileNotFoundException e) {
            System.out.println("El fichero [" + fichero.getPath() + "] no existe o no se encuentra.");
        } catch (IOException e) {
            System.out.println("Error leyendo la linea.");
        }

        return modulos;
    }

    /**
     * Metodo de clase que recibe una lista de modulos y la escribe en el fichero indicado en el atributo de la clase
     *
     * @param modulos
     */
    public void setModulos(List<Modulo> modulos) {
        FileWriter fw = null;
        PrintWriter pw = null;
        try {
            fw = new FileWriter(fichero);
            pw = new PrintWriter(fw);

            for (Modulo modulo : modulos) {
                pw.println(modulo.toString());
            }
            pw.flush();
        } catch (IOException e) {
            System.out.println("Error durante el acceso para escritura del fichero [" + fichero.getPath() + "]");
        } finally {
            if (pw != null)
                pw.close();
        }
    }

    /**
     * Metodo de clase que recibe un modulo y la escribe en el fichero indicado en el atributo de la clase
     *
     * @param modulos
     */
    public void setModulo(Modulo modulo) {
        FileWriter fw = null;
        PrintWriter pw = null;
        try {
            fw = new FileWriter(fichero);
            pw = new PrintWriter(fw);

            pw.println(modulo.toString());

            pw.flush();
        } catch (IOException e) {
            System.out.println("Error durante el acceso para escritura del fichero [" + fichero.getPath() + "]");
        } finally {
            if (pw != null)
                pw.close();
        }
    }
}
