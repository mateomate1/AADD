# 📝 Ejercicio de clase RA2 BBDD Relacionales

👨‍🏫 Asignatura: Acceso a Datos

🧑‍💻 Profesor: José Sala Gutiérrez

📆 Curso: [2025/2026]

---

Este proyecto de Java introduce la operativa básica para interactuar con Bases de Datos relacionales usando la **API JDBC estándar**. Entre otras operativas que se trabajan son: consultas, con y sin parámetros, altas, bajas y modificaciones así como invocación de funciones de usuario y procedimientos almacenados. También se pone el foco en la operativa para gestionar transacciones y las buenas prácticas (gestión eficiente de recursos, evitar hardcodes, parametrizar conexiones, etc).

El objetivo es **familiarizarse con el tratamiento de bases de datos relacionales en Java** y desarrollar la lógica necesaria para cumplir con los requisitos de un posible enunciado.

El SGBD a utilizar es **mysql**.

## 📁 Estructura del proyecto

```text

RA2_JDBC_CLASE/
├── target/                                # Carpeta donde se ubican .class
├── src/
│   ├──main
│   │    ├──es
│   │    │  └──ciudadescolar       
│   │    │      ├──instituto
│   │    │      │   └──Alumno.java     
│   │    │      ├──util 
│   │    │      │   ├──SQL.java            # Clase con las sentencias SQL 
│   │    │      │   └──DbManager.java      # Clase gestora de interacción con la BD relacional
│   │    │      └──Main.java               # Clase principal
│   │    └──resources
│   │       └──Logback.xml                 # fichero configuración Log
├── pom.xml                                # fichero configuración Maven
├── conexionDB.properties                  # fichero configuración de conexión a BD
└── readme.md                              # Este documento

````

---
