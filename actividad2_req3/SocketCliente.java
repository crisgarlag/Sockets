package actividad2_req3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.InputMismatchException;
import java.util.Scanner;

public class SocketCliente {

	Scanner sc = new Scanner(System.in);
	Scanner sc2 = new Scanner(System.in);
	public static final int PUERTO = 2017;
	public static final String IP_SERVER = "localhost";
	private InputStreamReader entrada;
	private PrintStream salida;
	private InetSocketAddress direccion;
	private Socket socketAlServidor;
	private BufferedReader bfr;
	private ProcesadoInformacion procesadoInformación;

	/**
	 * Muestra un menu con las distintas opciones existentes en la biblioteca
	 * 
	 * @return eleccion es la opcion elegida por el usuario
	 */
	public int menu() {

		int eleccion = 0;
		boolean opcionIncorrecta = false;
		;

		System.out.println("---------MENU DE LA BIBLIOTECA---------\n");
		System.out.println("Seleecione una de las siguientes opciones.");
		System.out.println("1.- Consulta libro por ISBN");
		System.out.println("2.- Consulta libro por titulo");
		System.out.println("3.- Consulta libro por autor");
		System.out.println("4.- Añadir libro");
		System.out.println("5.- Salir de la aplicacion");

		try {

			eleccion = sc.nextInt();
			while (!opcionIncorrecta) {
				if (eleccion > 5 || eleccion < 1) {

					System.out.println("El dato introducido es incorrecto, introduce una de las opciones validas.");
					eleccion = sc.nextInt();
				} else {
					opcionIncorrecta = true;
				}
			}
		} catch (InputMismatchException e) {
			System.out.println("Debe introducir un valor numerico.");
		}
		return eleccion;
	}

	/**
	 * Segñun la eleccion introducida por parametros muestra por solicita por
	 * pantalla la informacion que se enviara al servidor.
	 * 
	 * @param eleccion
	 * @return La informacion de la que el usuario quiere obtener informacion.
	 */
	public String indicarInformacionEnvio(int eleccion) {

		String ISBN;
		String titulo;
		String autor;
		String precio;
		String informacionEnvio = null;

		switch (eleccion) {

		case 1:
			System.out.println("Selecciona el ISBN");
			ISBN = sc2.nextLine();
			informacionEnvio = ("ISBN" + "-" + ISBN);
			break;
		case 2:
			System.out.println("Selecciona el titulo");
			titulo = sc2.nextLine();
			informacionEnvio = ("Titulo" + "-" + titulo);
			break;
		case 3:
			System.out.println("Selecciona el Autor");
			autor = sc2.nextLine();
			informacionEnvio = ("Autor" + "-" + autor);
			break;
		case 4:
			System.out.println("Indique ISBN");
			ISBN = sc2.nextLine();
			System.out.println("Indique Titulo");
			titulo = sc2.nextLine();
			System.out.println("Indique Autor");
			autor = sc2.nextLine();
			System.out.println("Indique Precio");
			precio = sc2.nextLine();
			informacionEnvio = ("Añadir" + "-" + ISBN + "-" + titulo + "-" + autor + "-" + precio);
			break;
		// default:
		// System.out.println("No ha elegido una opcion correcta, seleccione de
		// nuevo.");
		}
		return informacionEnvio;
	}

	/**
	 * El metodo recoge la logica por parte del cliente en cuanto al envio y
	 * recepcion de informacion del servidor.
	 * 
	 * @throws IOException
	 */
	public void funcionDelCliente() throws IOException {

		// Direccion IP y Puerto para establecer la conexion con el servidor.
		direccion = new InetSocketAddress(IP_SERVER, PUERTO);

		try {

			int eleccionClienteMenu = menu();
			
			//Permite que el cliente siga realizando peticiones hasta que el usuario indique la opcion 5.- Salir de la aplicacion
			while (eleccionClienteMenu != 5) {

				String informacionEnvio = indicarInformacionEnvio(eleccionClienteMenu);

				// Se inicializa el socket para establecer la conexion con el servidor a traves
				// de la direccion.

				socketAlServidor = new Socket();
				socketAlServidor.connect(direccion);
				System.out.println("Conexion establecida con el servidor por el puerto " + PUERTO + " y direccion IP "
						+ IP_SERVER);

				// Se inicializa la variable salida para enviar informacion al servidor.

				salida = new PrintStream(socketAlServidor.getOutputStream());
				salida.println(informacionEnvio);

				System.out.println("Esperando el resultado");

				// Se inicializa la variable entrada para recibir informacion del servidor

				entrada = new InputStreamReader(socketAlServidor.getInputStream());

				// La clase BufferReader ayuda en la recepcion de la informacion

				bfr = new BufferedReader(entrada);
				String datoDevuelto = bfr.readLine();
				procesadoInformación=new ProcesadoInformacion();
				procesadoInformación.procesarInformacionDeServidor(datoDevuelto);
				

				// Se cierra el socket

				socketAlServidor.close();

				eleccionClienteMenu = menu();

			}
			System.out.println("Fin de la aplicacion");

		} catch (IOException e) {
			System.out.println("La excepcion es " + e);
			e.printStackTrace();
		}
		sc.close();
		sc2.close();
	}
}
