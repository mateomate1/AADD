package com.mateomate1;

import java.io.File;
import java.io.FileFilter;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FolderStructureTranslator {
    private final Logger log = LoggerFactory.getLogger(FolderStructureTranslator.class);

    private final StringBuilder sb = new StringBuilder();

    private void print(File f, String prefix, boolean isLast, List<String> fileExtensions) {
        if (f.isFile()) {
            String nombre = f.getName();
            int punto = nombre.lastIndexOf('.');
            if (punto != -1) {
                String ext = nombre.substring(punto + 1).toLowerCase();
                if (!fileExtensions.contains(ext))
                    return;
            } else {
                if (!fileExtensions.contains(""))
                    return;
            }
        }
        sb.append(prefix);
        if (!prefix.isEmpty()) {
            sb.append(isLast ? "└── " : "├── ");
        }
        sb.append(f.getName());
        if (f.isDirectory())
            sb.append("/");
        sb.append("\n");

        if (f.isDirectory()) {
            File[] hijos = f.listFiles();
        }
    }
}

public class FolderEstructureFilter implements FileFilter{

    @Override
    public boolean accept(File f) {
        if(f.isDirectory())
            return true;
        else{
            
            return false;
        }
    }

}
