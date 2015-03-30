/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package networksocket;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author Antoine
 */
public class Server {
  
  
  HashMap <Socket,OutputStream> myMapofMessages;
  private ServerSocket server; //socket to permit exchange between client and server
  private Socket client;
  private int port;
  private InetAddress ip;
  
    
    //Exercice 1 lab7 : constructor with 2 parameters
    public Server(InetAddress ip,int port) throws IOException {
        this.port = port;
        this.ip = ip;
    }
    
    //Exercice 3&4 lab7 : start the server
    public void start(){
      try {
          //create a new server on the given port
          server=new ServerSocket(port);
          while(true){//listening (infinite loop)
              System.out.println("a new client has been connected");
              client=server.accept(); //we accept client
              //Exercice 7 lab7 : banner message for the client who connect
              client.getOutputStream().write("Welcome in server.java class: \n".getBytes());
              ClientHandler ch = new ClientHandler(client, this);
              ch.start();
              
          }
      } catch (IOException ex) {
          Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
      }
    }
    
    //Exercice 4 lab7 and exercice 8 lab 7
    public synchronized void transfer(Socket client, String message) {
        
        //When we enumerate the entries of a map, 
        //the iteration yields a series of objects which implement the Map 
        //Each one of these objects contains a key and a value.
        //so for each entry
        for (Map.Entry<Socket, OutputStream> entry : myMapofMessages.entrySet())
		{   
                    //if the client does not correspond (is not the same)
		    if(!entry.getKey().equals(client)) {
		    	try {   //if there is a message
		    		if(!message.isEmpty())
                                        //write the message and come back at the line
                                        
					entry.getValue().write((message+"\n").getBytes());
				} catch (IOException e) {
					e.printStackTrace();
				}
		    }
		    
		}
	}
    

}