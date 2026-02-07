package es.ciudadescolar.dominio.modelo;

import java.io.Serializable;

import jakarta.persistence.Embeddable;

@Embeddable
public class ClaveMatricula implements Serializable{

     private static final long serialVersionUID = 1L;

     private Long codigoModulo;

     private Long codigoAlumno;

     public ClaveMatricula(){}

     public ClaveMatricula(Long codMod, Long codAl)
     {
        this.codigoAlumno=codAl;
        this.codigoModulo=codMod;
     }

     public Long getCodigoModulo() {
         return codigoModulo;
     }

     public void setCodigoModulo(Long codigoModulo) {
         this.codigoModulo = codigoModulo;
     }

     public Long getCodigoAlumno() {
         return codigoAlumno;
     }

     public void setCodigoAlumno(Long codigoAlumno) {
         this.codigoAlumno = codigoAlumno;
     }

     @Override
     public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((codigoModulo == null) ? 0 : codigoModulo.hashCode());
        result = prime * result + ((codigoAlumno == null) ? 0 : codigoAlumno.hashCode());
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
        ClaveMatricula other = (ClaveMatricula) obj;
        if (codigoModulo == null) {
            if (other.codigoModulo != null)
                return false;
        } else if (!codigoModulo.equals(other.codigoModulo))
            return false;
        if (codigoAlumno == null) {
            if (other.codigoAlumno != null)
                return false;
        } else if (!codigoAlumno.equals(other.codigoAlumno))
            return false;
        return true;
     }

     @Override
     public String toString() {
        return "ClaveMatricula [codigoModulo=" + codigoModulo + ", codigoAlumno=" + codigoAlumno + "]";
     }

     
     
}
