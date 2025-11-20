package es.ciudadescolar.Util;

import java.io.File;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;

import es.ciudadescolar.Instituto.Alumno;
import es.ciudadescolar.Instituto.Instituto;

public class BBDDOOManager {

    private final Logger log = LoggerFactory.getLogger(BBDDOOManager.class);
    private ObjectContainer db = null;
    private File ficherodb = null;

    public BBDDOOManager(File fichero, boolean sobreescribir){
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
    public void matricularAlumno(Alumno al) {
    }

    public void expulsarAlumno(Alumno al) {
    }

    public boolean cambiarIdInstituto(int nuevoId) {
        return true;
    }

    public void guardarAlumno(Alumno al) {
    }

    public List<Alumno> getTodosAlumnos(String nombreInstituto) {
        return null;
    }

    public boolean guardarInstituto(Instituto insti) {
        return true;
    }

    public Instituto getInstituto(Instituto institutoBuscado) {
        return null;
    }

    public boolean borrarInstituto(Instituto institutoABorrar) {
        return true;
    }

    public boolean expulsarATodosAlumnos(String nombreInstituto) {
        return true;
    }

    public Instituto consultaMatriculasInstituto(String idInstituto) {
        return null;
    }

    public Instituto consultaInstiMatriculado(String nomAlumno) {
        return null;
    }

}
