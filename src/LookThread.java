import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by skatt on 16/03/2017.
 */
public class LookThread extends Thread {

    BufferedReader in;
    PrintWriter out;
    HumanClientGUI hcg;

    /**
     * Takes in parameters as the buffered reader, print writer and the gui itself
     */
    public LookThread(BufferedReader br, PrintWriter pw, HumanClientGUI hc){
        in = br;
        out = pw;
        hcg = hc;
    }

    /**
     * constantly loops to get the current surroundings of the player from game logic
     * The result is sent back from game logic and printed into the labels in the gui method print look
     */
    public void run(){
        try {
            while(true){
                out.println("look");
                hcg.printLook(in.readLine());
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException |NullPointerException e) {

        }
    }
}
