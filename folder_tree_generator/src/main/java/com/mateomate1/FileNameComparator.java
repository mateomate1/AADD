package com.mateomate1;

import java.io.File;
import java.util.Comparator;

public class FileNameComparator implements Comparator<File> {

    private Boolean directoriesFirst = null;
    private Boolean reverseOrder = null;
    private Boolean alphabeticalOrder = null;
    private Boolean sizeOrder = null;
    private Boolean lastModifiedOrder = null;
    private Boolean extensionOrder = null;


    //TODO: Implementar todos los tipos de orden
    @Override
    public int compare(File f1, File f2) {
        if (directoriesFirst) {
            if (f1.isDirectory() && !f2.isDirectory())
                return -1;
            if (!f1.isDirectory() && f2.isDirectory())
                return 1;
        }

        int resultado = 0;

        if (alphabeticalOrder) {
            resultado = f1.getName().compareToIgnoreCase(f2.getName());
        } else {
            resultado = f1.getName().compareTo(f2.getName());
        }

        if (reverseOrder)
            resultado = -resultado;

        return resultado;
    }

}
