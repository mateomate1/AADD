package com.example;

import java.io.File;
import java.io.IOException;

import com.mateomate1.FolderStructureTranslator;

public class Main {
    public static void main(String[] args) {
        FolderStructureTranslator f = new FolderStructureTranslator();
        f.generateTree(new File("."));
        try {
            f.flushTree(new File("tree.txt"));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}