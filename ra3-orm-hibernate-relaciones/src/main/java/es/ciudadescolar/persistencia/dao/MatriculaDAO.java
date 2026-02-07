package es.ciudadescolar.persistencia.dao;

import java.util.List;

import es.ciudadescolar.dominio.modelo.ClaveMatricula;
import es.ciudadescolar.dominio.modelo.Matricula;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

public class MatriculaDAO 
{
    private final EntityManager em;

    public MatriculaDAO(EntityManager em) {
        this.em = em;
    }
    
     public void guardar(Matricula matricula) {
        em.persist(matricula);
    }

    public void eliminar(Matricula matricula) {
            em.remove(matricula);
    }

    public void actualizar(Matricula matricula) {
        em.merge(matricula);
    }    

    public Matricula buscarPorId(ClaveMatricula id) {
        return em.find(Matricula.class, id);
    }

    public List<Matricula> buscarPorAlumno(Long idAlumno)
    {
        TypedQuery<Matricula> consulta = em.createQuery("SELECT m FROM Matricula m WHERE m.alumno.id = :idAl", Matricula.class);
        consulta.setParameter("idAl", idAlumno);
        List<Matricula> lista = consulta.getResultList();
        if (lista.isEmpty())
            return null;
        else
            return lista;
    }

    public List<Matricula> buscarPorModulo(Long idModulo)
    {
        TypedQuery<Matricula> consulta = em.createQuery("SELECT m FROM Matricula m WHERE m.modulo.codigo = :idcod", Matricula.class);
        consulta.setParameter("idcod", idModulo);
        List<Matricula> lista = consulta.getResultList();
        if (lista.isEmpty())
            return null;
        else
            return lista;
    }

    public boolean existeMatricula(Long idAlumno, Long idModulo)
    {
        // JPQL count devuelve Long. 
        // https://jakarta.ee/specifications/persistence/3.2/jakarta-persistence-spec-3.2#a5538
        TypedQuery<Long> consulta = em.createQuery("SELECT COUNT(m) FROM Matricula m WHERE m.alumno.id =:idAl AND m.modulo.codigo = :idMod", Long.class);
        consulta.setParameter("idAl", idAlumno);
        consulta.setParameter("idMod", idModulo);

        Long totalMatriculas = consulta.getSingleResult();

        return totalMatriculas > 0;

    }
}
