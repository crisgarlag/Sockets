package actividad2_Definitiva;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class SocketServidor {

	public static final int PUERTO = 2017;
	private int peticiones = 0;
	private InetSocketAddress direccion;
	private Socket socketAlCliente;
	public static ArrayList<Libro> bibliotecaPrincipal;

	/**
	 * El metodo recoge la logica por parte del servidor en cuanto al envio y
	 * recepcion de informacion del servidor.
	 * 
	 * @throws IOException
	 */
	public void funcionDelServidor() throws IOException {
		
		Libro libro1 = new Libro("001", "titulo1", "Autor1", 1.00);
		Libro libro2 = new Libro("002", "titulo2", "Autor2", 2.00);
		Libro libro3 = new Libro("003", "titulo3", "Autor3", 3.00);
		Libro libro4 = new Libro("004", "titulo4", "Autor3", 4.00);
		Libro libro5 = new Libro("005", "titulo5", "Autor5", 5.00);
		bibliotecaPrincipal = new ArrayList<Libro>();
		bibliotecaPrincipal.add(libro1);
		bibliotecaPrincipal.add(libro2);
		bibliotecaPrincipal.add(libro3);
		bibliotecaPrincipal.add(libro4);
		bibliotecaPrincipal.add(libro5);

		// Se declara e incicializa un objeto de la clase ServerSocket para que se
		// cierre sin necesidad de utilizar el metodo close()
		try (ServerSocket serverSocket = new ServerSocket()) {

			// Puerto para establecer la conexion con el cliente.
			direccion = new InetSocketAddress(PUERTO);
			serverSocket.bind(direccion);

			System.out.println("Esperando peticiones por el puerto " + PUERTO + "\n");

			// Permite que el servidor este escuchando siempre peticiones, a no ser que
			// paremos el programa.
			while (true) {
				// Con el metodo accept() el servidor se pone a escuchar peticiones del cliente
				// y devuelve un transmisor para el intercambio de informacion
				socketAlCliente = serverSocket.accept();

				System.out.println("Se ha aceptado la peticion numero" + " " + ++peticiones);
				// Cada vez que haya una peticion se creara un nuevo hilo de ejecucion que, con
				// lo que se puede trabajar en multitarea.
				new HiloDevolverLibro(socketAlCliente).start();
			}

		} catch (IOException e) {
			System.err.println("SERVIDOR: Error de entrada/salida");
			e.printStackTrace();
		} catch (Exception e) {
			System.err.println("SERVIDOR: Error");
			e.printStackTrace();
		}
	}

}
