
package networksocket;

/**
 *
 * @author Antoine
 */
import com.sun.corba.se.spi.activation.Server;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.HashMap;

//exercice 5&7&8&9 lab7
public class ClientHandler extends Thread implements Runnable {
	
	Socket socket;
	String message;
	Server server;
	HashMap <Socket, String> myMapOfClient;
	
	public ClientHandler (Socket s, Server server) {
		this.socket = s;
		this.message = "";
		this.server = server;
	}

        @Override
	public void run() {
		server.transfer(socket, message);
		while(message!=null) {
                        //exercice 4 lab 7
                        try {
			BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			message=in.readLine();
                        } catch (IOException e) {
			e.printStackTrace();
                        }
                        //exercie 9 lab7
                        if(message.startsWith("/nick")){
			myMapOfClient.put(socket, message.split(" ")[1]);
			}
		}
                
                //Exercice 6 lab 7
		try {
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}
	

}
