import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.net.ServerSocket;
import es.ciudadescolar.instituto.Alumno;
import es.ciudadescolar.util.FlujoBinarioSocket;

public class ProgramaServidor {
    public static void main(String[] args) throws Exception 
    {
	    int PUERTO = 4444;
	    ServerSocket servidorSocket =null;
		Socket clienteSocket =null;
	    try 
		{
			servidorSocket = new ServerSocket(PUERTO); // socket servidor
			clienteSocket = new Socket();   // socket cliente
			
			System.out.println("Esperando peticiones del cliente...");
		
			clienteSocket = servidorSocket.accept();
			System.out.println("Cliente en linea");
			
			Alumno[] alumnosRecibidos = FlujoBinarioSocket.lecturaSocketAlumnos(clienteSocket);
			
			for(Alumno al:alumnosRecibidos)
			{
				System.out.println(al);
			}
			
			System.out.println("Finalizando comunicación con el cliente");
			clienteSocket.close();
			servidorSocket.close();
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
