# ¿Qué es Hibernate?

Framework ORM que mapea **clases Java ↔ tablas relacionales**.
Se basa en la especificación Jakarta Persistence (JPA).

---

## 1. Anotaciones básicas de entidad

### `@Entity`

Marca la clase como entidad persistente.

```java
@Entity
public class Usuario { }
```

---

### `@Table`

Define el nombre de la tabla.

```java
@Table(name = "usuarios")
```

---

### `@Id`

Define la clave primaria.

```java
@Id
private Long id;
```

---

### `@GeneratedValue`

Generación automática de la clave primaria.

```java
@GeneratedValue(strategy = GenerationType.IDENTITY)
```

Estrategias:

* `IDENTITY`
* `AUTO`
* `SEQUENCE`
* `TABLE`

---

### `@Column`

Configura columnas.

```java
@Column(name = "nombre", nullable = false, length = 50)
```

Propiedades importantes:

* `nullable`
* `unique`
* `length`
* `updatable`
* `insertable`

---

## 2. Relaciones

## 🔹 `@OneToOne`

```java
@OneToOne
@JoinColumn(name = "perfil_id")
private Perfil perfil;
```

---

## 🔹 `@OneToMany`

```java
@OneToMany(mappedBy = "usuario")
private List<Pedido> pedidos;
```

---

## 🔹 `@ManyToOne`

```java
@ManyToOne
@JoinColumn(name = "usuario_id")
private Usuario usuario;
```

---

## 🔹 `@ManyToMany`

```java
@ManyToMany
@JoinTable(
    name = "usuario_rol",
    joinColumns = @JoinColumn(name = "usuario_id"),
    inverseJoinColumns = @JoinColumn(name = "rol_id")
)
private List<Rol> roles;
```

---

### `@JoinColumn`

Define la clave foránea.

---

### `mappedBy`

Indica el lado inverso de la relación (no propietario).

---

## 3. Clases embebidas

### `@Embeddable`

Clase sin tabla propia.

### `@Embedded`

Se usa dentro de la entidad.

---

### `@EmbeddedId`

Clave primaria compuesta.

---

## 4. Ciclo de vida

### `@Transient`

No se guarda en la BD.

### `@Enumerated`

Para enums.

```java
@Enumerated(EnumType.STRING)
```

---

### `@Temporal` (antiguo, antes de Java 8)

Para fechas con `Date`.

---

## 5. Fetch y Cascade

### `fetch`

* `FetchType.EAGER`
* `FetchType.LAZY`

Ejemplo:

```java
@OneToMany(fetch = FetchType.LAZY)
```

---

### `cascade`

* `PERSIST`
* `MERGE`
* `REMOVE`
* `ALL`
* `REFRESH`
* `DETACH`

Ejemplo:

```java
@OneToMany(cascade = CascadeType.ALL)
```
