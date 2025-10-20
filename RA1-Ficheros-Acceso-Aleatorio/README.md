# 📝 Repaso de ficheros de acceso aleatorio

👨‍🏫 Asignatura: Acceso a Datos

🧑‍💻 Profesor: José Sala Gutiérrez

📆 Curso: [2025/2026]

---

Este proyecto Java básico demuestra el tratamiento de fichero de acceso aleatorio para la lectura y escritura.

El objetivo es **familiarizarse con el tratamiento de ficheros de acceso aleatorio** en los que, si el soporte físico de almacenamiento lo soporta, se podrá acceder a posiciones concretas del fichero sin necesidad de procesar todo el contenido previo como sucede con los ficheros secuenciales.

## ✅ Características

- No se utilizan librerías adicionales.

## 📁 Estructura del proyecto

```text

RA1-FICHEROS-ACCESO-ALEATORIO/
├── lib/                # vacío
├── bin/                # Carpeta donde se ubican .class
├── src/
│   └──es
│      └──ciudadescolar
│         └──Programa.java                # Clase principal 
└── README.md                             # Este documento

````

## ▶️ ¿Cómo probar la funcionalidad?

El programa principal de este mismo repositorio recibe como parámetro el número de letra del alfabeto al que se quiere acceder dentro de un fichero que contiene todas las letras del alfabeto. El acceso a esa posición se realiza de forma directa/aleatoria sin necesidad de ir procesando una a una las letras previas del fichero.

Para poder establecer en Visual Studio Code valores a los parámetros recibidos en el main(), se puede crear un launch.json desde la sección "run&debug" y añadir un elemento clave valor llamado *args* con un JSON array de cadenas de texto entrecomilladas:

```bash
  "args": [param1, param2, param3]
```

Por ejemplo

```text
{
    "version": "0.2.0",
    "configurations": [
        {
            "type": "java",
            "name": "Programa",
            "request": "launch",
            "mainClass": "es.ciudadescolar.Programa",
            "args": [
                "27"
            ]
        }
    ]
}

```

---
