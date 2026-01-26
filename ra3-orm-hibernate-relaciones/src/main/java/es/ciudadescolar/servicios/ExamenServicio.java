package es.ciudadescolar.servicios;

import java.time.LocalDate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.ciudadescolar.dominio.modelo.Alumno;
import es.ciudadescolar.dominio.modelo.Examen;
import es.ciudadescolar.persistencia.dao.AlumnoDAO;
import es.ciudadescolar.persistencia.dao.ExamenDAO;
import es.ciudadescolar.util.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

public class ExamenServicio {
    private static final Logger log = LoggerFactory.getLogger(ExamenServicio.class);

    public void aniadirExamenAAlumno(Long idAlumno, String modulo, LocalDate fecha, Double nota){
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction trans = em.getTransaction();

        try
        {
            trans.begin();

            // Podriamos tener varios DAOs, todos ellos con el mismo EntityManager y bajo la misma transacción
            AlumnoDAO alumnoDAO = new AlumnoDAO(em);
            ExamenDAO examenDAO = new ExamenDAO(em);

            // 1º BuscaMOS alumno existente o crear
            Alumno alumno = alumnoDAO.buscarPorId(idAlumno);
            if (alumno == null)
            {
                log.warn("El alumno al que añadir examen no existe.");
                trans.rollback();
                return;
            }

            Examen examen = new Examen();
            examen.setModulo(modulo);
            examen.setFecha(fecha);
            examen.setNota(nota);

            // Nosotros debemos relacionar las entidades alumno y examen
            examen.setAlumno(alumno);
            alumno.aniadirExamen(examen);

            examenDAO.guardarExamen(examen);

            trans.commit();

            //OJO en relaciones bidir que en el toString no sea un bucle infinito
            log.info("Alumno registrado: " + alumno.toString());
        }
        catch (RuntimeException e){
            log.error("Error durante la transaccion " + e.getMessage());
        }
    }
}
