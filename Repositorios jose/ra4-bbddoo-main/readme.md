# 📝 Bases de Datos Orientadas a Objeto (BBDDOO)

👨‍🏫 Asignatura: Acceso a Datos

🧑‍💻 Profesor: José Sala Gutiérrez

📆 Curso: [2025/2026]

---

Este proyecto es un ejemplo sencillo de cómo realizar operaciones CRUD (**Create, Read, Update, Delete**) y gestionar transaciones sobre una base de datos orientada a objetos utilizando **DB4O** en Java.  

El objetivo es **familiarizarse con la simplicidad de uso de los SGBD orientados a objetos cuando utilizamos un lenguaje de programación orientado a objeto como Java** frente a la interacción con SGBDs relacionales.

## ✅ Características

- Se utilizan librerías externas:
  - org.slf4j
  - ch.qos.logback
  - com.db4o (esta obtenida de un repositorio externo)

- Se utiliza una clase `Alumno` y otra `Instituto` para la demostración de las diferentes operaciones de escritura, lectura, actualización y borrado, así como las consultas básicas para recuperar información almacenada.

- La documentación javadoc de la librería *com.db4o* está incluida en la carpeta *\javadoc* y puede ser añadida al repositorio local mediante la sentencia:

```bash
mvn install:install-file -Dfile="javadoc\db4o-7.7.67-javadoc.jar" -DgroupId="com.db4o" -DartifactId="com.db4o" -Dversion="7.7.67" -Dclassifier="javadoc" -Dpackaging="jar"
```

## 📁 Estructura del proyecto

```text

RA4-BBDDOO/
├── javadoc/                            # Carpeta donde se ubica la documentación de com.db4o
├── target/                             # Carpeta donde se ubican .class
├── src/
│   └──es
│      └──ciudadescolar       
│         ├──instituto
|         │  ├──Instituto.java 
|         │  └──Alumno.java 
│         ├──util
│         │  └──BbddooManager.java      # clase interacción con bases de datos OO 
│         └──Main.java                  # Clase principal
├── instituto.db4o                      # Base de datos db4o
└── README.md                           # Este documento

````

## ▶️ ¿Cómo probar la funcionalidad?

Se ha añadido un plugin de maven para la generación de un "fat" jar ejecutable con todas las dependiencias externas incluidas de forma que bastará con ejecutar lo siguiente:

```bash
mvn clean package
java -jar ra4-bbddoo-1.0.0-jar-with-dependencies.jar
````

---
