package es.ciudadescolar.persistencia.dao;

import java.util.List;

import es.ciudadescolar.dominio.modelo.Alumno;
import es.ciudadescolar.dominio.modelo.Examen;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

public class ExamenDAO {
    private final EntityManager em;

    public ExamenDAO(EntityManager em) {
        this.em = em;
    }

    public void guardarExamen(Examen ex) {
        em.persist(ex);
    }

    public void eliminarExamen(Examen ex) {
        em.remove(ex);
    }

    public void actualizarExamen(Examen ex) {
        em.merge(ex);
    }

    //En caso de querer usar el examen con persistencia
    // public Examen actualizarExamen(Examen ex) {
    //     return em.merge(ex);
    // }

    public Examen buscarPorId(Long idExamen){
        return em.find(Examen.class, idExamen);
    }

    public List<Examen> buscarPorAlumno(Alumno alumnoBuscar){
        TypedQuery<Examen> query = em.createQuery("SELECT e from Examen e WHERE e.alumno = :al", Examen.class);
        query.setParameter("al", alumnoBuscar);
        return query.getResultList();
    }

}
