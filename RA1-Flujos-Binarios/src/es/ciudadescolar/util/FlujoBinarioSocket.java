package es.ciudadescolar.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;

import es.ciudadescolar.instituto.Alumno;

public class FlujoBinarioSocket {
	public Alumno[] lecturaSocketAlumnos(Socket clientesocket) {
		InputStream is = null;
		BufferedInputStream bis = null;
		ObjectInputStream ois = null;

		Alumno[] alumnos = null;

		try {
			is = clientesocket.getInputStream();
			bis = new BufferedInputStream(is);
			ois = new ObjectInputStream(bis);

			Object objeto = ois.readObject();
			alumnos = (Alumno[]) objeto;

			ois.close();
		} catch (ClassNotFoundException e) {
			System.out.println("Error en el formato de la información");
		} catch (IOException e) {
			System.out.println("Error recibiendo el listado de alumnos");
		}

		return alumnos;
	}

	public void escribirSocketAlumnos(Socket socketServidor, Alumno[] alumnos) {
		OutputStream os = null;
		BufferedOutputStream bos = null;
		ObjectOutputStream oos = null;

		System.out.println("Enviando alumnos...");

		try {
			os = socketServidor.getOutputStream();
			bos = new BufferedOutputStream(os);
			oos = new ObjectOutputStream(bos);

			oos.writeObject(alumnos);

			oos.flush();
			oos.close();
			System.out.println("Alumnos enviados!!!!");

			socketServidor.close();

		} catch (IOException e) {
			System.out.println("Error durante el envío de alumnos");
		}

	}
}
