package com.mateomate1;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.List;

public class FileStructureFilter implements FileFilter {

    private List<String> excludedExtensions = null;
    private List<String> includedExtensions = null;
    private List<String> excludedFiles = null;
    private List<String> includedFiles = null;

    /**
     * Metodo que revisa cual de las listas esta rellena para asi decidir cuando no
     * incluir el archivo recivido
     *
     */
    @Override
    public boolean accept(File f) {
        String name = f.getName();
        if (f.isDirectory()) {
            if (excludedFiles != null && excludedFiles.contains(name)) {
                return false;
            }
            return true;
        }

        int dot = name.lastIndexOf('.');
        String ext = dot != -1 ? name.substring(dot + 1).toLowerCase() : "";

        if (includedExtensions != null && includedExtensions.contains(ext)) {
            if (excludedFiles != null && excludedFiles.contains(name))
                return false;
            return true;
        } else if (excludedExtensions != null && excludedExtensions.contains(ext)) {
            if (includedFiles != null && includedFiles.contains(name))
                return true;
            return false;
        }
        return true;
    }

    public List<String> getExcludedExtensions() {
        return excludedExtensions;
    }

    public void setExcludedExtensions(List<String> excludedExtensions) {
        this.excludedExtensions = excludedExtensions;
    }

    public List<String> getIncludedExtensions() {
        return includedExtensions;
    }

    public void setIncludedExtensions(List<String> includedExtensions) {
        this.includedExtensions = includedExtensions;
    }

    public List<String> getExcludedFiles() {
        return excludedFiles;
    }

    public void setExcludedFiles(List<String> excludedFiles) {
        this.excludedFiles = excludedFiles;
    }

    public List<String> getIncludedFiles() {
        return includedFiles;
    }

    public void setIncludedFiles(List<String> includedFiles) {
        this.includedFiles = includedFiles;
    }

    public void addExcludedExtension(String ext) {
        if (excludedExtensions == null) {
            excludedExtensions = new ArrayList<>();
        }
        excludedExtensions.add(ext);
    }

    public void addIncludedExtension(String ext) {
        if (includedExtensions == null) {
            includedExtensions = new ArrayList<>();
        }
        includedExtensions.add(ext);
    }

    public void addExcludedFile(String file) {
        if (excludedFiles == null) {
            excludedFiles = new ArrayList<>();
        }
        excludedFiles.add(file);
    }

    public void addIncludedFile(String file) {
        if (includedFiles == null) {
            includedFiles = new ArrayList<>();
        }
        includedFiles.add(file);
    }

    public void addExcludedExtensions(List<String> list) {
        if (excludedExtensions == null) {
            excludedExtensions = new ArrayList<>();
        }
        excludedExtensions.addAll(list);
    }

    public void addIncludedExtensions(List<String> list) {
        if (includedExtensions == null) {
            includedExtensions = new ArrayList<>();
        }
        includedExtensions.addAll(list);
    }

    public void addExcludedFiles(List<String> list) {
        if (excludedFiles == null) {
            excludedFiles = new ArrayList<>();
        }
        excludedFiles.addAll(list);
    }

    public void addIncludedFiles(List<String> list) {
        if (includedFiles == null) {
            includedFiles = new ArrayList<>();
        }
        includedFiles.addAll(list);
    }

    public void addExcludeFromGitignore() {
        // TODO: hacer la implementacion
    }

}
