# 📝 Repaso de flujos binarios

👨‍🏫 Asignatura: Acceso a Datos

🧑‍💻 Profesor: José Sala Gutiérrez

📆 Curso: [2025/2026]

---

Este proyecto Java básico demuestra el tratamiento de flujos binarios para la lectura y escritura desde la entrada estandar (teclado), desde fichero y desde sockets.

El objetivo es **familiarizarse con el tratamiento de flujos binarios** en diversos escenarios asimilando que la operativa en todos los casos es prácticamente la misma.

## ✅ Características

- No se utilizan librerías adicionales.
- Se utiliza una clase Alumno para la demostración del proceso de serialización en ficheros y sockets.

## 📁 Estructura del proyecto

```text

RA1-FLUJOS-BINARIOS/
├── lib/                # vacío
├── bin/                # Carpeta donde se ubican .class
├── src/
│   └──es
│      └──ciudadescolar
│         ├──instituto
│         │  └──Alumno.java
│         ├──util
│         │  ├──FlujoBinarioFichero.java  # clase interacción con ficheros
│         │  ├──FlujoBinarioSocket.java   # clase interacción con sockets (red)
│         │  └──FlujoBinarioTeclado.java  # clase interacción con teclado
│         └──Programa.java                # Clase principal 
└── README.md                             # Este documento

````

## ▶️ ¿Cómo probar la funcionalidad para teclado y ficheros?

Desde el programa principal de este mismo repositorio, ir comentando y descomentando las invocaciones a métodos de las clases del paquete util para evidenciar el funcionamiento de cada lectura/escritura y sus diferentes versiones.

## 🔍 ¿Cómo probar la funcionalidad para sockets?

Los métodos que interaccionan con sockets para la comunicación a través de la red precisan de *dos proyectos en ejecución a la vez*.

- 1º **Servidor**: donde se arranque un socket de escucha y lea los objetos transmitidos por el cliente cuando se conecte.
- 2º **cliente**: Disponga de los objetos a transmitir los envíe una vez conectado al servidor.

El repositorio [Servidor Socket](https://github.com/usuario/otro-repo) será el que dispone del código necesario para arrancar el servidor y recibir los objetos serializados. Mientras que el repositorio [Cliente Socket](https://github.com/usuario/otro-repo) será el que dispone del código necesario para conectar un cliente al servidor y transmitir los objetos serializados.

Ambos proyectos utilizan la clase ```FlujoBinarioSocket``` definida en este repositorio.

---
