import java.io.BufferedReader;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class SocketServidorHilo {
	
	public static final int PUERTO = 2018;
	
	public static void main(String[] args) {
		
		ArrayList<Libro> listaLibros= new ArrayList<>();
		
		listaLibros.add(new Libro(1, "Harry Potter", "J.K Rowling", 35));
		listaLibros.add(new Libro(2, "El señor de los anillos", "J.R.R Tolkien", 30));
		listaLibros.add(new Libro(3, "Lo que el viento se llevó", "Margaret Mitchell", 25));
		listaLibros.add(new Libro(4, "El código DaVinci", "Dan Brown", 25));
		listaLibros.add(new Libro(5, "El alquimista", "Paulo Coelho", 20));
		
		System.out.println("         SERVIDOR BIBLIOTECA          ");
		System.out.println("--------------------------------------");
		
		int peticion = 0;
		
		try (ServerSocket servidor = new ServerSocket()){
			InetSocketAddress direccion = new InetSocketAddress(PUERTO);
			servidor.bind(direccion);
			System.out.println("Esperando petición por el puerto " + PUERTO);
						
			while (true) {
				Socket socketAlCliente = servidor.accept();
				System.out.println("Petición: " + ++peticion + "recogida");
				new Hilo(socketAlCliente);
			}
		}catch (IOException e) {
			System.err.println("SERVIDOR: Error de entrada/salida");
			e.printStackTrace();
		} catch (Exception e) {
			System.err.println("SERVIDOR: Error");
			e.printStackTrace();
		}
	}
}
