# 📝 Ficheros XML/XSD - DOM

👨‍🏫 Asignatura: Acceso a Datos

🧑‍💻 Profesor: José Sala Gutiérrez

📆 Curso: [2025/2026]

---

Este proyecto de Java demuestra el tratamiento de ficheros XMLs para la lectura (*parser*) y escritura mediante la API DOM (org.w3c.dom). En ambos casos, el fichero de entrada y de salida se validan contra un XSD (Schema).

El objetivo es **familiarizarse con el procesamiento de ficheros XML con la API DOM** y aprender tanto a obtener información almacenada en dichos ficheros como a crear nuestros propios ficheros XML con la información solicitada. Habitualmente, la generación de un XML vendrá supeditado a un fichero validador que servirá como *acuerdo de interfaz* con otras aplicaciones o sistemas. En este caso concreto, dispondremos de un XSD (Schema) para asgurar el formato de la información del XML de salida.

## ✅ Características

- No se utilizan librerías adicionales.
- Se utiliza una clase Alumno para la demostración del proceso de recuperación de información y volcado de la misma.

## 📁 Estructura del proyecto

```text

RA1-XML-DOM_2/
├── lib/                                    # vacío
├── bin/                                    # Carpeta donde se ubican .class
├── src/
│   └──es
│      └──ciudadescolar       
│         ├──util
|         │  ├──Alumno.java 
│         │  ├──XmlManager.java             # clase interacción con ficheros XML (parsea y genera) 
│         │  └──AlumnoErrorHandler.java     # clase gestora de errores durante parseo/generación de XML.
│         └──Programa.java                  # Clase principal
├── alumnos3.xml                            # fichero xml de entrada para parsear
├── alumnos3.xsd                            # fichero xsd contra el que se valida el fichero xml  de entrada
├── alumnos4.xsd                            # fichero xsd contra el que se debe validar el fichero xml de salida
└── README.md                               # Este documento

````

## ▶️ ¿Cómo probar la funcionalidad?

Debes ubicar el xml "alumnos3.xml" a parsear y el XSD "alumnos3.xsd" que lo valida en el directorio de trabajo del proyecto. Así mismo, debes ubicar el XSD "alumno4.xsd" que permitirá validar el fichero de salida que genera la aplicación "alumnos4.xml"
Tras ejecutar el programa principal de este mismo repositorio, se mostrarán los alumnos por consola y se generá un xml de salida "alumnos4.xml" validable con el XSD proporcionado "alumnos4.xsd". El fichero de salida se sobreescribe con cada ejecución.

---
