

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketServidor {
	
	public static final int PUERTO = 2017;
	
	public static void main(String[] args) throws InterruptedException {
		System.out.println("      APLICACIÓN DE SERVIDOR      ");
		System.out.println("----------------------------------");
		
		
		InputStreamReader entrada = null;
		PrintStream salida = null;
		Socket socketAlCliente = null;
		InetSocketAddress direccion = new InetSocketAddress(PUERTO);
		
		
		try (ServerSocket serverSocket = new ServerSocket()){			
			
			//Decimos al server socket que escuche peticiones desde el puerto
			//que hayamos establecido
			serverSocket.bind(direccion);
			
			//Vamos a llevar la cuenta del numero de peticiones que nos llegan
			int peticion = 0;
			
			
			while(true){		
				
				System.out.println("SERVIDOR: Esperando peticion por el puerto " + PUERTO);
				
				//En este punto, se parara el programa, hasta que entre la peticion de 
				//un cliente, y sera en ese momento cuando se cree un objeto Socket
				socketAlCliente = serverSocket.accept();
				System.out.println("SERVIDOR: peticion numero " + ++peticion + " recibida");
				
				entrada = new InputStreamReader(socketAlCliente.getInputStream());
				BufferedReader bf = new BufferedReader(entrada);
								
				//El servidor se quedaría aquí parado hasta que el cliente nos mande
				//informacion, es decir, cuando haga un salida.println(INFORMACION);				
				String stringRecibido = bf.readLine();
				
				//Hay que tener en cuenta que toda comunicacion entre cliente y servidor
				//esta en formato de cadena de texto
				
				System.out.println("SERVIDOR: Me ha llegado del cliente: " + stringRecibido);
				
				
				//hacemos un split por "-"para obtener la información.(sirve para separar strings
				String[] elementos = stringRecibido.split("-");
				
				String part1 = elementos[0];
				String part2 = elementos[1];
				boolean sol;
				
			
				
				
				String libro1 = "el ISBM es 0001, el libro se llama Libro1 escrito por Jaime y con un precio de 10 euros ";
				String libro2 = "el ISBM es 0002, el libro se llama Libro2  escrito por Pedro y con un precio de 15 euros ";
				String libro3 = "el ISBM es 0003, el libro se llama Libro3  escrito por Alvaro y con un precio de 20 euros ";
				String libro4 = "el ISBM es 0004, el libro se llama Libro4  escrito por Eve y con un precio de 21 euros ";
				String libro5 = "el ISBM es 0005, el libro se llama Libro5  escrito por Amanda y con un precio de 37 euros ";
				String resultado;
				
					
				if(part2.equals("a")) {
					//Buscamos por ISBM
					
					if (sol= libro1.contains(part1)) {
						resultado= libro1;
						//Mandamos el resultado al cliente
						salida = new PrintStream(socketAlCliente.getOutputStream());
						salida.println(resultado);	
						
						//Si hemos llegado hasta aqui, cerramos las conexiones
						socketAlCliente.close();
						
					}else if(sol= libro2.contains(part1)) {
						resultado= libro2;
						
						salida = new PrintStream(socketAlCliente.getOutputStream());
						salida.println(resultado);	
						socketAlCliente.close();
						
					}else if(sol= libro3.contains(part1)) {
						resultado= libro3;

						salida = new PrintStream(socketAlCliente.getOutputStream());
						salida.println(resultado);	
						socketAlCliente.close();
						
					}else if(sol= libro4.contains(part1)) {
						resultado= libro4;

						salida = new PrintStream(socketAlCliente.getOutputStream());
						salida.println(resultado);	
						socketAlCliente.close();
						
					}else if(sol= libro5.contains(part1)) {
						resultado= libro5;

						salida = new PrintStream(socketAlCliente.getOutputStream());
						salida.println(resultado);	
						socketAlCliente.close();
						
					}else {
						System.out.println("SERVIDOR: Libro no encontrado"  );
						salida.println("libro no encontrado");	
						socketAlCliente.close();
					}
					
					
					
				}else if(part2.equals("b")) {
					//Buscamos por título
					
					if (sol= libro1.contains(part1)) {
						resultado= libro1;
					}else if(sol= libro2.contains(part1)) {
						resultado= libro2;
						
					}else if(sol= libro3.contains(part1)) {
						resultado= libro3;
						
					}else if(sol= libro4.contains(part1)) {
						resultado= libro4;
						
					}else if(sol= libro5.contains(part1)) {
						resultado= libro5;
						
					}else {
						System.out.println("SERVIDOR: Libro no encontrado"  );
					}
					
				}
					
			}
			
			
			
			
			
		} catch (IOException e) {
			System.err.println("SERVIDOR: Error de entrada/salida");
			e.printStackTrace();
		} catch (Exception e) {
			System.err.println("SERVIDOR: Error -> " + e);
			e.printStackTrace();
		}
	}//FIN DEL PROGRAMA
}
