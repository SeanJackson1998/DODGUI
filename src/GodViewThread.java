import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

public class GodViewThread extends Thread {
    DODServerGUI dsg;
    GameLogic gl;
    public GodViewThread(DODServerGUI ds, GameLogic game){
        dsg = ds;
        gl = game;
    }

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
