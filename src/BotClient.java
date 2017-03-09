import java.io.*;
import java.net.*;
 
public class BotClient {
	
	/**
	 * The BOT client uses the host name and the port number to connect to the host
	 * Then the client socket is made so that it can send and receive data to and from the server
	 * Using a print writer to send to the server for input, 
	 * and the processed command is returned for output via the buffered reader
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
	    	out.println("bot");
	    	
	        String fromServer = " ";
	        System.out.println("Connected");
	        
	        while (fromServer != null) {  
	        	out.println("");
	        	fromServer = in.readLine();
	        	System.out.println("Server: " + fromServer);        	
	        }
	    } catch (UnknownHostException e) {
	        System.err.println("Don't know about host " + hostName);
	        System.exit(1);
	    } catch (IOException e) {
	        System.err.println("Couldn't get I/O for the connection to " +
	            hostName);
	        System.exit(1);
	    }
	}
}