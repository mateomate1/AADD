package com.mateomate1;

import java.io.File;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        String pathname = "C:\\Users\\Alumno\\Desktop\\DAM 2.2\\Repos aparte\\demo-practica-voluntaria-mateomate1";
        File raiz = new File(pathname);
        File destino = new File("arbol.txt");
        VisorCarpetasAscii v = new VisorCarpetasAscii();
        List<String> extensiones = List.of("java", "txt", "md"); // Example extensions
        v.generarArbol(raiz, destino, extensiones);
    }
}