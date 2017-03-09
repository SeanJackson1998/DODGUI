/**
 * Inspiration taken from Manav Gupta
 */

import java.io.BufferedReader;
import java.io.IOException;

/**
 * This class is a thread to output to the clients terminal without them having to trigger it
 * So when another player shouts, this thread will output the command to the clients terminal
 */

public class OutThread extends Thread {
	 BufferedReader in;
	 public OutThread(BufferedReader br){
	    in = br;
	 }
	    
	 public void run(){
	     try {
	         String fromServer = "";
	         while(true){
	             if(!(fromServer = in.readLine()).isEmpty()){
	             	System.out.println("SERVER: " + fromServer);
	             }
	         }
	     } catch (IOException|NullPointerException e) {
	     	System.out.println("Disconnected from Server: type 'quit' to exit");
	     }	    
	 }
}