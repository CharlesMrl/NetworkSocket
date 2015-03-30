
package networksocket;

/**
 *
 * @author Antoine
 * 
 */
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
		
		while(message!=null) {
                        //exercice 4 lab 7
                        try {
                        
                        InputStreamReader myISR= new InputStreamReader(socket.getInputStream());
			BufferedReader in = new BufferedReader(myISR);
			message=in.readLine();
                        } catch (IOException e) {
			e.printStackTrace();
                        }
                        //exercie 9 lab7
                        //to change nickename
                        if(message.startsWith("/nick")){
                        //if the users write "/nick " we will put his nickname to the map of client corresponding to the socket
			myMapOfClient.put(socket, message.split(" ")[1]);
			}
                      
                        if(myMapOfClient.get(socket)!=null) //if there is a client
                        {//the given server transfer the message to the given client
                         server.transfer(socket, myMapOfClient+" : "+message);
                        }
                    }
                
                //Exercice 6 lab 7
		try {
                    //when we kill a client close the socket
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}
	

}
