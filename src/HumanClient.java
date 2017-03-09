/**
 * Inspiration taken from knock knock client
 */

import java.io.*;
import java.net.*;
 
public class HumanClient {
	
	/**
	 * The human client uses the host name and the port number to connect to the host
	 * Then the client socket is made so that it can send and receive data to and from the server
	 * Then an introduction on how to use the chat comes up
	 * Using a buffered reader to get input, it is then sent to the thread client class using the print writer, 
	 * and the processed command is returned for output
	 */
	public static void main(String[] args) throws IOException {
        
        if (args.length != 2) {
            System.err.println(
                "Usage: java HumanClient <host name> <port number>");
            System.exit(1);
        }

        String hostName = args[0];
        int portNumber = Integer.parseInt(args[1]);
        
        try (
            Socket clientSocket = new Socket(hostName, portNumber);
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        ) {
            BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));

            out.println("human");

            String fromUser;

            System.out.println("Connected");
            System.out.print("Choose a name: ");

            String name;
            
            fromUser = stdIn.readLine();
            name = fromUser; 
            out.println(fromUser);

            System.out.println("Welcome to dungeons of doom " + name + "!");
            System.out.println("You already know most of the commands, but with the new chat system there's a few more.");
            System.out.println("To chat publicly, type 'SHOUT message'");
            System.out.println("To chat privately, type 'SHOUT $name message'");
            System.out.println("To see the chat look in log.txt");
            System.out.println("GOOD LUCK!");
            
            // OutThread to output anything to the terminal that is waiting to be displayed
            new OutThread(in).start();
            
            while (!(fromUser = stdIn.readLine()).equalsIgnoreCase("quit")) {
            	System.out.println(name + ": " + fromUser);
                out.println(fromUser);
            }
            
            System.out.println(name + ": " + fromUser);
            out.println(fromUser);

        } catch (UnknownHostException e) {
            System.err.println("Don't know about host " + hostName);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to " + hostName);
            System.exit(1);
        }
    }
}