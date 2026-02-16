package es.ciudadescolar.persistencia.dao;

import java.util.List;

import es.ciudadescolar.dominio.modelo.Alumno;
import es.ciudadescolar.dominio.modelo.Expediente;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

public class ExpedienteDAO 
{
    private final EntityManager em;

    public ExpedienteDAO(EntityManager em) {
        this.em = em;
    }

    public void guardar(Expediente expediente) {
        em.persist(expediente);
    }

    public void eliminar(Expediente expediente) {
        em.remove(expediente);
    }

    public void actualizar(Alumno alumno) {
        em.merge(alumno);
    } 

    public Expediente buscarPorId(Long id) {
        return em.find(Expediente.class, id);
    }

    public Expediente buscarPorAlumnoId(Long idAlumno) {
        TypedQuery<Expediente> consulta = em.createQuery("SELECT e FROM Expediente e WHERE e.alumno.id = :idAlumno",Expediente.class);
        consulta.setParameter("idAlumno", idAlumno);

        List<Expediente> lista = consulta.getResultList();
        
        if (lista.isEmpty())
            return null;
        else
            return lista.get(0); // al ser UNIQUE sólo habrá un único registro

        // Opción elegante: return lista.isEmpty() ? null : lista.get(0);
    }

    
}
