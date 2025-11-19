package es.ciudadescolar.Util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;

import es.ciudadescolar.Instituto.Alumno;

public class Bbddoo {
    private final Logger log = LoggerFactory.getLogger(Bbddoo.class);
    private ObjectContainer db = null;
    private File ficherodb = null;

    public Bbddoo(File fichero, boolean sobreescribir) {
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

    public boolean cerrar() {
        if (db != null) {
            db.close();
            return true;
        }
        return false;
    }

    public boolean guardarAlumno(Alumno alumno) {
        boolean estado = false;
        if (db != null) {
            db.store(alumno);
            log.info("Alumno {" + alumno.toString() + "} guardado.");
            estado = !estado;
        }
        return estado;
    }

    public List<Alumno> recuperarTodosAlumnos() {
        // Preparamos una plantilla de un alumno
        List<Alumno> alumnos = new ArrayList<>();
        Alumno alumnoBuscado = new Alumno(null, null, null);
        ObjectSet<Alumno> alumnosRecuperados = db.queryByExample(alumnoBuscado);
        if (alumnosRecuperados.size() > 0) {
            for (Alumno alumno : alumnosRecuperados) {
                alumnos.add(alumno);
                log.info("Alumno recuperado: " + alumno);
            }
        } else {
            log.warn("No se han recuperado alumnos de la base de datos.");
        }
        return alumnos;
    }

    public Alumno recuperarAlumnoPorExpediente(String expediente) {
        // Preparamos una plantilla de un alumno
        Alumno alumnoBuscado = new Alumno(expediente, null, null);
        Alumno alumnoRecuperado = null;
        ObjectSet<Alumno> alumnosRecuperados = db.queryByExample(alumnoBuscado);
        if (db != null) {
            if (alumnosRecuperados.size() == 1) {
                alumnoRecuperado = alumnosRecuperados.next();
            }
        }
        return alumnoRecuperado;
    }

    public boolean borrarAlumno(Alumno alumno) {
        boolean status = false;
        ObjectSet<Alumno> alumnosRecuperados = null;
        if (db != null) {
            alumnosRecuperados = db.queryByExample(alumno);
            int size = alumnosRecuperados.size();
            if (size == 1) {
                db.delete(alumno);
                log.info("Alumno {" + alumno.toString() + "} eliminado.");
                status = !status;
            } else {
                if (size == 0) {
                    log.warn("No se ha encontrado el alumno {" + alumno.toString() + "} para eliminar.");
                } else
                    log.error("Se han encontrado varios alumnos {" + alumno.toString() + "} para eliminar.");
            }
        }
        return status;
    }

    public boolean borrarTodosAlumnos() {
        boolean status = false;
        List<Alumno> alumnos = recuperarTodosAlumnos();
        if (db != null) {
            for (Alumno alumno : alumnos) {
                db.delete(alumno);
                log.info("Alumno {" + alumno.toString() + "} eliminado.");
            }
            status = true;
        }
        return status;
    }

    public boolean modificarEdadAlumno(Alumno alumnoModificar, Integer nuevaEdad) {
        boolean status = false;
        ObjectSet<Alumno> alumnosModificar = db.queryByExample(alumnoModificar);
        Alumno alumno = null;
        int totalEncontrados = alumnosModificar.size();
        switch (totalEncontrados) {
            case 0: log.warn("No se encontro el alumno a modificar");
                break;
            case 1:
                alumno = alumnosModificar.next();
                alumno.setEdad(nuevaEdad);
                db.store(alumno);
                log.info("Alumno modificado: " + alumno.toString());
                status = true;
                break;
            default:
                log.error("Se han encontrado varios alumnos a modificar");
        }
        return status;
    }
}
