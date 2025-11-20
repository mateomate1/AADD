package com.mateomate1;

import java.io.File;
import java.io.FileFilter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FolderStructureTranslator {
    private final Logger log = LoggerFactory.getLogger(FolderStructureTranslator.class);

    private final StringBuilder sb = new StringBuilder();

    public void generarArbol(File carpeta, File salida, FileFilter filter, FileNameComparator comparator) {
        sb.setLength(0);
        print(carpeta, "", true, filter, comparator);
        flushTree(salida);
    }

    private void print(File f, String prefix, boolean isLast, FileFilter filter, FileNameComparator comparator) {
        if (!filter.accept(f))
            return;
        sb.append(prefix);
        if (!prefix.isEmpty())
            sb.append(isLast ? "└── " : "├── ");
        sb.append(f.getName());
        if (f.isDirectory())
            sb.append("/");
        sb.append("\n");

        if (f.isDirectory()) {
            File[] hijos = f.listFiles(filter);
            if (hijos != null) {
                Arrays.sort(hijos, comparator);
                for (int i = 0; i < hijos.length; i++) {
                    boolean ultimoHijo = (i == hijos.length - 1);
                    String nuevoPrefijo = prefix + (isLast ? "    " : "│   ");
                    print(hijos[i], nuevoPrefijo, ultimoHijo, filter, comparator);
                }
            }
        }
    }

    private void flushTree(File salida) {
        try (FileWriter fw = new FileWriter(salida)) {
            fw.write(sb.toString());
            log.trace("Tree dumped into file succesfully");
        } catch (IOException e) {
            log.error("Error flushing tree into file");
        }
    }
}