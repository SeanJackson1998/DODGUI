import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by skatt on 17/03/2017.
 */
public class GodViewThread extends Thread {
    DODServerGUI dsg;
    GameLogic gl;
    public GodViewThread(DODServerGUI ds, GameLogic gl){
        dsg = ds;
    }

    public void run(){
        while(true){
            dsg.printGodView(gl.getGodView());
        }
    }
}
