package es.ciudadescolar.dominio.modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

/** 
 * Nunca meter los métodos de guardar, modificar o consultar un alumno en esta clase simulando un DAO.
 * Es una mala práctica porque viola el principio "Single Responsibility Principle", es decir haría dos cosas:
 * - Representar un alumno
 * - Acceder a la base de datos
 * Debemos crear otra clase auxiliar (AlumnoRepositorio o AlumnoDAO) donde implementemos los métodos de interacción con la clase Alumno.
 * 
 * Para poder gestionar transacciones que aglutinen la invocación de varios métodos que interactuen con la BD, siempre deberíamos
 * evitar crear y cerrar EntityManager en esos métodos (error de diseño), debemos pasarle al constructor un EntityManager ya creado en 
 * una clase superior (AlumnoService o Main) que haga labores de orquestador. 
 * 
 * En la capa de servicio es donde se cierran los EntityManager. Usamos uno por cada caso de uso. Porque ahí es donde:
 * - Empieza la transacción
 * - Termina la transacción
 * - Se sabe cuándo acaba el trabajo
 * 
 * En el Main como orquestador final de la aplicación es donde debemos cerrar el EntityManagerFactory.
*/

@Entity
@Table(name = "alumno")
public class Alumno implements Serializable
{

    private static final long serialVersionUID = 1L;
    
    // Por tratarse de la PK simple (!= compuesta) y además querer que sea auto_increment
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    // la columna se llamará IGUAL que el atributo y será OBLIGATORIO
    @Column(name="nombre", nullable = false)
    private String nombre;

    // la columna se llamará distinto del atributo y será unique
    @Column(name="correoElectronico", unique = true)
    private String email;

    /**
     * anotación para reflejar la relación 1:1 entre Alumno y Direccion
     * explícitamente indico que al recuperar un alumno quiero traerme también su direccion (EAGER)
     * cualquier operación (persist, remove, refresh, detach,merge) que haga en alumno quiero 
     * que se haga automáticament en su dirección (CASCADE ALL)
     * Si una dirección se queda "huerfana", ORM la borra automáticamente (orphanRemoval): 
     *                  solo si esa dirección está adiministrada
     * 
     * la anotación @JoinColumn en esta entidad indica que es aquí donde está la FK y por tanto consideramos 
     * que sea esta entidad, la entidad padre/owner de la relación. 
     *
     * Para asegurar una relación 1:1, fijaremos la propiedad UNIQUE a true de forma que evitemos un N:1 encubierto.
     * Si no ponemos UNIQUE, la BD y JPA permitiría asignar una misma dirección a dos alumnos distintos:
     *      alumno1.setDireccion(dir);
     *      alumno2.setDireccion(dir);
     * 
     * Para asegurar que podemos guardar un alumno sin dirección, debemos también poner la propiedad NULLABLE a true
     * sabiendo que en H2, MySQL, PostgreSQL, Oracle: UNIQUE permite múltiples NULL
     */
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "direccion", unique = true, nullable = true)
    private Direccion direc;

    /* Dirección es un dato accesorio pero Expediente es una entidad de negocio. */
    /* la FK decido meterla en expediente en este caso */
    @OneToOne(mappedBy = "alumno", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Expediente expediente;

    /**
     * Anotación para reflejar la relación 1:N entre Alumno y Examen (estamos en el lado del "uno")
     * implícitamente JPA utiliza fetch LAZY del lado del "uno", es decir, al recuperar un alumno 
     * no tendré sus exámenes salvo que posteriormente haga un getExamenes(). Pero como siempre, mejor
     * ser explícitos.
     * 
     * En principio, solo las operaciones persist y remove que haga en alumno quiero 
     * que se haga automáticamente en sus exámenes.
     * 
     * La colección NO es “un hijo”, cada elemento de la lista es un hijo independiente por lo que también tendría
     * sentido añadir orphanRemoval = true de forma que si elimino la relación de un alumno con un examen, el examen 
     * se borre automáticamente.
     *
     */
    @OneToMany(mappedBy = "alumno", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval=true)
    private List<Examen> examenes = new ArrayList<Examen>();
   
    /* Descomponemos la relación @ManyToMany que teníamos cuando la relación no tenía atributos adicionales
     en un @OnToMany desde alumno y un @ManyToOne desde Matricula
     */
    
    @OneToMany(mappedBy = "alumno", cascade = CascadeType.PERSIST,fetch = FetchType.LAZY)
    private Set<Matricula> modulosMatriculados = new HashSet<Matricula>();

    public Alumno(){}

    public Alumno(String nombre, String email) {
        this.nombre = nombre;
        this.email = email;
    }

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

        public Direccion getDirec() {
        return direc;
    }

    public void setDirec(Direccion direc) {
        this.direc = direc;
    }

    public Expediente getExpediente() {
        return expediente;
    }

    public void setExpediente(Expediente expediente) {
        this.expediente = expediente;
    }

        public List<Examen> getExamenes() {
        return examenes;
    }

    public void setExamenes(List<Examen> examenes) {
        this.examenes = examenes;
    }

    public void aniadirExamen(Examen ex)
    {
       // if(examenes == null)
       //      this.examenes = new ArrayList<Examen>();
        
        examenes.add(ex);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
        result = prime * result + ((email == null) ? 0 : email.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Alumno other = (Alumno) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (nombre == null) {
            if (other.nombre != null)
                return false;
        } else if (!nombre.equals(other.nombre))
            return false;
        if (email == null) {
            if (other.email != null)
                return false;
        } else if (!email.equals(other.email))
            return false;
        return true;
    }
    
    @Override
    public String toString() {
        return "Alumno [id=" + id + ", nombre=" + nombre + ", email=" + email + ", direc=" + direc + "]";
    } 
    
    public boolean aniadirModulo(Matricula mod)
    {
        return modulosMatriculados.add(mod);
    }

    public boolean quitarModulo(Matricula mod)
    {
        return modulosMatriculados.remove(mod);
    }
}
