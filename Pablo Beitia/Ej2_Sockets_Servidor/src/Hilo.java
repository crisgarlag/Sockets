import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

public class Hilo implements Runnable{
	private Thread hilo;
	private static int numCliente = 0;
	private Socket socketAlCliente;
	
	public Hilo(Socket socketAlCliente) {
		numCliente++;
		hilo = new Thread(this, "Cliente_" + numCliente);
		this.socketAlCliente = socketAlCliente;
		hilo.start();
	}
	
	@Override
	public void run() {
		System.out.println("Estableciendo comunicación con " + hilo.getName());
		PrintStream salida = null;
		InputStreamReader entrada = null;
		BufferedReader entradaBuffer = null;
		
		try {
			//Salida del servidor al cliente
			salida = new PrintStream(socketAlCliente.getOutputStream());
			//Entrada del servidor al cliente
			entrada = new InputStreamReader(socketAlCliente.getInputStream());
			entradaBuffer = new BufferedReader(entrada);
			
			String texto = "";
			boolean continuar = true;
			
			while(continuar) {
				texto = entradaBuffer.readLine();
				if (texto.trim().equalsIgnoreCase("3")) {
					salida.println("Fin aplicación");
					System.out.println(hilo.getName() + " ha cerrado la comunicación");
					continuar = false;
				}			
			}
			
		}catch (IOException e) {
			System.err.println("HiloContadorLetras: Error de entrada/salida");
			e.printStackTrace();
		} catch (Exception e) {
			System.err.println("HiloContadorLetras: Error");
			e.printStackTrace();
		}
	}	
}
