package actividad2_Definitiva;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.ArrayList;

public class HiloDevolverLibro extends Thread {

	private Socket socketAlCliente;
	private InputStreamReader entrada;
	private PrintStream salida;
	private BufferedReader bfr;
	private ProcesadoInformacion procesadoInformación;

	/**
	 * Constructor
	 * 
	 * @param socketAlCliente
	 */
	public HiloDevolverLibro(Socket socketAlCliente) {

		this.socketAlCliente = socketAlCliente;
	}

	/**
	 * Constituye el cuerpo de un hilo de ejecucion. En este caso gestiona el
	 * intercambio de informacion desde el servidor al cliente.
	 */
	public void run() {

		ArrayList<Libro> bibliotecaDevueltaACliente = new ArrayList<Libro>();
		String stringRecibidoDelCliente;
		String[] stringRecibidoDelClienteConvertidoArray;
		String codigoBusqueda;
		String datoBusqueda;
		String textoDevuelto;

		System.out.println("Estableciendo conexion del hilo" + " -  " + this.getName());

		try {

			entrada = new InputStreamReader(socketAlCliente.getInputStream());
			bfr = new BufferedReader(entrada);
			salida = new PrintStream(socketAlCliente.getOutputStream());
			procesadoInformación = new ProcesadoInformacion();

			stringRecibidoDelCliente = bfr.readLine();

			System.out.println("\nLa informacion recibida es la siguiente: \n " + stringRecibidoDelCliente + "\n");

			stringRecibidoDelClienteConvertidoArray = stringRecibidoDelCliente.split("-");

			codigoBusqueda = stringRecibidoDelClienteConvertidoArray[0];
			datoBusqueda = stringRecibidoDelClienteConvertidoArray[1];

			if (codigoBusqueda.equals("Añadir")) {
				procesadoInformación.añadirLibro(stringRecibidoDelClienteConvertidoArray);
				salida.println("Libro añadido correctamente\n");

			} else {
				switch (codigoBusqueda) {
				case "ISBN":
					bibliotecaDevueltaACliente = procesadoInformación.buscarPorISBN(datoBusqueda);
					break;

				case "Titulo":
					bibliotecaDevueltaACliente = procesadoInformación.buscarPorTitulo(datoBusqueda);
					break;

				case "Autor":
					bibliotecaDevueltaACliente = procesadoInformación.buscarPorAutor(datoBusqueda);
					break;
				}

				textoDevuelto = procesadoInformación.procesarInformacionAlCliente(bibliotecaDevueltaACliente);

				if (textoDevuelto != "") {
					salida.println(textoDevuelto);
					System.out.println("La informacion enviada es la siguiente: \n" + textoDevuelto + "\n");
				} else {
					salida.println("No hay libros con la caracteristica elegida");
					System.out.println("No se ha encontrado libro para enviar\n");
				}
			}

			bibliotecaDevueltaACliente.clear();
			;

			socketAlCliente.close();

		} catch (IOException e) {
			System.err.println("HiloDevolverLibro: Error de entrada/salida");
			e.printStackTrace();
		} catch (Exception e) {
			System.err.println("HiloDevolverLibro: Error");
			e.printStackTrace();
		}
	}
}
