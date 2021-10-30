

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;


public class SocketCliente {
	
	
	public static final int PUERTO = 2017;
	public static final String IP_SERVER = "127.0.0.1";
	
	public static void main(String[] args) {
		System.out.println("        APLICACIÓN CLIENTE         ");
		System.out.println("-----------------------------------");
		
		
		try (Scanner sc = new Scanner(System.in);
			Socket socketAlServidor = new Socket()){
					
			
			
			System.out.println("CLIENTE: elija la opcion");
				System.out.println("1 - Consultar por ISBN");
				System.out.println("2 - Consultar por titulo");
				System.out.println("3 - Salir");
				
			int opcion = 0;	
			opcion = sc.nextInt();
			String isbmOp ;
			String Total;
			String resultado;
			
			
			switch (opcion) {
				case 1://Consultar por ISBN
					
					
					System.out.println("Iintroduce el  ISBN");
					String isbm = sc.nextLine();
					
					//para saber el tipo de busqueda
					isbmOp ="a";
					
					
					//En este caso voy a establecer que mi comunicación entre el cliente y el
					//servidor va a ser que le mando los dos numeros separados por un "-"
					//para luego en el servidor obtener ambmos numeros mediante el 
					//metodo split() de la clase String
					Total = isbm + "-" + isbmOp;
					
					
					
					
					
					break;
		
				case 2://Consultar por titulo
					
					
					System.out.println("Introduce el  titulo");
					String titulo = sc.nextLine();
					
					//para saber el tipo de busqueda
					isbmOp ="b";
					
					//En este caso voy a establecer que mi comunicación entre el cliente y el
					//servidor va a ser que le mando los dos numeros separados por un "-"
					//para luego en el servidor obtener ambmos numeros mediante el 
					//metodo split() de la clase String
					Total = titulo + "-" + isbmOp;
					
					
					
					
					break;
				
				case 3://Salir
					System.out.println("Fin del programa");
					break;
				
				}
			
				//---------------------------------------Enviar----------------------------//
				
				//Establecemos la conexión
				System.out.println("CLIENTE: Esperando a que el servidor acepte la conexión");
				socketAlServidor.connect(IP_SERVER);			
				System.out.println("CLIENTE: Conexion establecida... a " + IP_SERVER 
						+ " por el puerto " + PUERTO);	
			
				//creamos el objeto que manda la informacion al servidor
				PrintStream salida = new PrintStream(socketAlServidor.getOutputStream());
				
				//Mandamos la información por el Stream
				salida.println(Total);
				//---------------------------------------Recibir----------------------------//
				
				
				//Creamos el objeto que nos va a permitir leer la salida del servidor
				InputStreamReader entrada = new InputStreamReader(socketAlServidor.getInputStream());
						
				//Esta clase nos ayuda a leer datos del servidor linea a linea en vez de 
				//caracter a caracter como la clase InputStreamReader
				BufferedReader bf = new BufferedReader(entrada);
				
				System.out.println("CLIENTE: Esperando al resultado del servidor...");
				
				//En la siguiente linea se va a quedar parado el hilo principal
				//de ejecución hasta que el servidor responda, es decir haga un println
				resultado = bf.readLine();
					
				System.out.println("CLIENTE: El resultado de la suma es: " + resultado);
				
				
		} catch (UnknownHostException e) {
			System.err.println("CLIENTE: No encuentro el servidor en la dirección" + IP_SERVER);
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println("CLIENTE: Error de entrada/salida");
			e.printStackTrace();
		} catch (Exception e) {
			System.err.println("CLIENTE: Error -> " + e);
			e.printStackTrace();
		}
		
		System.out.println("CLIENTE: Fin del programa");
		}
		
		
	}



