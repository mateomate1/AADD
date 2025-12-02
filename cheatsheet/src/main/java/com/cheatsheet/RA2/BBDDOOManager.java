package com.cheatsheet.RA2;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cheatsheet.Example.Objeto;
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
        // devolvera objetos del mismo tipo que contengan los atributos que coincidad si
        // esta relleno. En caso de ser un tipo de dato como el int debera contener el
        // valor por defecto(int 0, boolean false)
        Objeto objetoBuscado = new Objeto(dato1, null);
        ObjectSet<Objeto> objetosRecuperados = db.queryByExample(objetoBuscado);
        if (objetosRecuperados.size() > 0) {
            for (Objeto objeto : objetosRecuperados) {
                objetos.add(objeto);
            }
        }
        // Otra solucion es hacer lo siguiente:
        if (objetosRecuperados.size() > 0) {
            objetos = objetosRecuperados.subList(0, objetosRecuperados.size());
        }
        return objetosRecuperados;
    }

    public boolean deleteObjeto(Objeto obj) {
        boolean status = false;
        ObjectSet<Objeto> objEncontrados = null;

        if (db != null) {
            objEncontrados = db.queryByExample(obj);
            int coincidencias = objEncontrados.size();
            if (coincidencias == 1) {
                // Si solo encontro 1 se eliina
                db.delete(obj);
                status = true;
            } else if (coincidencias == 0) {
                // Si no encontro objetos, no eliminamos y damos mensaje
                log.warn("No se encontraron coincidencias del objeto");
            } else {
                log.warn("Se encontraron multiples coincidencias, no se borrara nada");
            }
        }

        return status;
    }

    /**
     * Este metodo busca eliminar TODOS los objetos de una CLASE especifica, no toda
     * la bdd
     *
     * @return
     */
    public boolean deleteAllObjetoEspecifico() {
        boolean status = false;
        List<Objeto> objetosObjeto = recuperarObjetosGenericos();
        if (objetosObjeto.size() == 0) {
            log.warn("No se encontraron objetos del tipo objeto en la bdd");
        }
        for (Objeto objeto : objetosObjeto) {
            // Delete solo borra objetos de 1 en 1
            db.delete(objeto);
            log.debug("Borrando objeto: " + objeto);
        }
        return status;
    }

    public boolean modificarObjeto(Objeto objeto_a_modificar, String nuevoDato1) {
        boolean status = false;
        ObjectSet<Objeto> modificar = db.queryByExample(objeto_a_modificar);
        int coincidencias = modificar.size();
        switch (coincidencias) {
            case 0:
                log.warn("No existe el objeto en la BD");
                break;
            case 1: // Como solo hay uno se puede poner next para pasar al primero
                Objeto objeto = modificar.next();
                objeto.setDato1(nuevoDato1);
                db.store(objeto);
                log.info("Se ha actualizado el dato 1 del objeto: " + objeto);
                status = true;
                break;
            default:
                log.warn("No se actualizan varios objetos de una vez");
                break;
        }
        return status;
    }

    /**
     * El metodo solo sirve para guardar los cambios, no hay comando de start
     * transacction, las "transacciones" empiezan al abrir el fichero que contiene la
     * bdd o cuando se ha hecho un commit
     */
    public void commitTransaction() {
        db.commit();
        log.debug("Se consolidan los cambios previos en la BD");
    }

    public void rollbackTransaction() {
        db.rollback();
        log.warn("Se han descartado los cambios previos en la BD");
    }
}
