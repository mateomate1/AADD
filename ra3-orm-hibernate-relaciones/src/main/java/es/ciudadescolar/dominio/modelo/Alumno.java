package es.ciudadescolar.dominio.modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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
 * Nunca meter los métodos de guardar, modificar o consultar un alumno en esta
 * clase simulando un DAO.
 * Es una mala práctica porque viola el principio "Single Responsibility
 * Principle", es decir haría dos cosas:
 * - Representar un alumno
 * - Acceder a la base de datos
 * Debemos crear otra clase auxiliar (AlumnoRepositorio o AlumnoDAO) donde
 * implementemos los métodos de interacción con la clase Alumno.
 * 
 * Para poder gestionar transacciones que aglutinen la invocación de varios
 * métodos que interactuen con la BD, siempre deberíamos
 * evitar crear y cerrar EntityManager en esos métodos (error de diseño),
 * debemos pasarle al constructor un EntityManager ya creado en
 * una clase superior (AlumnoService o Main) que haga labores de orquestador.
 * 
 * En la capa de servicio es donde se cierran los EntityManager. Usamos uno por
 * cada caso de uso. Porque ahí es donde:
 * - Empieza la transacción
 * - Termina la transacción
 * - Se sabe cuándo acaba el trabajo
 * 
 * En el Main como orquestador final de la aplicación es donde debemos cerrar el
 * EntityManagerFactory.
 */

@Entity
@Table(name = "alumno")
public class Alumno implements Serializable {

    private static final long serialVersionUID = 1L;

    // Por tratarse de la PK simple (!= compuesta) y además querer que sea
    // auto_increment
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    // la columna se llamará IGUAL que el atributo y será OBLIGATORIO
    @Column(name = "nombre", nullable = false)
    private String nombre;

    // la columna se llamará distinto del atributo y será unique
    @Column(name = "correoElectronico", unique = true)
    private String email;

    /**
     * anotación para reflejar la relación 1:1 entre Alumno y Direccion
     * explícitamente indico que al recuperar un alumno quiero traerme también su
     * direccion (EAGER)
     * cualquier operación (persist, remove, refresh, detach,merge) que haga en
     * alumno quiero
     * que se haga automáticament en su dirección (CASCADE ALL)
     * Si una dirección se queda "huerfana", ORM la borra automáticamente
     * (orphanRemoval):
     * solo si esa dirección está adiministrada
     * 
     * la anotación @JoinColumn en esta entidad indica que es aquí donde está la FK
     * y por tanto consideramos
     * que sea esta entidad, la entidad padre/owner de la relación.
     *
     * Para asegurar una relación 1:1, fijaremos la propiedad UNIQUE a true de forma
     * que evitemos un N:1 encubierto.
     * Si no ponemos UNIQUE, la BD y JPA permitiría asignar una misma dirección a
     * dos alumnos distintos:
     * alumno1.setDireccion(dir);
     * alumno2.setDireccion(dir);
     * 
     * Para asegurar que podemos guardar un alumno sin dirección, debemos también
     * poner la propiedad NULLABLE a true
     * sabiendo que en H2, MySQL, PostgreSQL, Oracle: UNIQUE permite múltiples NULL
     */
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "direccion", unique = true, nullable = true)
    private Direccion direc;

    /*
     * Mappedby usa el nombre del atributo de la clase que mapea, en este caso el
     * atributo de Examen que contiene los alumnos se llama alumno, si en la clase
     * se llamara x y en la bdd se llamara y usariamos x
     *
     * Se mapea en lqa entidad padre que es la entidad que contiene la foreing key
     * en la bdd
     */
    @OneToMany(mappedBy = "alumno", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private List<Examen> examenes;

    public Alumno() {
        this.examenes = new ArrayList<>();
    }

    public Alumno(String nombre, String email) {
        this.nombre = nombre;
        this.email = email;
        this.examenes = new ArrayList<>();
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

    public List<Examen> getExamenes() {
        return examenes;
    }

    public void setExamenes(List<Examen> examenes) {
        this.examenes = examenes;
    }

    public void aniadirExamen(Examen ex){
        if(examenes == null)
            this.examenes = new ArrayList<>();
        this.examenes.add(ex);
    }

}
