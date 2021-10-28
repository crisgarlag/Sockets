package actividad2_req3;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.ArrayList;

public class HiloDevolverLibro extends Thread {

	public static int numCliente = 0;
	private Socket socketAlCliente;
	private InputStreamReader entrada;
	private PrintStream salida;
	private BufferedReader bfr;
	private ProcesadoInformacion procesadoInformación;

	public HiloDevolverLibro(Socket socketAlCliente) {
		numCliente++;
		this.socketAlCliente = socketAlCliente;
	}

	/**
	 * Constituye el cuerpo de un hilo de ejecucion. En este caso gestiona el
	 * intercambio de informacion desde el servidor al cliente.
	 */
	public void run() {

		Libro libroDevueltoDeBiblioteca = null;
		ArrayList<Libro> bibliotecaDevueltaACliente = new ArrayList<Libro>();
		Libro libroAñadidoABiblioteca;

		System.out.println("Estableciendo conexion del hilo" + this.getName() + " -  " + this.getId());

		try {

			entrada = new InputStreamReader(socketAlCliente.getInputStream());
			bfr = new BufferedReader(entrada);

			String stringRecibidoDelCliente = bfr.readLine();

			System.out.println("Se ha recibido " + stringRecibidoDelCliente);

			String[] stringRecibidoDelClienteConvertidoArray = stringRecibidoDelCliente.split("-");

			String codigoBusqueda = stringRecibidoDelClienteConvertidoArray[0];
			String datoBusqueda = stringRecibidoDelClienteConvertidoArray[1];

			if (codigoBusqueda.equals("Añadir")) {
				Double precio;
				libroAñadidoABiblioteca = new Libro();
				libroAñadidoABiblioteca.setISBN(stringRecibidoDelClienteConvertidoArray[1]);
				libroAñadidoABiblioteca.setTitulo(stringRecibidoDelClienteConvertidoArray[2]);
				libroAñadidoABiblioteca.setAutor(stringRecibidoDelClienteConvertidoArray[3]);
				precio = Double.parseDouble(stringRecibidoDelClienteConvertidoArray[4]);
				libroAñadidoABiblioteca.setPrecio(precio);

				SocketServidor.bibliotecaPrincipal.add(libroAñadidoABiblioteca);
			}

			for (int i = 0; i < SocketServidor.bibliotecaPrincipal.size(); i++) {

				switch (codigoBusqueda) {
				case "ISBN":
					if (datoBusqueda.equalsIgnoreCase(SocketServidor.bibliotecaPrincipal.get(i).getISBN())) {
						libroDevueltoDeBiblioteca = SocketServidor.bibliotecaPrincipal.get(i);
						bibliotecaDevueltaACliente.add(libroDevueltoDeBiblioteca);
					}
					break;

				case "Titulo":
					if (datoBusqueda.equalsIgnoreCase(SocketServidor.bibliotecaPrincipal.get(i).getTitulo())) {
						libroDevueltoDeBiblioteca = SocketServidor.bibliotecaPrincipal.get(i);
						bibliotecaDevueltaACliente.add(libroDevueltoDeBiblioteca);
					}
					break;

				case "Autor":
					if (datoBusqueda.equalsIgnoreCase(SocketServidor.bibliotecaPrincipal.get(i).getAutor())) {
						libroDevueltoDeBiblioteca = SocketServidor.bibliotecaPrincipal.get(i);
						bibliotecaDevueltaACliente.add(libroDevueltoDeBiblioteca);
					}
					break;
				}
			}

			procesadoInformación = new ProcesadoInformacion();
			String textoDevuelto = procesadoInformación.procesarInformacionACliente(bibliotecaDevueltaACliente);
			System.out.println(textoDevuelto + "hola");

			salida = new PrintStream(socketAlCliente.getOutputStream());

			if (textoDevuelto != "") {
				salida.println(textoDevuelto);
			} else {
				salida.println("No hay libros con la caracteristica elegida");
			}

			libroDevueltoDeBiblioteca = null;
			bibliotecaDevueltaACliente.clear();
			;

			socketAlCliente.close();

		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}
