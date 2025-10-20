package es.ciudadescolar.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.charset.StandardCharsets;

import es.ciudadescolar.instituto.Alumno;

public class FlujoBinarioFichero {
	/**
	 * Lectura binaria "ineficiente": Se va a disco para recuperar (leer) un byte de
	 * cada vez
	 *
	 * @param rutaFichero La ruta del fichero a leer.
	 */
	public void lecturaFicheroByteAByte(String rutaFichero) {
		FileInputStream fis = null;
		int byteLeido;
		try {
			fis = new FileInputStream(new File(rutaFichero));

			byteLeido = fis.read();
			while (byteLeido != -1) {
				System.out.println(
						"Byte leído de fichero: " + byteLeido + " que corresponde con char: " + (char) byteLeido);
				byteLeido = fis.read();
			}
			System.out.println("Fin de la lectura.");
		} catch (FileNotFoundException e) {
			// TODO Este tratamiento de la excepción no es definitivo. A futuro lo
			// mandaremos a un log.
			System.out.println("Problemas al leer de un flujo de texto binario. Fichero no encontrado:" + e.getMessage());
		} catch (IOException e) {
			// TODO Este tratamiento de la excepción no es definitivo. A futuro lo
			// mandaremos a un log.
			System.out.println("Problemas al leer de un flujo de texto binario:" + e.getMessage());
		} finally {
			try {
				if (fis != null)
					fis.close();
			} catch (IOException e) {
				// TODO Este tratamiento de la excepción no es definitivo. A futuro lo
				// mandaremos a un log.
				System.out.println("Problemas durante el cierre de flujo de texto binario:" + e.getMessage());
			}
		}

	}

	/**
	 * Lectura binaria "eficiente": en bloques de bytes en lugar de byte a byte
	 * El BufferedInputStream aporta un buffer interno leyendo en bloque (aunque
	 * nosotros leamos byte a byte)
	 * podemos dejar el tamaño por defecto o establecer el número de bytes nosotros
	 * durante la instanciación
	 *
	 * @param rutaFichero La ruta del fichero a leer.
	 */
	public void lecturaFicheroBufferDeBytes(String rutaFichero) {
		FileInputStream fis = null;
		BufferedInputStream bis = null;
		int byteLeido;
		try {
			fis = new FileInputStream(new File(rutaFichero));
			bis = new BufferedInputStream(fis, 1024);

			byteLeido = bis.read();
			while (byteLeido != -1) {
				System.out.println(
						"Byte leído de fichero: " + byteLeido + " que corresponde con char: " + (char) byteLeido);
				byteLeido = bis.read();
			}
			System.out.println("Fin de la lectura.");
		} catch (FileNotFoundException e) {
			// TODO Este tratamiento de la excepción no es definitivo. A futuro lo
			// mandaremos a un log.
			System.out
					.println("Problemas al leer de un flujo de texto binario. Fichero no encontrado:" + e.getMessage());
		} catch (IOException e) {
			// TODO Este tratamiento de la excepción no es definitivo. A futuro lo
			// mandaremos a un log.
			System.out.println("Problemas al leer de un flujo de texto binario:" + e.getMessage());
		} finally {
			try {
				if (bis != null)
					bis.close();
			} catch (IOException e) {
				// TODO Este tratamiento de la excepción no es definitivo. A futuro lo
				// mandaremos a un log.
				System.out.println("Problemas durante el cierre de flujo de texto binario:" + e.getMessage());
			}
		}

	}

	/**
	 * Lectura binaria "eficiente": en bloques personalizados de bytes en lugar de
	 * byte a byte
	 * Creamos un buffer de tamaño personalizado para leer X bytes de una vez
	 *
	 * @param rutaFichero La ruta del fichero a leer.
	 */
	public void lecturaFicheroBufferPersonalizadoDeBytes(String rutaFichero) {
		FileInputStream fis = null;

		int bytesLeidos;
		byte[] buffer = new byte[1024];

		try {
			fis = new FileInputStream(new File(rutaFichero));

			bytesLeidos = fis.read(buffer);
			while (bytesLeidos != -1) {
				System.out.println("Leídos " + bytesLeidos + " bytes");
				// Aquí puedes procesar los datos del buffer
				System.out.println(new String(buffer, 0, bytesLeidos, StandardCharsets.UTF_8));

				for (int i = 0; i < bytesLeidos; i++) {
					System.out.println(
							"Byte leído de fichero: " + buffer[i] + " que corresponde con char: " + (char) buffer[i]);
				}
				bytesLeidos = fis.read(buffer);

			}
			System.out.println("Fin de la lectura.");
		} catch (FileNotFoundException e) {
			// TODO Este tratamiento de la excepción no es definitivo. A futuro lo
			// mandaremos a un log.
			System.out
					.println("Problemas al leer de un flujo de texto binario. Fichero no encontrado:" + e.getMessage());
		} catch (IOException e) {
			// TODO Este tratamiento de la excepción no es definitivo. A futuro lo
			// mandaremos a un log.
			System.out.println("Problemas al leer de un flujo de texto binario:" + e.getMessage());
		} finally {
			try {
				if (fis != null)
					fis.close();
			} catch (IOException e) {
				// TODO Este tratamiento de la excepción no es definitivo. A futuro lo
				// mandaremos a un log.
				System.out.println("Problemas durante el cierre de flujo de texto binario:" + e.getMessage());
			}
		}
	}

	/**
	 * Escribe en fichero un array de instancias Alumno serializándolos.
	 * Se sobreescribe el fichero con cada ejecución.
	 * 
	 * @param alumnos
	 * @param ficheroAlumno
	 */
	public static void escrituraFicheroAlumnos(Alumno[] alumnos, String ficheroAlumno) {
		FileOutputStream fos = null;
		BufferedOutputStream bos = null;
		ObjectOutputStream oos = null;

		try {
			fos = new FileOutputStream(new File(ficheroAlumno));
			bos = new BufferedOutputStream(fos);
			oos = new ObjectOutputStream(bos);

			for (Alumno al : alumnos) {
				oos.writeObject(al);
			}

		} catch (FileNotFoundException e) {
			System.out.println("Error durante la escritura de objetos en fichero: " + e.getMessage());
		} catch (IOException e) {
			System.out.println("Error durante la escritura de objetos en fichero: " + e.getMessage());
		} finally {
			try {
				oos.flush();
				oos.close();
			} catch (IOException e) {
				System.out.println("Error en el cierre de fichero binario con objetos: " + e.getMessage());

			}

		}

	}

	/**
	 * Lee los objetos serializados en un fichero binario mostrandolos por pantalla
	 * (Salida estandar)
	 * 
	 * @param rutaFichero
	 */
	public static void lecturaFicheroAlumnos(String rutaFichero) {
		FileInputStream fis = null;
		BufferedInputStream bis = null;
		ObjectInputStream ois = null;

		try {
			fis = new FileInputStream(new File(rutaFichero));
			bis = new BufferedInputStream(fis);
			ois = new ObjectInputStream(bis);

			while (true) {

				Alumno al = (Alumno) ois.readObject();
				System.out.println(al);
			}
		} catch (EOFException e) {
			// excepción controlada. Fin de fichero.
			System.out.println("Lectura de fichero finalizada");

		} catch (ClassNotFoundException e) {
			System.out.println("Error durante la lectura de objetos: " + e.getMessage());
		} catch (IOException e) {
			System.out.println("Error durante la lectura de objetos: " + e.getMessage());

		} finally {
			try {
				ois.close();
			} catch (IOException e) {
				System.out.println("Error en el cierre de fichero binario con objetos" + e.getMessage());
			}
		}

	}

	/**
	 * Escribe un array de caracteres (tipo elemental char)
	 * 
	 * @param caracteres
	 * @param rutaFichero
	 */
	public static void escrituraFicheroChars(char[] caracteres, String rutaFichero) {
		FileOutputStream fos = null;
		BufferedOutputStream bos = null;
		DataOutputStream dos = null;

		try {
			fos = new FileOutputStream(rutaFichero);
			bos = new BufferedOutputStream(fos);
			dos = new DataOutputStream(bos);

			for (int i = 0; i < caracteres.length; i++) {
				dos.writeChar(caracteres[i]);
			}

		} catch (FileNotFoundException e) {
			System.out.println("Error. Fichero no encontrado");
		} catch (IOException e) {
			System.out.println("Error. Problemas durante el acceso a disco");
		} finally {
			try {
				dos.flush();
				dos.close();
			} catch (IOException e) {
				System.out.println("Error durante el cierre del fichero");
			}

		}

	}

	/**
	 * Lee un fichero binario en el que se dispone de caracteres (tipo elemental)
	 * 
	 * @param rutaFichero
	 */
	public static void lecturaFicheroChars(String rutaFichero) {

		FileInputStream fis = null;
		BufferedInputStream bis = null;
		DataInputStream dis = null;

		try {
			fis = new FileInputStream(rutaFichero);
			bis = new BufferedInputStream(fis);
			dis = new DataInputStream(bis);

			while (true) // de este bucle infinito salimos con la excepción EOFException (al llegar al
							// final de fichero)
			{
				// leyendo char a char (tipo elemental -> datainputstream)
				char caracter = dis.readChar();
				System.out.print(caracter + " ");
			}
		} catch (EOFException e) {
			// excepción controlada. No es un error. Es el final del fichero.
			System.out.print('\n');
			System.out.println("Lectura de fichero finalizada");
		} catch (FileNotFoundException e) {
			System.out.println("Error. Fichero no accesible");
		} catch (IOException e) {
			System.out.println("Error accediendo al fichero para leer");
		} finally {
			try {
				dis.close();
			} catch (IOException e) {
				System.out.println("Error durante el cierre del fichero");
			}
		}
	}

}
