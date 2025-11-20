package com.mateomate1;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class VisorCarpetasAscii {
    private final Logger log = LoggerFactory.getLogger(VisorCarpetasAscii.class);

    private final StringBuilder sb = new StringBuilder();

    public void generarArbol(File carpeta, File salida, List<String> extensiones) {
        sb.setLength(0);
        imprimir(carpeta, "", true, extensiones);
        escribirFichero(salida);
    }

    private void imprimir(File f, String prefijo, boolean esUltimo, List<String> extensiones) {
        if (f.isFile()) {
            String nombre = f.getName();
            int punto = nombre.lastIndexOf('.');
            if (punto == -1)
                return;
            String ext = nombre.substring(punto + 1).toLowerCase();
            if (!extensiones.contains(ext))
                return;
        }

        sb.append(prefijo);
        if (!prefijo.isEmpty()) {
            sb.append(esUltimo ? "└── " : "├── ");
        }
        sb.append(f.getName());
        if (f.isDirectory())
            sb.append("/");
        sb.append("\n");

        if (f.isDirectory()) {
            File[] hijos = f.listFiles();
            if (hijos != null) {
                Arrays.sort(hijos);
                for (int i = 0; i < hijos.length; i++) {
                    boolean ultimoHijo = (i == hijos.length - 1);
                    String nuevoPrefijo = prefijo + (esUltimo ? "    " : "│   ");
                    imprimir(hijos[i], nuevoPrefijo, ultimoHijo, extensiones);
                }
            }
        }
    }

    private void escribirFichero(File salida) {
        try (FileWriter fw = new FileWriter(salida)) {
            fw.write(sb.toString());
        } catch (IOException e) {
            log.error("Error escribiendo en el fichero");
        }
    }
}
