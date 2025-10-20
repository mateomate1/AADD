import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import es.ciudadescolar.instituto.Alumno;
import es.ciudadescolar.util.FlujoBinarioSocket;

public class ProgramaCliente {
    public static void main(String[] args) throws Exception 
    {
        String HOST = "localhost";
	    int PUERTO = 4444;
	    Socket socket;
	    Alumno [] alumnos = {new Alumno("1","Paco","aaa"),new Alumno("2","Manolo","bbb"),new Alumno("3","Fermin","ccc")};

        try 
		{
			socket = new Socket(HOST,PUERTO);
			System.out.println("Conexión establecida con el servidor ["+socket+"]");
            FlujoBinarioSocket.escribirSocketAlumnos(socket, alumnos);
		} 
		catch (UnknownHostException e) 
		{
			System.out.println("Error, no se ha identificado el host del servidor");
		} 
		catch (IOException e) 
		{
			System.out.println("Error en la comunicación con el servidor");
		}

    }
}
