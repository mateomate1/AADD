package com.cheatsheet.Util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cheatsheet.Objeto;
import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;

public class BBDDOOManager {
    private final Logger log = LoggerFactory.getLogger(BBDDOOManager.class);

    private ObjectContainer db = null;
    private File ficherodb = null;

    /**
     * Metodo que inicializa la clase BBDDOOManager inicializando tmb el contenedor
     * de la base de datos ObjectContainer
     *
     * @param fichero
     * @param sobreescribir
     */
    public BBDDOOManager(File fichero, boolean sobreescribir) {
        this.ficherodb = fichero;
        if (sobreescribir) {
            if (ficherodb.exists()) {
                log.warn("Se procede a eliminar la base de datos");
                ficherodb.delete();
            }
        }

        db = Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(), ficherodb.getName());
        log.debug("Se ha inicializado la bdd");
    }

    /**
     * Metodo que cierra la base de datos, es decir la conexion al archivo q
     * contiene los datos de esta
     *
     * @return
     */
    public boolean cerrar() {
        if (db != null) {
            db.close();
            return true;
        }
        return false;
    }

    public boolean guardarAlumno(Objeto objeto) {
        boolean estado = false;
        if (db != null) {
            db.store(objeto);
            log.info("Objeto {" + objeto.toString() + "} guardado.");
            estado = !estado;
        }
        return estado;
    }

    public List<Objeto> recuperarObjetosGenericos() {
        // Preparamos una plantilla de un alumno
        List<Objeto> objetos = new ArrayList<>();
        // Usamos un objeto vacio del tipo que queremos recuperar para que la bbdd
        // busque cualquiero objeto del mismo tipo
        Objeto objetoBuscado = new Objeto(null, null);
        ObjectSet<Objeto> objetosRecuperados = db.queryByExample(objetoBuscado);
        if (objetosRecuperados.size() > 0) {
            for (Objeto objeto : objetosRecuperados) {
                objetos.add(objeto);
            }
        }
        return objetosRecuperados;
    }

    public List<Objeto> recuperarObjetosEspecifico(String dato1) {
        // Preparamos una plantilla de un alumno
        List<Objeto> objetos = new ArrayList<>();
        // Al usar un objeto que contiene uno de los atributos rellenos la bbdd nos
        // devolvera objetos del mismo tipo que contengan los atributos que coincidad si esta relleno
        Objeto objetoBuscado = new Objeto(dato1, null);
        ObjectSet<Objeto> objetosRecuperados = db.queryByExample(objetoBuscado);
        if (objetosRecuperados.size() > 0) {
            for (Objeto objeto : objetosRecuperados) {
                objetos.add(objeto);
            }
        }
        return objetosRecuperados;
    }
}
