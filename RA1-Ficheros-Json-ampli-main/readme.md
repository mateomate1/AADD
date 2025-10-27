# 📝 Ficheros JSON (ampliación)

👨‍🏫 Asignatura: Acceso a Datos

🧑‍💻 Profesor: José Sala Gutiérrez

📆 Curso: [2025/2026]

---

Este proyecto de Java demuestra la funcionalidad de serialización y deserialización "automática" mediante la API Jackson databind sobre ficheros JSON. En el proyecto anterior, recordad que fuimos nosotros "dinámicamente" quienes recuperamos y escribimos la información sobre ficheros JSON.

El objetivo es **familiarizarse con las condiciones necesarias para serializar y deserializar automáticamente nuestras clases sobre ficheros JSON con la API Jackson databind** y conocer las sentencias para realizar dichas operaciones delengando en la API Jackson (reducimos notablemente nuestras líneas de código).

Los **requisitos básicos para serializar/deserializar una clase o colección de clases** con Jackson:

- Constructor sin argumentos: Jackson necesita poder crear instancias de la clase durante la deserialización (al leer JSON).

- Getters y Setters públicos: Jackson usa reflexión para acceder a los campos. Por defecto, sigue la convención JavaBean: Getters (getCampo()) para leer y Setters (setCampo()) para escribir.

- Todos los campos deben ser serializables:
  - Tipos primitivos (int, boolean, etc.)
  - Tipos wrapper (Integer, Boolean, etc.)
  - String
  - Clases personalizadas (si también cumplen a su vez estas condiciones)
  - Colecciones o arrays (List, Set, Map, ...) Si algún campo no es serializable (por ejemplo, un Socket o un Thread), habrá que marcarlo como ignorado: *@JsonIgnore*

- El nombre de los campos y métodos será el que useJackson para mapear al JSON resultante. Si queremos usar nombres distintos en el JSON: *@JsonProperty("XXX")*

- Serialización de colecciones: Si tenemos colecciones de una clase propia (por ejemplo, una lista de Alumno), Jackson las serializa automáticamente como arrays JSON

## Serialización y deserialización 

**Serializar** significa convertir un objeto de Java (o una colección de objetos) en un formato que pueda almacenarse o transmitirse, en este contexto, como JSON.

```bash
    mapper.writerWithDefaultPrettyPrinter().writeValue(ficheroSalidaJson, instituto);
    mapper.writerWithDefaultPrettyPrinter().writeValue(ficheroSalidaJson, arrayAlumnos);
````

**Deserializar** es el proceso inverso: convertir JSON en objetos Java o colecciones de dichos objetos.

```bash
  List<Alumno> alumnos = mapper.readValue(ficheroEntradaJson, new TypeReference<List<Alumno>>() {});
  Instituto instituto=  mapper.readValue(ficheroEntradaJson, Instituto.class);
````

- **Nota**: La deserialización de Jackson sólo aplica sobre la raiz:
  - Si es un objeto la raiz ({...}) deberíamos tener una clase Java que represente dicho elemento. Ejemplos:

    ```bash
    Alumno alumno = mapper.readValue(json, Alumno.class);
    Instituto instituto = mapper.readValue(ficheroJson, Instituto.class);
    ````

  - Si es un array la raiz ([...]) deberíamos tener una clase Java solo con los objetos contenidos. Ejemplo:

    ```bash
    listaAlumnos = mapper.readValue(ficheroJson, new TypeReference<List<Alumno>>() {});
    ````


## ✅ Características

- Se utilizan librerías externas:
  - org.slf4j
  - ch.qos.logback
  - com.fasterxml.jackson.core
  - com.fasterxml.jackson.datatype

- Se utiliza una clase Alumno y otra Instituto para la demostración del proceso de serialización y deserialización automática.

- Se ha modificado la clase Alumno original para añadir una propiedad de tipo fecha y así aprender a procesar en Json dicho formato.

- Se utilizan anotaciones Jackson sobre los atributos de la clase Alumno con las siguientes finalidades:
  - *@JsonIgnore* → excluir un campo (no serializarlo ni deserializarlo).
  - *@JsonProperty*("nombre_json") → cambiar el nombre de un determinado atributo.
  - *@JsonFormat*(pattern = "yyyy-MM-dd") → establecer el formato de fecha a utilizar.

## 📁 Estructura del proyecto

```text

RA1-FICHEROS-JSON-AMPLI/
├── target/                             # Carpeta donde se ubican .class
├── src/
│   └──es
│      └──ciudadescolar       
│         ├──instituto
|         │  ├──Instituto.java 
|         │  └──Alumno.java 
│         ├──util
│         │  └──JsonManager.java        # clase interacción con ficheros Json (parsea y genera) 
│         └──Main.java                  # Clase principal
├── alumnos.json                            # fichero json de entrada para parsear
└── README.md                               # Este documento

````

## ▶️ ¿Cómo probar la funcionalidad?

Se ha añadido un plugin de maven para la generación de un "fat" jar ejecutable con todas las dependiencias externas incluidas de forma que bastará con ejecutar lo siguiente:

```bash
mvn clean package
java -jar ra1-ficheros-json-ampli-1.0.0-jar-with-dependencies.jar
````

---
