package es.ciudadescolar.navidad25_rest.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

/**
 * Clase Nino que representa un niño que escribe cartas a Papanoel y recibe
 * regalos entregados por Elfos
 * Un niño puede recibir varias unidades de un mismo regalo, por ejemplo, dos
 * ositos de peluche
 */

@Entity
@Table(name = "ninos", schema = "navidad25")
public class Nino implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_nino")
    private Long id;

    @Column(name = "nombre")
    private String nombre;

    // tipo enum: "clase" especial que representa un grupo de constantes.
    public static enum Comportamiento {
        bueno, malo, regular
    }

    // atributo de instancia con el valor por defecto alineado a la definión del
    // campo en la BD
    @Column(name = "comportamiento")
    @Enumerated(EnumType.STRING)
    private Comportamiento comportamiento = Comportamiento.regular;

    // recupero eager porque luego en el toString() fuera de hibernate
    // 2026-02-08 12:47:03,481 [main] ERROR
    // org.springframework.boot.SpringApplication - Application run failed
    // org.hibernate.LazyInitializationException: Cannot lazily initialize
    // collection of role 'es.ciudadescolar.navidad25.model.Nino.cartasPapaNoel'
    // with key '1' (no session)
    @OneToMany(mappedBy = "nino", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Carta> cartasPapaNoel = new ArrayList<>();

    public Nino() {
    }

    public Nino(String nom, Comportamiento comport) {
        this.nombre = nom;
        this.comportamiento = comport;
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

    /**
     * El getter devuelve el enum, no un String!!
     * Los enum se comparan siempre con ==, no con equals.
     * Haz esto para comparar:
     * if (nino.getComportamiento() == Nino.Comportamiento.BUENO) {
     * System.out.println("Es bueno"); }
     * No hagas esto nunca para comparar:
     * if (p.getComportamiento() == "BUENO")
     * 
     * Puedes recuperar el literal si quieres así:
     * String comportamientoTexto = nino.getComportamiento().name();
     * 
     * @return
     */
    public Comportamiento getComportamiento() {
        return comportamiento;
    }

    /**
     * Para fijar un valor válido a comportamiento puedes usar
     * Nino nino = new Nino();
     * nino.setComportamiento(Nino.Comportamiento.BUENO);
     * 
     * Si recuperamos de la BD el valor de se campo:
     * String valorBD = "malo";
     * Puedes usar algo como:
     * Comportamiento c = Comportamiento.valueOf(valorBD.toUpperCase());
     * nino.setComportamiento(c);
     * 
     * @exception IllegalArgumentException Si el valor fijado no coincide con los
     *                                     valores del enum
     * @param comportamiento
     */
    public void setComportamiento(Comportamiento comportamiento) {
        this.comportamiento = comportamiento;
    }

    public void aniadirCarta(Carta carta) {
        cartasPapaNoel.add(carta);
        carta.setNino(this);
    }

    public void eliminarCarta(Carta carta) {
        cartasPapaNoel.remove(carta);
        carta.setNino(null);
    }

    public List<Carta> getCartasPapaNoel() {
        return cartasPapaNoel;
    }

    public void setCartasPapaNoel(List<Carta> cartasPapaNoel) {
        this.cartasPapaNoel = cartasPapaNoel;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
        result = prime * result + ((comportamiento == null) ? 0 : comportamiento.hashCode());
        result = prime * result + ((cartasPapaNoel == null) ? 0 : cartasPapaNoel.hashCode());
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
        Nino other = (Nino) obj;
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
        if (comportamiento != other.comportamiento)
            return false;
        if (cartasPapaNoel == null) {
            if (other.cartasPapaNoel != null)
                return false;
        } else if (!cartasPapaNoel.equals(other.cartasPapaNoel))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Nino [id=" + id + ", nombre=" + nombre + ", comportamiento=" + getComportamiento() + ", num cartas="
                + cartasPapaNoel.size() + "]";
    }
}
