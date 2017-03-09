import java.net.Socket;

/**
 * The user class holds the x and y coordinates of the player, their id and the amount of gold they have
 */
public class User {
	private int x;
	private int y;
	private int id;
	private int gold;
	private char type;
	private String Name;
	private Socket socket = null;
	
	
	/**
	 * The User constructor gives all the attributes that were passed through to the new user
	 */
	User(int x, int y, int id, int gold, char type, Socket socket, String Name) {
		this.x = x;
		this.y = y;
		this.id = id;
		this.gold = 0;
		this.type = type;
		this.socket = socket;
		this.Name = Name;
	}

	/**
	 * This accessor gets the name for the user
	 */
	public String getName() {
		return Name;
	}
	
	/**
	 * This mutator sets the name for the user
	 */
	public String setName(String name) {
		Name = name;
		return Name;
	}
	
	/**
	 * This mutator sets the id for the user
	 */
	public int setID(int ID) {
		id = ID;
		return id;
	}
	
	/**
	 * This accessor gets the socket for the user
	 */
	public Socket getSocket() {
		return socket;
	}
	
	/**
	 * This mutator sets the socket for the user
	 */
	public Socket setSocket(Socket socket1) {
		socket = socket1;
		return socket;
	}
	
	/**
	 * This accessor gets the id for the user
	 */
	public int getID() {
		return id;
	}
	
	/**
	 * This mutator sets the type for the user
	 */
	public char setType(char Type) {
		type = Type;
		return type;
	}
	
	/**
	 * This accessor gets the type for the user
	 */
	public char getType() {
		return type;
	}
	
	/**
	 * This mutator sets the x coordinate for the user
	 */
	public int setX(int i) {
		x = i;
		return x;
	}
	
	/**
	 * This mutator sets the y coordinate for the user
	 */
	public int setY(int i) {
		y = i;
		return y;
	}
	
	/**
	 * This accessor gets the x coordinate for the user
	 */
	public int getX() {
		return x;
	}
	
	/**
	 * This accessor gets the y coordinate for the user
	 */
	public int getY() {
		return y;
	}

	/**
	 * This accessor gets the gold held for the user
	 */
	public int getGold() {
		return gold;
	}
	
	/**
	 * This mutator sets the gold held for the user
	 */
	public int setGold(int Gold) {
		gold = Gold;
		return gold;
	}
}
