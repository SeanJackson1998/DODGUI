/**
 * Inspiration taken from knock knock multi-server thread
 */

import java.net.*;
import java.io.*;

/**
 * The client thread creates a thread which holds the socket, the game logic, the id of the thread and the user its related to.
 */
public class ThreadClient extends Thread{
    private Socket socket = null;
    private GameLogic game = null;
	private int playerID;
	private char type = 'P';
	private User correctUser;
	private String Name;
    
    public ThreadClient(Socket socket, GameLogic game, int playerID, User user, char type, String Name) {
        super();
        this.socket = socket;
        this.game = game;
        this.playerID = playerID;
        this.correctUser = user;
        this.type = type;
        this.Name = Name;
    }
    
    
    /* The new user is fully created through game logic and the user is then checked against their id to make sure they have the id they were given
    * Then the user is set so that it is not created outside the map or on top of another player
    * Then while the thread receives the input it processes it with the user and outputs it back to the human client
    * If a player is a BOT type then it outputs nothing
    */
    public void run() {
        try (
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        ) {
        	// from the client and from the server
        	String inputLine, outputLine = null;
        	// to check if the player has quit or not
        	boolean quitbool = false;
        	game.newUser(playerID, type, socket);
        	correctUser = game.getCorrectUser(playerID);
        	game.setUser(correctUser);
        	correctUser = game.checkSpawnTile(correctUser);
        	
            if(correctUser.getType() == 'B')
            {
            	String command;
            	Name = "Bot" + Integer.toString(playerID);
            	correctUser.setName(Name);
				game.playerConnected(correctUser, " joined the game.");

            	/**
            	 * while the game is running, the bot is set to the user like the human client,
            	 * Then the automated random commands are generated and processed like the human client
            	 * Then to make it a fair game, the bot client sleeps for 2 seconds giving the human a fair chance
            	 */
            	while(game.isGameRunning())
            	{
            		correctUser = game.getCorrectUser(playerID);
	            	game.setUser(correctUser);
            		command = game.getBotsNextAction(correctUser);
            		outputLine = game.processInput(command, correctUser);       		
            		try {
            			//out.println(command);
						Thread.sleep(2000);
					} catch (InterruptedException e) {
						System.err.println("sleep is messed up");
					}
            	}
				game.finishGame(correctUser);
            }
            else{

            	Name = in.readLine();
        		correctUser.setName(Name);

            	game.playerConnected(correctUser, " joined the game.");

            	while ((inputLine = in.readLine()) != null && !correctUser.getSocket().isClosed() && game.isGameRunning()){
	            	// sets it to the player coordinates of this thread
	            	correctUser = game.getCorrectUser(playerID);
	            	game.setUser(correctUser);

	                outputLine = game.processInput(inputLine, correctUser);
	                out.println(outputLine);
	                // if the player has said they want to quit then remove them from the game
	                if(inputLine.equalsIgnoreCase("quit"))
	                {
	                	game.removeUser(correctUser);
	                }

					/** if the user didn't quit but the while loop still exited then the game must be over
					 this calls finish game */

					if(!game.isGameRunning())
					{
						game.finishGame(correctUser);
					}
	            }
        }

        } catch (SocketException e) {
        	if(game.isGameRunning())
        	{
        		System.err.println("Client closed incorrectly: use the quit command in future.");
            	game.quitGame(correctUser);
            	game.removeUser(correctUser);
        	}
        	else{
        		game.quitGame(correctUser);
            	game.removeUser(correctUser);
        	}
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
}