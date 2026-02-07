package es.ciudadescolar.persistencia.dao;

import java.util.List;

import es.ciudadescolar.dominio.modelo.Alumno;
import es.ciudadescolar.dominio.modelo.Examen;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

public class ExamenDAO {

     private final EntityManager em;

     public ExamenDAO (EntityManager e){
        this.em = e;
     }

     public void guardarExamen(Examen ex) {
        em.persist(ex);
     }

     public void eliminarExamen (Examen ex){
        em.remove(ex);
     }

     public void actualizarExamen (Examen ex){
        em.merge(ex);
     }

     public Examen buscarPorId (Long idExamen){
        return em.find(Examen.class, idExamen);
     }

     public List<Examen> buscarPorAlumno(Alumno alumnoABuscar)
     {
        TypedQuery<Examen> query = em.createQuery(" SELECT e from Examen e WHERE e.alumno = :al", Examen.class); // JPQL
        query.setParameter("al", alumnoABuscar);
        return query.getResultList();
        
     }

      public List<Examen> listarExamenesPorNota(Double notaMenor, Double notaMayor)
     {
        TypedQuery<Examen> query = em.createQuery(" SELECT e from Examen e WHERE e.nota >=:inf AND e.nota<=:sup", Examen.class); // JPQL
        query.setParameter("inf", notaMenor);
        query.setParameter("sup", notaMayor);
        return query.getResultList();
     }
     
}
