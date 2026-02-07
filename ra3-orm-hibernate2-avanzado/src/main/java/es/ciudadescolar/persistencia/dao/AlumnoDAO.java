package es.ciudadescolar.persistencia.dao;

import java.util.ArrayList;
import java.util.List;

import es.ciudadescolar.dominio.modelo.Alumno;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;

public class AlumnoDAO 
{
    private final EntityManager em;

    public AlumnoDAO(EntityManager em) {
        this.em = em;
    }

    public void guardar(Alumno alumno) {
        em.persist(alumno);
    }

     /**
      * Este método actualiza todos los atributos del objeto en la BD
      * Ojo que si algun atributo no clave está sin informar (null), llevará null a la BD porque
      * merge() no distingue entre “atributo no informado” y “atributo que quiero poner a null”
      * por lo que NUNCA usar con objetos parciales.
      * Si tenemos id sin informar(auto_increment):
      *     merge() con ID null → creará un nuevo registro con ID generado automáticamente.
      *     merge() con ID existente → actualiza la fila, no crea ni cambia ID.
      * 
      * Para actualizar parcialmente (no todos los atributos) → mejor find() + setters, así evitamos sobrescribir con null.
      * @param alumno
      */
    public void actualizar(Alumno alumno) {
        em.merge(alumno);
    }

    /**
     * Este método nos permite cambiar el email de un alumno existente
     * @param idAlumno
     * @param nuevoCorreo
     */
    public void actualizarEmailAlumno (Long idAlumno, String nuevoCorreo)
    {
        Alumno a = em.find(Alumno.class, idAlumno); 
        if (a != null)
            a.setEmail(nuevoCorreo);
    }

    /**
     * Este método nos permite cambiar el nombre de un alumno existente
     * @param idAlumno
     * @param nuevoNombre
     */
    public void actualizarNombreAlumno (Long idAlumno, String nuevoNombre)
    {
        Alumno a = em.find(Alumno.class, idAlumno); 
            if (a != null)
                a.setNombre(nuevoNombre);
    }

    /**
     * Eliminar un alumno a partir de su Id
     * @param idAlumno
     */
    public void eliminarPorId(Long idAlumno) {
        Alumno a = em.find(Alumno.class, idAlumno); // devuelve entidad gestionada o null
        if (a != null) 
            em.remove(a);
    }
    
    /**
     * Eliminar un alumno pasado como parámetro
     * @param idAlumno
     */
    public void eliminar(Alumno alumno) {
        if (!em.contains(alumno)) 
            alumno = em.find(Alumno.class, alumno.getId()); // debe estar administrada para poder ser borrada
        
        if (alumno != null) 
            em.remove(alumno);
    }

    /**
     * Recuperar un alumno (administrado) pasado como parámetro
     * Recordad que una entidad está administrada solo mientras viva el mismo EntityManager que la cargó.
     * Por tanto el EntityManager en cuestión debe seguir abierto
     * @param idAlumno
     * @return instancia administrada del alumno buscado o NULL
     */
    public Alumno buscarPorId(Long id) {
        return em.find(Alumno.class, id);
    }

    /**
     * Recuperar un alumno (administrado) cuyo email (unique) se pasa como parámetro
     * Recordad que una entidad está administrada solo mientras viva el mismo EntityManager que la cargó.
     * Por tanto el EntityManager en cuestión debe seguir abierto
     * @param email
     * @return instancia administrada del alumno buscado o NULL
     */

    public Alumno buscarPorEmail(String email) 
    {
        // email tiene restricción UNIQUE: no pueden haber dos registros con el mismo email...

        TypedQuery<Alumno> consulta= em.createQuery("SELECT a FROM Alumno a WHERE a.email = :email", Alumno.class);

        consulta.setParameter("email", email);

        List<Alumno> lista = consulta.getResultList();

        if (lista.isEmpty())
            return null;
        else
            return lista.get(0); // al ser UNIQUE sólo habrá un único registro

        // Opción elegante: return lista.isEmpty() ? null : lista.get(0);
    }

    /**
     * Recuperar los alumnos (administrados) cuyo nombre (not unique) se pasa como parámetro
     * Recordad que una entidad está administrada solo mientras viva el mismo EntityManager que la cargó.
     * Por tanto el EntityManager en cuestión debe seguir abierto
     * @param nombre
     * @return lista de instancias administradas de los alumnos coincidentes o lista vacía
     */
    public List<Alumno> buscarPorNombre(String nombre) 
    {
        // nombre puede repetirse...  por lo que podría devolverse  0..n resultados.
        
        List<Alumno> lista = null;

        TypedQuery<Alumno> consulta= em.createQuery("SELECT a FROM Alumno a WHERE a.nombre = :nombre", Alumno.class);

        consulta.setParameter("nombre", nombre);

        lista = consulta.getResultList();

        return lista;
    }

     /**
     * Recuperar TODOS los alumnos existentes (administrados)
     * Recordad que una entidad está administrada solo mientras viva el mismo EntityManager que la cargó.
     * Por tanto el EntityManager en cuestión debe seguir abierto
     * @return lista de instancias administradas de los alumnos coincidentes o lista vacía
     */
    public List<Alumno> buscarTodos() 
    {
        TypedQuery<Alumno> consulta = em.createQuery("SELECT a FROM Alumno a", Alumno.class);
        return consulta.getResultList();
    }

    /**
     * Cuando la consulta no devuelve entidades, debemos apoyarnos en Query (consultas no tipadas)
     * Dichas consultas devuelven una lista de tuplas (array de objetos) pues no
     * se conoce el tipo de resultado en tiempo de compilación y por tanto requiere casts manuales
     * @return lista de nombres de TODOS los alumnos o lista vacía 
     */
    public List<String> recuperarNombresAlumnos() 
    {
        List<String> lista = new ArrayList<String>();

        Query consultaNoTipada = em.createQuery("SELECT a.nombre FROM Alumno a");

        List<?> filas = consultaNoTipada.getResultList(); // recupera una lista de arrays de objetos...

        for (Object fila : filas) 
        {
            Object[] arrayFila = (Object[]) fila;
            lista.add((String) arrayFila[0]);
        }
        return lista;
    }
}

