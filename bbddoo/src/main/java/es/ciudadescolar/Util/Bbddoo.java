package es.ciudadescolar.Util;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;

public class Bbddoo {
    private final Logger log = LoggerFactory.getLogger(Bbddoo.class);
    private ObjectContainer db = null;
    private File ficherodb = null;

    public Bbddoo(File fichero, boolean sobreescribir){
        this.ficherodb = fichero;
        if(sobreescribir){
            if(ficherodb.exists()){
                log.warn("Se procede a eliminar la base de datos");
                ficherodb.delete();
            }
        }

        db = Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(), ficherodb.getName());
        log.debug("Se ha inicializado la bdd");
    }

    public boolean cerrar(){
        if(db != null){
            
        }
        return false;
    }
}
