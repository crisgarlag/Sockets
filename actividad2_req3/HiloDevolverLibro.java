package actividad2_req3;

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

	public HiloDevolverLibro(Socket socketAlCliente) {

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

		System.out.println("Estableciendo conexion del hilo" + " -  " + this.getName());

		try {

			entrada = new InputStreamReader(socketAlCliente.getInputStream());
			bfr = new BufferedReader(entrada);
			salida = new PrintStream(socketAlCliente.getOutputStream());

			String stringRecibidoDelCliente = bfr.readLine();

			System.out.println("\nLa informacion recibida es la siguiente: \n " + stringRecibidoDelCliente + "\n");

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
				salida.println("Libro añadido correctamente\n");
				System.out.println("Se ha añadido un libro a la biblioteca.\n");
			} else {

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

				

				if (textoDevuelto != "") {
					salida.println(textoDevuelto);
					System.out.println("La informacion enviada es la siguiente: \n" + textoDevuelto + "\n");
				} else {
					salida.println("No hay libros con la caracteristica elegida");
					System.out.println("No se ha encontrado libro para enviar\n");
				}
			}

			libroDevueltoDeBiblioteca = null;
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
