package es.ciudadescolar.dominio.modelo;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 *  Al tratarse de una entidad débil, no tengo que crear un DAO y un SERVICE propio
 */

@Entity
@Table (name = "direccion")
public class Direccion implements Serializable
{

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cod_dir")
    private Long idDireccion;

    @Column(name="calle", nullable = false)
    private String calle;

    @Column(name="numero", nullable = false)
    private Integer num;

    public Direccion()    {   }

    public Direccion(String calle, Integer numero)
    {
        this.calle = calle;
        this.num = numero;
    }

    

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public Long getIdDireccion() {
        return idDireccion;
    }

    public void setIdDireccion(Long idDireccion) {
        this.idDireccion = idDireccion;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((idDireccion == null) ? 0 : idDireccion.hashCode());
        result = prime * result + ((calle == null) ? 0 : calle.hashCode());
        result = prime * result + ((num == null) ? 0 : num.hashCode());
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
        Direccion other = (Direccion) obj;
        if (idDireccion == null) {
            if (other.idDireccion != null)
                return false;
        } else if (!idDireccion.equals(other.idDireccion))
            return false;
        if (calle == null) {
            if (other.calle != null)
                return false;
        } else if (!calle.equals(other.calle))
            return false;
        if (num == null) {
            if (other.num != null)
                return false;
        } else if (!num.equals(other.num))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Direccion [idDireccion=" + idDireccion + ", calle=" + calle + ", num=" + num + "]";
    }

    

    
    
    
}
