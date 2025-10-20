package es.ciudadescolar;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

public class Programa {

	public static void main(String[] args) {
		if (args.length != 1) {
			System.out.println(
					"error de parámetros. Se esperaba un parámetro con el número de letra a mostrar del alfabeto.");
			System.exit(1);
		}
		int num_letra = -1;
		try {
			num_letra = Integer.parseInt(args[0]);
			if (num_letra < 1 || num_letra > 27) {
				throw new NumberFormatException();
			}
		} catch (NumberFormatException e) {
			System.out.println("error de parámetro. Se esperaba un número comprendido entre 1 y 27");
			System.exit(1);
		}

		System.out.println("El numero de caracter(char) al que queremos acceder es [" + num_letra + "]");

		// Creación del fichero binario de acceso aleatorio con las letras del alfabeto

		RandomAccessFile fich = null;

		File fichero = new File("Abecedario.dat");

		if (fichero.exists())
			System.out.println("Error, el fichero ya existe");
		else {
			try {
				fich = new RandomAccessFile(fichero, "rw");
				String abecedario = "abcdefghijklmnñopqrstuvwxyz";

				// opción 1: escribir char a char

				/*
				 * for(int i=0;i<abecedario.length();i++)
				 * {
				 * fich.writeChar(abecedario.charAt(i));
				 * }
				 */

				// opción 2: escribir de golpe los chars de un String
				fich.writeChars(abecedario);

				fich.close();

				// El fichero binario de acceso aleatorio recien creado con las letras del
				// alfabeto lo abrimos de nuevo para acceder "aleatoriamente"

				fich = new RandomAccessFile(fichero, "rw");
				System.out
						.println("El fichero [" + fichero.getAbsolutePath() + "] ocupa [" + fich.length() + "] bytes");

				int posicion = num_letra * 2; // 2 bytes cada char

				System.out.println(
						"Antes de posicionar el puntero, estamos en la posicion [" + fich.getFilePointer() + "]");
				fich.seek(posicion - 2); // posiciono el puntero donde empieza la letra número X (2 bytes previos)
				System.out.println("Despues de posicionar el puntero en la posicion indicada, estamos en la posicion ["
						+ fich.getFilePointer() + "]");
				System.out.println("El caracter(char) en esa posicion es [" + fich.readChar() + "]");
				System.out.println(
						"Despues de leer los bytes del char, estamos en la posicion [" + fich.getFilePointer() + "]");
			} catch (FileNotFoundException e) {
				System.out.println("Error, el fichero no existe");
			} catch (IOException e) {
				System.out.println("Error en la lectura del fichero");
			} finally {
				try {
					fich.close();
					fichero.delete();
				} catch (IOException e) {
					System.out.println("Error durante el cierre o borrado de fichero");
				}
			}
		}
	}
}
