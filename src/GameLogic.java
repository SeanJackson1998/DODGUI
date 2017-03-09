import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Random;

/**
 * Contains the main logic part of the game, as it processes.
 *
 * @author : The unnamed tutor.
 */
public class GameLogic {

	private Map map = new Map();
	private ArrayList<User> players;
	private int[] playerPosition;
	private int collectedGold;
	private boolean gameRunning = true;
	public SendToFile chatLogger;

	public GameLogic() {
		//list of all the users
		players = new ArrayList<>();

		// new chat logger to write to the log.txt file
		chatLogger = new SendToFile();

		// stores player positions on the map
		playerPosition = new int[2];

		// collectedGold[0] is human players total collected gold
		collectedGold = 0;

	}

	public boolean isGameRunning() {
		return gameRunning;
	}

	// get current players total of collected gold
	private int getPlayersCollectedGold() {
		return collectedGold;
	}

	// increment current players total of collected gold
	private void incrementPlayersCollectedGold() {
		collectedGold = collectedGold + 1;

	}

	// get current players x coordinate
	private int getPlayersXCoordinate() {
		return playerPosition[0];
	}

	// set current players x coordinate
	private void setPlayersXCoordinate(int newX) {
		playerPosition[0] = newX;
	}

	// get current players y coordinate
	private int getPlayersYCoordinate() {
		return playerPosition[1];
	}

	// set current players y coordinate
	private void setPlayersYCoordinate(int newY) {
		playerPosition[1] = newY;
	}

	/**
	 * Processes the command. It should return a reply in form of a String, as the protocol dictates.
	 * Otherwise it should return the string "Invalid".
	 *
	 * @return : Processed output or Invalid if the @param command is wrong.
	 */
	public String processCommand(String action, User user) {
		String[] command = action.trim().split(" ");
		String answer = "FAIL";

		switch (command[0].toUpperCase()) {
			case "HELLO":
				answer = hello();
				break;
			case "MOVE":
				if (command.length == 2) {
					answer = move(command[1].toUpperCase().charAt(0), user);
				}
				break;
			case "PICKUP":
				answer = pickup(user);
				break;
			case "LOOK":
				answer = look(user);
				break;
			case "QUIT":
				answer = quitGame(user);
				break;
			case "SHOUT":
				shout(action.substring(5).trim(), user);
				answer = "";
				break;
			default:
				answer = "FAIL";
		}

		return answer;
	}

	/**
	 * The shout method will send the global chat message to all of the human clients
	 * It will also call the send to file class so that the chat is recorded in the file.
	 * Then the output is sent to the out thread where it will be displayed to the client consoles
	 *
	 * However if the the chat is private and there is a name with a '$' at the start then the message will be sent to that play only
	 *
	 * @param shoutString
	 * @param user
	 */
	public void shout(String shoutString, User user) {

		String[] nameString = shoutString.split(" ");
		int nameLength = nameString[0].length();

		if (nameString[0].startsWith("$")) {
			for (User userTest : players) {
				try {
					if (((nameString[0].substring(1)).equals(userTest.getName())) && (userTest.getType() == 'P')) {
						PrintWriter output = new PrintWriter(userTest.getSocket().getOutputStream(), true);
						// write to file
						chatLogger.chatLog(user.getName() + " to " + userTest.getName() + ": " + shoutString.substring(nameLength).trim());
						output.println(user.getName() + " (PM) says: " + shoutString.substring(nameLength).trim());
					}

				} catch (IOException e) {
				}
			}
		}

		else {
			for (User userTest : players) {
				if (userTest.getID() != user.getID() && userTest.getType() == 'P') {
					try {
						PrintWriter output = new PrintWriter(userTest.getSocket().getOutputStream(), true);
						output.println(user.getName() + " says: " + shoutString);
					} catch (IOException e) {
						System.err.println("Shout Print Writer error");
					}
				}

			}
			chatLogger.chatLog(user.getName() + " to ALL: " + shoutString.trim());
		}
		try {
			PrintWriter output = new PrintWriter(user.getSocket().getOutputStream(), true);
			output.println("Message Sent");
		} catch (IOException e) {
			System.err.println("Shout Print Writer error");
		}
	}

	/**
	 * @return : Returns back gold player requires to exit the Dungeon.
	 */
	private String hello() {
		return "GOLD: " + (map.getGoldToWin() - getPlayersCollectedGold());
	}

	/**
	 * Checks if movement is legal and updates player's location on the map.
	 * <p>
	 * If the move is on an exit tile and a player has enough gold to leave, then the game will send them a victory message
	 * It will also set the gameRunning variable to false as the game is over
	 * This will trigger the finish game method in thread client
	 *
	 * @param direction : The direction of the movement.
	 * @return : Protocol if success or not.
	 */
	protected String move(char direction, User user) {
		int newX = getPlayersXCoordinate();
		int newY = getPlayersYCoordinate();
		switch (direction) {
			case 'N':
				newY -= 1;
				break;
			case 'E':
				newX += 1;
				break;
			case 'S':
				newY += 1;
				break;
			case 'W':
				newX -= 1;
				break;
			default:
				break;
		}

		if ((map.getTile(newX, newY) != '#') && checkMoveTile(newX, newY, user.getID())) {

			// set coordinates for player
			setPlayersXCoordinate(newX);
			setPlayersYCoordinate(newY);

			// set coordinates for object to new coordinates of player
			user.setX(newX);
			user.setY(newY);

			if (checkWin()) {
				gameRunning = false;
				return "Congratulations!!! \n You have escaped the Dungeon of Dooom!!!!!! \n Thank you for playing! \n Type 'quit' to leave the server";

			}
			return "SUCCESS";
		} else {
			return "FAIL";
		}
	}

	/**
	 * checks if the player collected all GOLD and is on the exit tile
	 *
	 * @return True if all conditions are met, false otherwise
	 */
	protected boolean checkWin() {
		if (getPlayersCollectedGold() >= map.getGoldToWin() && map.getTile(getPlayersXCoordinate(), getPlayersYCoordinate()) == 'E') {
			gameRunning = false;
			return true;
		}
		return false;
	}

	/**
	 * Converts the map from a 2D char array to a single string.
	 *
	 * @return : A String representation of the game map.
	 */
	public String look(User user) {
		// get look window for current player
		char[][] look = map.look(getPlayersXCoordinate(), getPlayersYCoordinate());

		// add current player's icon to look window
		look[2][2] = user.getType();

		// search for user coordinates in the array list and if they are between the bounds then print them
		User userTest;
		for (int i = 0; i <= players.size() - 1; i++) {
			userTest = players.get(i);

			int userXDif = getPlayersXCoordinate() - userTest.getX();
			int userYDif = getPlayersYCoordinate() - userTest.getY();

			if (Math.abs(userXDif) <= 2 && Math.abs(userYDif) <= 2) {
				// check the character for if its a BOT as well
				look[2 - userXDif][2 - userYDif] = userTest.getType();
			}
		}

		// return look window as a String for printing
		String lookWindow = "";
		for (int i = 0; i < look.length; i++) {
			for (int j = 0; j < look[i].length; j++) {
				lookWindow += look[j][i] + " ";
			}
			lookWindow += System.lineSeparator();
		}
		return lookWindow;
	}

	/**
	 * Processes the player's pickup command, updating the map and the player's gold amount.
	 *
	 * @return If the player successfully picked-up gold or not.
	 */
	protected String pickup(User user) {
		if (map.getTile(getPlayersXCoordinate(), getPlayersYCoordinate()) == 'G') {
			incrementPlayersCollectedGold();
			user.setGold(collectedGold);
			map.replaceTile(getPlayersXCoordinate(), getPlayersYCoordinate(), '.');
			return "SUCCESS, GOLD COINS: " + getPlayersCollectedGold();
		}

		return "FAIL There is nothing to pick up...";
	}

	/**
	 * Quits the game when called
	 *
	 * @return
	 */
	public synchronized String quitGame(User user) {
		playerConnected(user, " left the game.");
		return user.getName() + " left.";
	}

	/**
	 * removes the user from the array list of users
	 *
	 * @param user
	 */
	public synchronized void removeUser(User user) {
		players.remove(user);
	}

	/**
	 * Creates a new user with the id
	 * Passes data through to the constructor
	 * Then it adds the new user to an array list of users
	 */
	public synchronized void newUser(int id, char type, Socket socket1) {
		map.readMap();
		User user = new User(map.getMapWidth(), map.getMapHeight(), id, 0, type, socket1, "");
		players.add(user);
	}

	/**
	 * Takes the input and processes the command for the user
	 * Then it returns the processed command
	 */
	public synchronized String processInput(String command, User user) {
		String commandOut = processCommand(command, user);
		return commandOut;
	}

	/**
	 * SetUser sets the users information to the array variables for the game logic code to execute
	 */
	public synchronized void setUser(User user) {
		playerPosition[0] = user.getX();
		playerPosition[1] = user.getY();
		collectedGold = user.getGold();
	}

	/**
	 * Loops through the array list of users and finds the user with the requested id
	 * Then it returns the user its looking for
	 */
	public synchronized User getCorrectUser(int id) {
		for (User user : players) {
			if (user.getID() == id) {
				return user;
			}
		}
		return null;
	}

	/**
	 * Loops through the array list of users and finds the coordinates of each player
	 * Then it checks if the new player is about to spawn over an already taken tile
	 * If it is then its assigned a new random location on the map
	 * Then it returns the user
	 */
	public synchronized User checkSpawnTile(User user) {
		int x = user.getX();
		int y = user.getY();
		boolean onlyUser;
		char tile = map.getTile(x, y);
		Random random = new Random();

		do {
			onlyUser = true;
			x = random.nextInt(map.getMapWidth());
			y = random.nextInt(map.getMapHeight());
			user.setX(x);
			user.setY(y);
			tile = map.getTile(x, y);
			for (User userTest : players) {
				if ((userTest.getX() == user.getX() && userTest.getY() == user.getY()) && user.getID() != userTest.getID()) {
					onlyUser = false;
					break;
				}
			}
		} while (tile == '#' || !onlyUser);

		user.setX(x);
		user.setY(y);

		return user;
	}

	/**
	 * Loops through the array list of users and checks the coordinates of all the users with the new tile
	 * If the tile the user is about to move to is taken, then return false
	 */
	public boolean checkMoveTile(int x, int y, int ID) {
		boolean onlyUser = true;

		for (User userTest : players) {
			if ((userTest.getX() == x && userTest.getY() == y) && ID != userTest.getID()) {
				onlyUser = false;
				break;
			}
		}
		return onlyUser;
	}

	/**
	 * Checks if the bot is stood on gold, if so it will pick it up as normal
	 * else it will move randomly in one of the four directions
	 *
	 * @param user
	 * @return
	 */
	public synchronized String getBotsNextAction(User user) {
		String command = null;
		if (map.getTile(user.getX(), user.getY()) == 'G') {
			command = "PICKUP";
			return command;
		} else {
			Random random = new Random();
			final char[] DIRECTIONS = {'N', 'S', 'E', 'W'};
			command = "MOVE " + DIRECTIONS[random.nextInt(4)];
		}
		return command;
	}

	/**
	 * finish game will output to the other users who won.
	 * Then it will close the sockets to all the users and remove them from the array list
	 *
	 * @param user
	 * @throws IOException
	 */
	public void finishGame(User user) throws IOException {
		for (User userTest : players) {
			if (userTest.getID() != user.getID() && userTest.getType() == 'P') {
				try {
					PrintWriter output = new PrintWriter(userTest.getSocket().getOutputStream(), true);
					output.println(user.getName() + " won the game. \nThe game will now end. \nType 'quit' to exit.");
				} catch (SocketException e) {
					System.err.println("Failed to close socket on the user");
				}
			}
		}
		try {
			user.getSocket().close();
		} catch (IOException e) {
			System.err.println("Failed to close socket on the user");
		}
		removeUser(user);
		chatLogger.chatLog(user.getName() + " won the game.");
	}

	/**
	 * player Joined loops through the array list of players,
	 * and if they are a human then it will let them know a knew play has joined the game.
	 * However it can also be used to let players know if someone has left the game.
	 * Then it logs this in the file
	 *
	 * @param user
	 * @param message
	 */
	public void playerConnected(User user, String message) {
		for (User userTest : players) {
			if (userTest.getID() != user.getID() && user.getType() == 'P') {
				try {
					PrintWriter output = new PrintWriter(userTest.getSocket().getOutputStream(), true);
					output.println(user.getName() + message);
				} catch (IOException e) {
					e.printStackTrace();
					System.err.println("Print Writer error in pJ");
				}
			}
		}
		chatLogger.chatLog(user.getName() + message);
	}
}