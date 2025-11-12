package com.practicas;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.practicas.Objetos.Empresa;
import com.practicas.Util.JsonManager;

public class Main {
    private static final Logger log = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        Empresa empresa = JsonManager.getEmpresa(new File("extras.json"));
        log.trace(empresa.toString());
        JsonManager.writeEmpresa(empresa, new File("fusionado.json"));
    }
}