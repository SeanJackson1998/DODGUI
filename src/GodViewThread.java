import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by skatt on 17/03/2017.
 */
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
            map = gl.getGodView(); // this is returning null
            System.out.print(map[0][0]);
            dsg.printGodView(map);
        }
    }
    /*public void printGodView(char[][] mapArray) {
        int i, j = 0; // i = row count, j = column count

        for (i = 0; i < map.getMapHeight(); i++) {
            for(j=0;j<map.getMapWidth();j++)
            {
                putInImage((mapArray[i][j]), i, j);
            }
        }

        // refresh panel here
    }

    private void putInImage(char tile, int i, int j) {

        switch (tile){
            case 'P':
                godViewWindow[i][j].setIcon(human2);
                break;
            case 'B':
                godViewWindow[i][j].setIcon(bot);
                break;
            case '.':
                godViewWindow[i][j].setIcon(floor);
                break;
            case '#':
                godViewWindow[i][j].setIcon(wall);
                break;
            case 'X':
                godViewWindow[i][j].setIcon(lava);
                break;
            case 'G':
                godViewWindow[i][j].setIcon(goldimage);
                break;
            case 'E':
                godViewWindow[i][j].setIcon(exit);
                break;
        }
    }*/

}
