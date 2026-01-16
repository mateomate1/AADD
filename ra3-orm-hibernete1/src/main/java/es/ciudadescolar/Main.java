package es.ciudadescolar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.ciudadescolar.Instituto.Alumno;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

public class Main {
    private static final Logger log = LoggerFactory.getLogger(Main.class);
    //PersistenciaInstituto es el nombre que recibe en el xml
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("PersistenciaInstituto");
    public static void main(String[] args) {
        //La instancia 'al' esta en estado transitorio(en la ram, si apago lo pierdo)
        Alumno al = new Alumno("Juan", "juan_._dos@ciudadescolar.es");

        guardarAlumno(al);

        actualizarAlumno(1L, "juan2@ciudadescolar.es");

        if(emf != null && emf.isOpen())
            emf.close();
        
    }

    private static boolean actualizarAlumno(Long id_alumno, String nuevoCorreo){
        boolean estado = true;
        EntityManager em = null;
        EntityTransaction trans = null;
        Alumno alBuscado = null;
        try {
            em = emf.createEntityManager();
            trans = em.getTransaction();

            trans.begin();
            //Logica de ejecucion

            //Find busca por PK y devuielve la instancia administrada
            alBuscado = em.find(Alumno.class, id_alumno);

            if(alBuscado != null){
                alBuscado.setEmail(nuevoCorreo);
            }
            trans.commit();
        } catch (RuntimeException e) {
            trans.rollback();
            estado = false;
        } finally {
            if(em != null && em.isOpen())
                em.close();
        }
        return estado;
    }
    
    private static boolean guardarAlumno(Alumno al){
        boolean estado = true;
        
        EntityManager em = null;
        EntityTransaction trans = null;
    
    
        try {
            //Le pedimos al pool de conexiones una conexion existente sin usar
            em = emf.createEntityManager();
            trans = em.getTransaction();
            //Siempre necesito una transaccion para modificar la BD(alta, baja y modificacion)
            trans.begin();
    
            em.persist(al);
            log.debug("la instancia de alumno pasa a estar administrada o persistente");
            //
    
            //Al hacer un commit hace falta hacer un begin transacction
            trans.commit();
            log.debug("El estado de la instancia se ha guardado en la DB");
            /*
            A partir de aqui la instancia pasa a estar separada(detached)
            Cambios posteriores no se vuelcan en la DB
            */
            
        } catch (RuntimeException e) {
            estado = false;
            log.error("Error durante la persistencia de datos: " + e.getMessage());
            if(trans != null && trans.isActive()){
                trans.rollback();
                log.debug("Se ha llevado a cabo el rollback de la transaccion");
            } else
                log.debug("La transaccion no estaba abierta");
        } finally{
            if(em!= null && em.isOpen()){
                em.close();
                log.debug("Se ha cerrado la conexion en la DB");
            } if(emf != null && emf.isOpen()){
                emf.close();
                log.debug("Se ha cerrado el pull de conexiones con la DB");
            }
        }

        return estado;
    }
}