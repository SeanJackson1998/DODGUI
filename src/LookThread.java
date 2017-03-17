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
    public LookThread(BufferedReader br, PrintWriter pw, HumanClientGUI hc){

        in = br;
        out = pw;
        hcg = hc;
    }

    public void run(){
        try {
            while(true){
                out.println("look");
                hcg.printLook(in.readLine());
            }
        } catch (IOException |NullPointerException e) {

        }
    }
}
