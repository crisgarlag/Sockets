import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class SocketCliente {

	public static final int PUERTO = 2018;
	public static final String IP_SERVER = "localhost";
	
	public static void main(String[] args) {
		System.out.println("      BIBLIOTECA VIRTUAL     ");
		System.out.println("-----------------------------");		
		
		InetSocketAddress direccionServidor = new InetSocketAddress(IP_SERVER, PUERTO);
		
		try(Scanner sc = new Scanner(System.in)){
			System.out.println("CLIENTE: Esperando a que el servidor acepte la conexión");
			Socket socketAlServidor = new Socket();
			socketAlServidor.connect(direccionServidor);
			System.out.println("CLIENTE: Conexion establecida... a " + IP_SERVER + 
					" por el puerto " + PUERTO);
			InputStreamReader entrada = new InputStreamReader(socketAlServidor.getInputStream());
			BufferedReader entradaBuffer = new BufferedReader(entrada);
			PrintStream salida = new PrintStream(socketAlServidor.getOutputStream());
			String texto = "";
			boolean continuar = true;
			
			do {
				System.out.println("1-> Buscar libro por ISBN");
				System.out.println("2-> Buscar libro por título");
				System.out.println("3-> Salir de la aplicación");
				System.out.println("-----------------------------");
				System.out.println("Elija una opción:");
				texto = sc.nextLine();
				salida.println(texto);
				while(texto != "1" && texto != "2" && texto != "3") {
					int numero = 0;
					
					switch(texto) {
					case "1":
						System.out.println("Escriba el ISBN del libro");
						numero = sc.nextInt();
						break;
					case "2":
						System.out.println("Escribe el título del libro");
						numero = sc.nextInt();
						break;
					case "3":
						continuar = false;
						break;
					default:
						System.out.println("Escribe un número válido.");
						break;
					}
					
				}
				String respuesta = entradaBuffer.readLine();
				
			}while(continuar);
			socketAlServidor.close();
		}catch (UnknownHostException e) {
			System.err.println("CLIENTE: No encuentro el servidor en la dirección" + IP_SERVER);
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println("CLIENTE: Error de entrada/salida");
			e.printStackTrace();
		} catch (Exception e) {
			System.err.println("CLIENTE: Error -> " + e);
			e.printStackTrace();
		}
	}
}