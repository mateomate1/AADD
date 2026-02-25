# Relaciones en Hibernate

Resumen práctico de cómo se colocan las anotaciones en relaciones OneToMany, ManyToOne y OneToOne.

## @OneToMany – Uno a Muchos

### Idea

Una entidad padre tiene una colección de entidades hijas.

Ejemplo:
Un Usuario tiene muchos Pedido.

### Se coloca en la clase PADRE

````java
@Entity
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Pedido> pedidos;

}
````

### Claves importantes

``mappedBy`` → obligatorio si la relacion es bidireccional.

mappedBy apunta al atributo en la clase hija.

No lleva ``@JoinColumn`` aqui cuando hay mappedBy.

El lado OneToMany NO suele ser el propietario.

## @ManyToOne – Muchos a Uno

### Idea

Muchos objetos apuntan a uno.

Ejemplo:
Muchos Pedido pertenecen a un Usuario.

### Se coloca en la clase HIJA

````java
@Entity
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

}
````

### Claves importantes

Aqui SI va @JoinColumn.

Este es el lado propietario.

Es el que tiene la clave foranea en la base de datos.

🔹 3️⃣ @OneToOne – Uno a Uno

Puede ser:

Unidireccional

Bidireccional

📍 Unidireccional

Solo una clase conoce la relacion.

@Entity
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "perfil_id")
    private Perfil perfil;

}

Lleva @JoinColumn.

Es el lado propietario.

📍 Bidireccional

Una clase es propietaria, la otra usa mappedBy.

Clase propietaria:

@Entity
public class Usuario {

    @Id
    private Long id;

    @OneToOne
    @JoinColumn(name = "perfil_id")
    private Perfil perfil;

}

Clase inversa:

@Entity
public class Perfil {

    @Id
    private Long id;

    @OneToOne(mappedBy = "perfil")
    private Usuario usuario;

}
🧠 Resumen visual rapido
Relacion Donde va @JoinColumn Donde va mappedBy Lado propietario
OneToMany En ManyToOne En OneToMany ManyToOne
ManyToOne Aqui No lleva ManyToOne
OneToOne En el propietario En el inverso El que tiene FK
⚠ Regla clave de examen

La clave foranea SIEMPRE esta en:

@ManyToOne

El lado propietario de @OneToOne

Nunca en el OneToMany.
