# Apuntes JDBC

## 1. DriverManager

Se usa para obtener la conexion con la base de datos.

Uso tipico:

* Arranque de la aplicacion
* Pruebas simples

```java
Connection conn = DriverManager.getConnection(url, user, pass);
```

---

## 2. Connection

Representa una conexion activa con la base de datos.

Se usa para:

* Crear statements
* Manejar transacciones

Metodos importantes:

* createStatement()
* prepareStatement(*String*)
* prepareCall(*String*)
* setAutoCommit(*Boolean*)
* commit()
* rollback()

---

## 3. Statement

Se usa cuando:

* SQL fijo
* Sin parametros
* Consultas simples

```java
Statement st = conn.createStatement();
ResultSet rs = st.executeQuery("SELECT * FROM alumno");
```

Inconvenientes:

* Riesgo de SQL Injection
* Peor rendimiento si se ejecuta muchas veces

---

## 4. PreparedStatement

Se usa cuando:

* SQL con parametros
* La misma consulta se ejecuta varias veces
* Seguridad y rendimiento

```java
PreparedStatement ps = conn.prepareStatement(
    "SELECT * FROM alumno WHERE id = ?"
);
ps.setInt(1, 10);
ResultSet rs = ps.executeQuery();
```

En caso de ser un **INSERT**, **UPDATE** o **DELETE** se recomienda usar ***executeUpdate***, metodo que devuelve un entero con la cantidad de filas afectadas

```java
PreparedStatement ps = conn.prepareStatement(
    "UPDATE alumno SET nombre = ? WHERE id = ?"
);
ps.setString(1, "Manolo");
ps.setInt(2, 10);
if(ps.executeUpdate() > 1){
    System.out.println("Se actualizaron demasiada lineas...")
    con.rollback();
}
```

Ventajas:

* Evita SQL Injection
* Mejor rendimiento
* Codigo mas limpio

---

## 5. CallableStatement

Se usa para procedimientos y funciones almacenadas.

### IN

```java
CallableStatement cs = conn.prepareCall("{CALL sp_getalumno(?)}");
cs.setInt(1, 10);
ResultSet rs = cs.executeQuery();
```

### OUT

```java
CallableStatement cs = conn.prepareCall("{CALL sp_total(?, ?)}");
cs.setInt(1, 10);
cs.registerOutParameter(2, Types.INTEGER);
cs.execute();
int total = cs.getInt(2);
```

### INOUT

```java
CallableStatement cs = conn.prepareCall("{CALL sp_inout(?)}");
cs.setInt(1, 5);
cs.registerOutParameter(1, Types.INTEGER);
cs.execute();
int resultado = cs.getInt(1);
```

---

## 6. ResultSet

Contiene el resultado de una consulta SELECT.

```java
while (rs.next()) {
    int id = rs.getInt("id");
    String nombre = rs.getString("nombre");
}
```

Tipos:

* TYPE_FORWARD_ONLY
* TYPE_SCROLL_INSENSITIVE
* TYPE_SCROLL_SENSITIVE

---

## 7. ResultSetMetaData

Se usa cuando no se conoce la estructura del resultado.

```java
ResultSetMetaData md = rs.getMetaData();
int columnas = md.getColumnCount();
```

---

## 8. SQLException

Se usa para capturar errores SQL.

```java
catch (SQLException e) {
    e.getMessage();
    e.getSQLState();
    e.getErrorCode();
}
```

---

## 9. DataSource

Alternativa moderna a DriverManager.

Se usa cuando:

* Pool de conexiones
* Aplicaciones grandes

Ventajas:

* Mejor rendimiento
* Gestion centralizada de conexiones

---

## Eleccion rapida

* SELECT simple -> Statement
* SELECT con parametros -> PreparedStatement
* INSERT / UPDATE / DELETE -> PreparedStatement
* Procedimientos almacenados -> CallableStatement
* Lectura de resultados -> ResultSet

---

## Regla general

En aplicaciones reales se usa principalmente:

PreparedStatement + ResultSet + DataSource

Statement casi nunca en produccion.
