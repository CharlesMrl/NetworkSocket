/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package networksocket;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author charlesmariller
 * Sources : stackoverflow, http://tutorials.jenkov.com/java-nio/
 */
public class MyServerChannel {

    /**
     * @param args the command line arguments
     */
    private InetAddress address;
    private int port;
    private HashMap <SocketChannel,OutputStream> myMapofMessages;
    
    private MyServerChannel(InetAddress myAdress, int myPort)
    {
        address = myAdress;
        port = myPort;
    }
    
    
    public void start() {
        try {
            /* variables */
            InetAddress add;
            ServerSocketChannel serverSocketChan;
            InetSocketAddress inetSocket;
            Selector mySelector;
            SelectionKey key;
            SocketChannel clientSocketChan = null;
            Set<SelectionKey> selectedKeys;
            Iterator<SelectionKey> keyIterator;
            ByteBuffer buf = null;
            String getMessage;
            int read;
            
            add = InetAddress.getByName("localhost");
            System.out.println("address "+add.getHostAddress());
            
            serverSocketChan = ServerSocketChannel.open();
            inetSocket = new InetSocketAddress(add, 8889);
            serverSocketChan.bind(inetSocket);
            
            mySelector = Selector.open();
            serverSocketChan.configureBlocking(false);
            key = serverSocketChan.register(mySelector, SelectionKey.OP_ACCEPT);
            
            // creating socket channel for client
            
            
            while(true)
            {
//                serverSocketChan.accept();
                
                selectedKeys = mySelector.selectedKeys();
                keyIterator = selectedKeys.iterator();
                
                while(keyIterator.hasNext()) {
                    
                    SelectionKey newKey = keyIterator.next();
                    buf.clear();
                    buf = ByteBuffer.allocate(500);
                    
                    if(newKey.isAcceptable()) {
                        // a connection was accepted by a ServerSocketChannel.
                        serverSocketChan.accept();
                        clientSocketChan.register(mySelector, SelectionKey.OP_READ );
                        clientSocketChan.write(buf);
                        buf.rewind();
                    } 
                    
                    else if (newKey.isReadable())
                    {
                        // channel is ready for reading
                        buf.clear();
                        StringBuilder sb = new StringBuilder();
                        read = 0;

                        while ((read = clientSocketChan.read(buf)) > 0) 
                        {
                            buf.flip();
                            byte[] bytes = new byte[buf.limit()];
                            buf.get(bytes);
                            sb.append(new String(bytes));
                            buf.clear();
                        }
                        
                        if (read<0)
                        {
                            getMessage = newKey.attachment() + "quit.\n";
                            clientSocketChan.close();
                        }
                        
                        else
                        {
                            getMessage = newKey.attachment() + " " + sb.toString();
                        }
                        System.out.println(getMessage);
                        transfer(clientSocketChan, getMessage);
                    } 
                    
                    keyIterator.remove();
                }
            }
            
        } catch (UnknownHostException ex) {
            Logger.getLogger(MyServerChannel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(MyServerChannel.class.getName()).log(Level.SEVERE, null, ex);
        }
            
            

        
    } 
    
    public synchronized void transfer(SocketChannel client, String message) {
        
        //When we enumerate the entries of a map, 
        //the iteration yields a series of objects which implement the Map 
        //Each one of these objects contains a key and a value.
        //so for each entry
        for (Map.Entry<SocketChannel, OutputStream> entry : myMapofMessages.entrySet())
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
