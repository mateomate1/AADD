package com.mateomate1;

import java.io.File;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        FolderStructureTranslator fst = new FolderStructureTranslator();
        String pathname = "C:\\Users\\Mateo\\Documents\\GitHub\\DAM2.2\\Repos Aparte\\demo-practica-voluntaria-mateomate1";
        File folder = new File(pathname);
        File salida = new File("tree.txt");
        FileStructureFilter fsf = new FileStructureFilter();

        fsf.addIncludedExtensions(Arrays.asList("java", "txt"));
        fsf.addExcludedExtension("class");
        fsf.addExcludedFiles(Arrays.asList(".git", ".github", "objects"));

        fst.generarArbol(folder, salida, fsf, null);
    }
}