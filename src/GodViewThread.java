import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;

public class GodViewThread extends Thread {
    DODServerGUI dsg;
    GameLogic gl;
    JPanel lookInnerPanel;
    JLabel[][] godViewWindow;



    /**
     * The gui itself and game logic are sent through as parameters
     */
    public GodViewThread(DODServerGUI ds, GameLogic game, JPanel lookPanel, JLabel[][] godView){
        dsg = ds;
        gl = game;
        lookInnerPanel = lookPanel;
        godViewWindow = godView;
    }

    private ImageIcon floor = new ImageIcon(new ImageIcon("images/floor.png").getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT));
    private ImageIcon goldimage = new ImageIcon(new ImageIcon("images/gold.png").getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT));
    private ImageIcon human2 = new ImageIcon(new ImageIcon("images/human2.png").getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT));
    private ImageIcon bot = new ImageIcon(new ImageIcon("images/bot.png").getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT));
    private ImageIcon exit = new ImageIcon(new ImageIcon("images/exit.png").getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT));
    private ImageIcon wall = new ImageIcon(new ImageIcon("images/wall.png").getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT));
    private ImageIcon lava = new ImageIcon(new ImageIcon("images/lava.png").getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT));

    /**
     * constantly loops to get the current state of the map from game logic
     * The result is sent back from game logic and printed into the labels in the gui method refresh map
     */
    public void run(){
        while(true){
            ArrayList<User> players = gl.getPlayers();
            refreshMap(players);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Method to get the current map with the players on and output the map in the form of images
     */
    public void refreshMap(ArrayList<User> players) {
        char[][] mapCharArray = gl.getGodView();

        for(int i = 0; i < mapCharArray.length; i ++){
            for(int j = 0; j < mapCharArray[0].length; j++){
                int[] location = {i,j};
                for(User u : players){
                    int[] userLoc = {u.getY(), u.getX()};
                    if(Arrays.equals(location, userLoc)){
                        mapCharArray[i][j] = u.getType();
                    }
                }
            }
        }

        for (int i = 0; i < mapCharArray.length; i++) {
            for (int j = 0; j < mapCharArray[0].length; j++) {
                switch (mapCharArray[i][j]){
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
            }
        }
        /**
         * refreshes the panel updating all of the labels
         */
        lookInnerPanel.repaint();


    }
}
