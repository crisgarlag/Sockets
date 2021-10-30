package actividad2_Definitiva;

import java.io.IOException;

public class PrincipalSocketServidor {

	public static void main(String[] args) throws IOException {
		SocketServidor socketServidor= new SocketServidor();
		socketServidor.funcionDelServidor();
	}

}