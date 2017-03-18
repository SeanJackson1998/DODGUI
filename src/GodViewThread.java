import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

public class GodViewThread extends Thread {
    DODServerGUI dsg;
    GameLogic gl;

    /**
     * The gui itself and game logic are sent through as parameters
     */
    public GodViewThread(DODServerGUI ds, GameLogic game){
        dsg = ds;
        gl = game;
    }

    /**
     * constantly loops to get the current state of the map from game logic
     * The result is sent back from game logic and printed into the labels in the gui method refresh map
     */
    public void run(){
        char[][] map;
        while(true){
            dsg.refreshMap();
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
