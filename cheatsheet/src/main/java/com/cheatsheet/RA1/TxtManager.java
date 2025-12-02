package com.cheatsheet.RA1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cheatsheet.Example.Objeto;

public class TxtManager {
    private final Logger log = LoggerFactory.getLogger(TxtManager.class);

    private File ficheroTxt;

    public TxtManager(File ficheroTxt) {
        this.ficheroTxt = ficheroTxt;
        log.trace("Gestor de txt inicializado con exito");
    }

    public TxtManager(String path) {
        this.ficheroTxt = new File(path);
        log.trace("Gestor de txt inicializado con exito");
    }

    public void parsearTxt() {
        FileReader fr = null;
        BufferedReader br = null;

        // Variable que almacenara cada linea leida del txt
        String linea = null;
        // Variable que almacena los datos de cada linea
        String[] datos = null;

        Integer posicionDato1 = null;
        Integer posicionDato2 = null;
        Integer posicionDato3 = null;
        Integer posicionDato4 = null;
        try {
            fr = new FileReader(ficheroTxt);
            br = new BufferedReader(fr);
            linea = br.readLine();
            while (linea != null) {
                datos = linea.split("\\|");
                // Si alguno de los datos que deberia estar en el indice no se encuentra no
                // recogera el resto de datos.
                if (posicionDato1 == null || posicionDato2 == null || posicionDato3 == null || posicionDato4 == null) {
                    // Recorre cada dato de la linea en busca de los indices q buscamos
                    for (int i = 0; i < datos.length; i++) {
                        // Este ifelse lo usaremos si tenemos 1 primera linea que nos sirva de indice
                        if (datos[i].toLowerCase().equals("nombreDato1")) {
                            posicionDato1 = i;
                        } else if (datos[i].toLowerCase().equals("nombreDato2")) {
                            posicionDato2 = i;
                        } else if (datos[i].toLowerCase().equals("nombreDato3")) {
                            posicionDato3 = i;
                        } else if (datos[i].toLowerCase().equals("nombreDato4")) {
                            posicionDato4 = i;
                        } else {
                            log.warn("No corresponde a ninguno de los datos buscados");
                        }
                    }
                } else {
                    Objeto objeto = new Objeto();
                    objeto.setDato1(datos[posicionDato1]);
                    objeto.setDato2(datos[posicionDato2]);
                }
            }
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            if (br != null)
                try {
                    br.close();
                } catch (IOException e) {
                    System.out.println("Error durante el cierre de fichero");
                }
        }
    }

    public void escribirFichero() {
        FileWriter fw = null;
        PrintWriter pw = null;
        try {
            fw = new FileWriter(ficheroTxt);
            pw = new PrintWriter(fw);

            for (int i = 0; i < 10; i++) {
                pw.println("linea#" + i + "#texto");
            }
            pw.flush();
        } catch (IOException e) {
            System.out
                    .println("Error durante el acceso para escritura del fichero [" + ficheroTxt.getAbsolutePath() + "]");
        } finally {
            if (pw != null)
                pw.close();
        }
    }
}
