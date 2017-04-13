import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;

/*
 * constantly updates the god view
 */
public class GodViewThread extends Thread {

    DODServerGUI dsg;
    GameLogic gl;
    JPanel lookInnerPanel;
    JLabel[][] godViewWindow;
    int smallest;

    private ImageIcon floor;
    private ImageIcon goldimage;
    private ImageIcon human2;
    private ImageIcon bot;
    private ImageIcon exit;
    private ImageIcon wall;
    private ImageIcon lava;
    /**
     * The gui itself and game logic are sent through as parameters
     */
    public GodViewThread(DODServerGUI ds, GameLogic game, JPanel lookPanel, JLabel[][] godView, int small) {
        dsg = ds;
        gl = game;
        lookInnerPanel = lookPanel;
        godViewWindow = godView;
        smallest=small;

        /*
         * instantiates the image icons and puts them into the correct size
         */
        floor = new ImageIcon(new ImageIcon("floor.png").getImage().getScaledInstance(smallest,smallest, Image.SCALE_SMOOTH));
        goldimage = new ImageIcon(new ImageIcon("gold.png").getImage().getScaledInstance(smallest,smallest , Image.SCALE_SMOOTH));
        human2 = new ImageIcon(new ImageIcon("human2.png").getImage().getScaledInstance(smallest,smallest , Image.SCALE_SMOOTH));
        bot = new ImageIcon(new ImageIcon("bot.png").getImage().getScaledInstance(smallest,smallest , Image.SCALE_SMOOTH));
        exit = new ImageIcon(new ImageIcon("exit.png").getImage().getScaledInstance(smallest,smallest , Image.SCALE_SMOOTH));
        wall = new ImageIcon(new ImageIcon("wall.png").getImage().getScaledInstance(smallest,smallest , Image.SCALE_SMOOTH));
        lava = new ImageIcon(new ImageIcon("lava.png").getImage().getScaledInstance(smallest,smallest , Image.SCALE_SMOOTH));
    }

    /**
     * constantly loops to get the current state of the map from game logic
     * The result is sent back from game logic and printed into the labels in the gui method refresh map
     */
    public void run() {
        while (true) {
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

        for (int i = 0; i < mapCharArray.length; i++) {
            for (int j = 0; j < mapCharArray[0].length; j++) {
                int[] location = {i, j};
                for (User u : players) {
                    int[] userLoc = {u.getY(), u.getX()};
                    if (Arrays.equals(location, userLoc)) {
                        mapCharArray[i][j] = u.getType();
                    }
                }
            }
        }

        for (int i = 0; i < mapCharArray.length; i++) {
            for (int j = 0; j < mapCharArray[0].length; j++) {
                switch (mapCharArray[i][j]) {
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
        // refreshes the panel updating all of the labels
        lookInnerPanel.repaint();
    }
}
