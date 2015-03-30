/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package networksocket;

import gnu.getopt.Getopt;
import gnu.getopt.LongOpt;
import java.io.IOException;
import java.net.InetAddress;


    
/**
 *
 * @author Camille
*/

public class Main{
    
    
           public static void main(String[] args) throws IOException{
                
                //Initialisation of the ip address and the port
               String ip = "127.0.0.1";
               int port = 31003;
               int serverOp = 0;
               int multicast = 0;
               int nio = 0;
               

                //Definition of the different program options possible with long arguments variable
                LongOpt[] longopts = new LongOpt[6];
		longopts[0] = new LongOpt ( "help" , LongOpt.NO_ARGUMENT, null , 'h' ) ;
		longopts[1] = new LongOpt ( "a" , LongOpt.REQUIRED_ARGUMENT  , null , 'a' );
                longopts[2] = new LongOpt ( "m" , LongOpt.NO_ARGUMENT , null , 'm' ) ;
                longopts[3] = new LongOpt ( "n" , LongOpt.NO_ARGUMENT , null , 'n' ) ;
                longopts[4] = new LongOpt ( "p" , LongOpt.REQUIRED_ARGUMENT  , null , 'p' ) ;
                longopts[5] = new LongOpt ( "s" , LongOpt.NO_ARGUMENT , null , 's' ) ;
                
                //Get the arguments pass by the user
		Getopt g = new Getopt ( "Tchat Java" , args, "ha:dhmnp:s" ,longopts ) ;
		int opt ;
       while ((opt=g.getopt())!=-1){
       switch ( opt ) {
				
                                case 'h':
				System.out.println("Usage: java -jar target/multichat -0.0.1-SNAPSHOT.jar [OPTION]...");
                                System.out.println("");
                                System.out.println("-a, --address=ADDR set the IP address");
                                System.out.println("-h, --help display this help and quit");
                                System.out.println("-m, --multicast start the client en multicast mode");
                                System.out.println("-n, --nio configure the server in NIO mode");
                                System.out.println("-p, --port=PORT set the port");
                                System.out.println("-s, --server start the server");
				System.exit(0);
                                break;
                                case 'a':
                                    ip = g.getOptarg();
                                        System.out.println("The new ip address of the server is : "+ ip);
				
				break;
				case 'm':
                                    multicast = 1;
					System.out.println("The mode multicast is on");
					break;
				case 'n':
                                    nio=1;
					System.out.println("The nio is activated");
                                        break;
                                case 'p':
                                    port = Integer.parseInt(g.getOptarg());
					System.out.println("The new port number for the server is : "+port);
                                        break;
                                case 's':
                                    serverOp = 1;
					System.out.println("We run the server") ;
                                        
                                        break;
                                default :
				System.out.println("Invalid option");
			}
       
   }
       
       
       /// A MODIFIER
       if (serverOp==1) {
    if (nio==1) 
    {
        MyServerChannel server = new MyServerChannel(InetAddress.getByName(ip), port);
        server.start();
    } 
    else 
    {
        MyServer server = new MyServer( InetAddress.getByName(ip), port);
        server.start();
    }
    
    } 
       else 
       {
            String[] parameters = new String[3];
            parameters[0] = ip;
            parameters[1] = Integer.toString(port);
            if (multicast==1)
            parameters[2] = "1";
            else
            parameters[2] = "0";
            
            javafx.application.Application.launch(WindowsClient.class, parameters);

       }
           }
}
       