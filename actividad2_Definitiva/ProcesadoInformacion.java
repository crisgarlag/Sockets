package actividad2_Definitiva;

import java.util.ArrayList;

public class ProcesadoInformacion {

	private String texto = "";
	private String textoLibro;
	private String ISBN;
	private String titulo;
	private String autor;
	private String precio;
	private String[] datosRecibidosCliente;
	private String[] arrayDatoDevuelto;
	private Libro libroDevueltoDeBiblioteca;
	private Libro libroAñadidoABiblioteca;
	private ArrayList<Libro> bibliotecaDevueltaACliente;

	/**
	 * Transforma la informacion solicitada por el cliente para ser enviada por el
	 * servidor
	 * 
	 * @param informacionRecibida
	 */
	public String procesarInformacionAlCliente(ArrayList<Libro> bibliotecaDevuelta) {
		for (int i = 0; i < bibliotecaDevuelta.size(); i++) {

			ISBN = "ISBN:" + bibliotecaDevuelta.get(i).getISBN() + " ";
			titulo = "Titulo:" + bibliotecaDevuelta.get(i).getTitulo() + " ";
			autor = "Autor:" + bibliotecaDevuelta.get(i).getAutor() + " ";
			precio = "Precio:" + bibliotecaDevuelta.get(i).getPrecio();
			textoLibro = ISBN + titulo + autor + precio;

			if (texto.equals("")) {
				texto += textoLibro;
			} else {
				texto += "-" + textoLibro;
			}
		}
		return texto;
	}

	/**
	 * Transforma la informacion recibida por el cliente del servidor para que sea
	 * comprensible y apta para la lectura del usuario
	 * 
	 * @param informacionRecibida
	 */
	public void procesarInformacionDelServidor(String informacionRecibida) {

		System.out.println("\nLa informacion solicitada es la siguiente:\n");
		if (informacionRecibida != "") {
			arrayDatoDevuelto = informacionRecibida.split("-");

			for (int i = 0; i < arrayDatoDevuelto.length; i++) {
				System.out.println("Libro " + (i + 1));
				System.out.println(arrayDatoDevuelto[i] + "\n");
			}
		} else {
			System.out.println("No hay libros con la caracteristica elegida\n");
		}

	}
//PENDIENTE
	public void añadirLibro() {

		Double precio;
		libroAñadidoABiblioteca = new Libro();
		libroAñadidoABiblioteca.setISBN(datosRecibidosCliente[1]);
		libroAñadidoABiblioteca.setTitulo(datosRecibidosCliente[2]);
		libroAñadidoABiblioteca.setAutor(datosRecibidosCliente[3]);
		precio = Double.parseDouble(datosRecibidosCliente[4]);
		libroAñadidoABiblioteca.setPrecio(precio);

		SocketServidor.bibliotecaPrincipal.add(libroAñadidoABiblioteca);

	}

	public ArrayList<Libro> buscarPorISBN(String datoBusqueda) {
		for (int i = 0; i < SocketServidor.bibliotecaPrincipal.size(); i++) {
			if (datoBusqueda.equalsIgnoreCase(SocketServidor.bibliotecaPrincipal.get(i).getISBN())) {
				libroDevueltoDeBiblioteca = SocketServidor.bibliotecaPrincipal.get(i);
				bibliotecaDevueltaACliente.add(libroDevueltoDeBiblioteca);
			}
		}
		return bibliotecaDevueltaACliente;
	}

	public ArrayList<Libro> buscarPorTitulo(String datoBusqueda) {
		for (int i = 0; i < SocketServidor.bibliotecaPrincipal.size(); i++) {
			if (datoBusqueda.equalsIgnoreCase(SocketServidor.bibliotecaPrincipal.get(i).getTitulo())) {
				libroDevueltoDeBiblioteca = SocketServidor.bibliotecaPrincipal.get(i);
				bibliotecaDevueltaACliente.add(libroDevueltoDeBiblioteca);
			}
		}
		return bibliotecaDevueltaACliente;
	}

	public ArrayList<Libro> buscarPorAutor(String datoBusqueda) {
		for (int i = 0; i < SocketServidor.bibliotecaPrincipal.size(); i++) {
			if (datoBusqueda.equalsIgnoreCase(SocketServidor.bibliotecaPrincipal.get(i).getAutor())) {
				libroDevueltoDeBiblioteca = SocketServidor.bibliotecaPrincipal.get(i);
				bibliotecaDevueltaACliente.add(libroDevueltoDeBiblioteca);
			}
		}
		return bibliotecaDevueltaACliente;
	}
}
