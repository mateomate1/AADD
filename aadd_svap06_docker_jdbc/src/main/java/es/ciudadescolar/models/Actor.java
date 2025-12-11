package es.ciudadescolar.models;

public class Actor {
    // id-first_name last_name
    private Integer id;
    private String first_name;
    private String last_name;
    private String last_update;

    public Actor() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getLast_update() {
        return last_update;
    }

    public void setLast_update(String last_update) {
        this.last_update = last_update;
    }

    @Override
    public String toString() {
        return "Actor [id=" + id + ", first_name=" + first_name + ", last_name=" + last_name + "]";
    }

    public String INSERT() {
        String salida = "";
        String cabecera = "INSERT INTO actor(";
        String film_id = getId() != null ? "film_id, " : "";
        String first_name = "first_name, ";
        String last_name = "last_name ";
        String values = ") VALUES(";

        salida = cabecera + film_id + first_name + last_name + values + getId() != null ? getId().toString()
                : "" + getFirst_name() + getLast_name();

        return salida;
    }
}
