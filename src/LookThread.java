import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;

public class LookThread extends Thread {

    BufferedReader in;
    PrintWriter out;
    HumanClientGUI hcg;
    JPanel lookInnerPanel;
    JLabel[][] lookWindow;
    JTextArea chatWindow;
    JLabel status;
    JComboBox<String> users;
    JFrame HumanClientGUIFrame;

    /**
     * Takes in parameters as the buffered reader, print writer and the gui itself
     */
    public LookThread(BufferedReader br, PrintWriter pw, JPanel lookPanel, JLabel[][] lookView, JTextArea chat, JLabel command, JFrame hcgf, JComboBox<String> userlist, HumanClientGUI humanClient) {
        in = br;
        out = pw;
        lookInnerPanel = lookPanel;
        lookWindow = lookView;
        hcg = humanClient;
        chatWindow = chat;
        status = command;
        users = userlist;
        HumanClientGUIFrame = hcgf;
    }

    /*
     * creating the image icons from the file
     */
    private ImageIcon floor = new ImageIcon(new ImageIcon("images/floor.png").getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT));
    private ImageIcon goldImage = new ImageIcon(new ImageIcon("images/gold.png").getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT));
    private ImageIcon human = new ImageIcon(new ImageIcon("images/human.png").getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT));
    private ImageIcon human2 = new ImageIcon(new ImageIcon("images/human2.png").getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT));
    private ImageIcon bot = new ImageIcon(new ImageIcon("images/bot.png").getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT));
    private ImageIcon exit = new ImageIcon(new ImageIcon("images/exit.png").getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT));
    private ImageIcon wall = new ImageIcon(new ImageIcon("images/wall.png").getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT));
    private ImageIcon lava = new ImageIcon(new ImageIcon("images/lava.png").getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT));

    /**
     * constantly loops to get the current surroundings of the player from game logic
     * checks that what is read in is a look and prints it to the screen
     * if it contains said, joined, left then it should be output to the chat window
     * If it contains congratulations or won the game then a message box will appear with the game result
     * Otherwise the line will be printed to the command status label
     */
    public void run() {
        try {
            out.println("look");
            while (true) {
                final String line = in.readLine().trim();
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {

                        if (line.length() == 25 && line.matches("[XEPGB#.]+")) {
                            out.println("look");
                            printLook(line);
                            // pass a command to get back a string of player names and then split them and put them in the combo box
                        } else {
                            if (line.contains("said") || line.contains("joined") || line.contains("left")) {
                                chatWindow.append(line + "\n");
                            } else if (line.equals("Congratulations!!!")) {
                                JOptionPane.showMessageDialog(HumanClientGUIFrame, "You won the Game");
                                System.exit(0);
                            } else if (line.contains("won the game.")) {
                                JOptionPane.showMessageDialog(HumanClientGUIFrame, "Sorry, you lost");
                                System.exit(0);
                            } else if (line.startsWith("ALL")){
                                out.println("players");
                                putPlayersInList(line);
                            }
                            else {
                                status.setText("SERVER RESPONSE: " + line);
                            }
                        }

                    }
                });
            }
        } catch (IOException | NullPointerException e) {
            hcg.disconnect();
        }
    }

    private void putPlayersInList(String line) {
        users.removeAllItems();
        String[] players = line.split(",");
        for(int i=0;i<players.length;i++)
        {
            users.addItem(players[i]);
        }
    }

    /**
     * The function print-look calls the look function to get the surroundings
     * of the player. The string is then cut up into a string array of single
     * characters. One by one the characters are fed into the char array and for
     * each 5th character a new line in the array is made.
     */
    public void printLook(String lookString) {
        //String[] lookLines;

        //lookLines = lookString.split("");
        //System.out.println(Arrays.toString(lookString));

        int i = 0; // i = line count, j = char count

        for (i = 0; i < lookString.length(); i++) {
            if (i == 12) {
                lookWindow[2][2].setIcon(human);
            } else
            {
                putInImage(lookString.charAt(i), i % 5, i/5);
            }

        }
        lookInnerPanel.repaint();
    }

    /**
     * putInImage take the char of the tile and converts that into the corresponding image
     */
    private void putInImage(char tile, int i, int j) {

        switch (tile) {
            case 'P':
                lookWindow[j][i].setIcon(human2);
                break;
            case 'B':
                lookWindow[j][i].setIcon(bot);
                break;
            case '.':
                lookWindow[j][i].setIcon(floor);
                break;
            case '#':
                lookWindow[j][i].setIcon(wall);
                break;
            case 'X':
                lookWindow[j][i].setIcon(lava);
                break;
            case 'G':
                lookWindow[j][i].setIcon(goldImage);
                break;
            case 'E':
                lookWindow[j][i].setIcon(exit);
                break;
        }
    }
}