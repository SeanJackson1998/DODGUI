import javax.swing.*;
import java.awt.*;
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
    JPanel lookInnerPanel;
    JLabel[][] lookWindow;

    /**
     * Takes in parameters as the buffered reader, print writer and the gui itself
     */
    public LookThread(BufferedReader br, PrintWriter pw, JPanel lookPanel, JLabel[][] lookView){
        in = br;
        out = pw;
        lookInnerPanel = lookPanel;
        lookWindow = lookView;
    }


    private ImageIcon floor = new ImageIcon(new ImageIcon("images/floor.png").getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT));
    private ImageIcon goldimage = new ImageIcon(new ImageIcon("images/gold.png").getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT));
    private ImageIcon human = new ImageIcon(new ImageIcon("images/human.png").getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT));
    private ImageIcon human2 = new ImageIcon(new ImageIcon("images/human2.png").getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT));
    private ImageIcon bot = new ImageIcon(new ImageIcon("images/bot.png").getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT));
    private ImageIcon exit = new ImageIcon(new ImageIcon("images/exit.png").getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT));
    private ImageIcon wall = new ImageIcon(new ImageIcon("images/wall.png").getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT));
    private ImageIcon lava = new ImageIcon(new ImageIcon("images/lava.png").getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT));


    /**
     * constantly loops to get the current surroundings of the player from game logic
     * The result is sent back from game logic and printed into the labels in the gui method print look
     */
    public void run(){
        try {
            while(true){
                out.println("look");
                printLook(in.readLine());

                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException |NullPointerException e) {

        }
    }

    /**
     * The function print-look calls the look function to get the surroundings
     * of the player. The string is then cut up into a string array of single
     * characters. One by one the characters are fed into the char array and for
     * each 5th character a new line in the array is made.
     */
    public void printLook(String lookString) {
        String[] lookLines;

        lookLines = lookString.split("");

        int i, j = 0; // i = line count, j = char count

        for (i = 0; i < lookString.length(); i++) {
            if(i==12)
            {
                lookWindow[2][2].setIcon(human);
            }
            else
            {
                if (i % 5 == 0 && i != 0) {
                    j++;
                    putInImage(lookLines[i], i % 5, j);
                } else {
                    if (j == 0) {
                        putInImage(lookLines[i], i%5, j);
                    } else {
                        putInImage(lookLines[i], i % 5, j);
                    }
                }
            }
        }
        lookInnerPanel.repaint();
    }

    /**
     * putInImage take the char of the tile and converts that into the corresponding image
     */
    private void putInImage(String tile, int i, int j) {

        switch (tile){
            case "P":
                lookWindow[j][i].setIcon(human2);
                break;
            case "B":
                lookWindow[j][i].setIcon(bot);
                break;
            case ".":
                lookWindow[j][i].setIcon(floor);
                break;
            case "#":
                lookWindow[j][i].setIcon(wall);
                break;
            case "X":
                lookWindow[j][i].setIcon(lava);
                break;
            case "G":
                lookWindow[j][i].setIcon(goldimage);
                break;
            case "E":
                lookWindow[j][i].setIcon(exit);
                break;
        }
    }

}
