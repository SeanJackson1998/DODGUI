/**
 * Inspiration taken from knock knock server
 */

import java.net.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.io.*;

public class DODServer{
	/**
	 * The DOD server will be made using a port number
	 * Then the server socket is made allowing clients to join
	 * then a user is created after the client joins the server
	 * Then a thread of the new client is created
	 */
	public static void main(String[] args) throws SocketException {

	    if (args.length != 1) {
	        System.err.println("Usage: java DODMultiServer <port number>");
	        System.exit(1);
	    }
	        int portNumber = Integer.parseInt(args[0]);
	        int playerID = 0;
	        GameLogic game = new GameLogic();
	        Map map = new Map();
	        map.readMap();
	        User user;
	        char type;
	        String name = "";
	        
	        // setting up the chat file for the next game
	        game.chatLogger.chatLog("---------------------------------------------------------------------------------");
	        game.chatLogger.chatLog("Game Started: " + getTime());
	        
	        try (ServerSocket serverSocket = new ServerSocket(portNumber)) { 
	            while (true) {
	            	Socket socket1 = serverSocket.accept();
		            BufferedReader in = new BufferedReader(new InputStreamReader(socket1.getInputStream()));
		            String playerType = in.readLine();
		            
	            	if(playerType.equals("human"))
		            {
		            	type = 'P';
		            	System.out.println("human connected");
		            	user = new User(map.getMapWidth(), map.getMapHeight(), playerID, 0, type, socket1, name);
		            	new ThreadClient(socket1,game,playerID, user, type, name).start();
				            playerID++;
		            }
		            else
		            {
		            	type = 'B';
		            	System.out.println("bot connected");
		            	user = new User(map.getMapWidth(), map.getMapHeight(), playerID, 0, type, socket1, name);
		            	new ThreadClient(socket1,game,playerID, user, type, name).start();
				            playerID++;
		            }
		        }
		    } catch (SocketException e) {
		    	System.err.println(e.getLocalizedMessage());
		    	System.exit(1);
		    } catch (IOException e) {
	            System.err.println("Could not listen on port " + portNumber);
	            System.exit(1);
	        }
	    }

	private static String getTime() {
		//getting current date and time using Date class
	    DateFormat DF = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
	    Calendar cal = Calendar.getInstance();    
	return DF.format(cal.getTime());
	}
}