import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

/**
 * Created by skatt on 16/03/2017.
 */
public class DODServerGUI {

    private ServerSocket serverSocket;
    private Map map;
    private GameLogic game;
    private ArrayList<ThreadClient> threads = new ArrayList<>();

    private JFrame DODServerGUIFrame;
    private static JPanel lookInnerPanel;
    private static JTextField AddressField;
    private static JTextField PortField;

    /**
     * array to hold the godView screen
     */
    private static JLabel[][] godViewWindow;

    /**
     * The images to be put into the god view grid
     */
    private ImageIcon floor = new ImageIcon(new ImageIcon("images/floor.png").getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT));
    private ImageIcon goldimage = new ImageIcon(new ImageIcon("images/gold.png").getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT));
    private ImageIcon human2 = new ImageIcon(new ImageIcon("images/human2.png").getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT));
    private ImageIcon bot = new ImageIcon(new ImageIcon("images/bot.png").getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT));
    private ImageIcon exit = new ImageIcon(new ImageIcon("images/exit.png").getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT));
    private ImageIcon wall = new ImageIcon(new ImageIcon("images/wall.png").getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT));
    private ImageIcon lava = new ImageIcon(new ImageIcon("images/lava.png").getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT));

    /**
     * constructor to build the server GUI
     */
    public DODServerGUI()
    {
        game = new GameLogic();
        map = game.getMapObj();
        godViewWindow = new JLabel[map.getMapHeight()][map.getMapWidth()];
    }

    private void makeGUI(){
        setUpServerGUI();
        setUpGodViewArray(map.getMapHeight(),map.getMapWidth());
        printGodView(map.getMap());
        DODServerGUIFrame.setVisible(true);
    }
    /**
     * Adds labels to the god view array ready to be filled in later
     */
    private void setUpGodViewArray(int y, int x) {
        for(int i=0;i<y;i++)
        {
            for(int j=0;j<x; j++)
            {
                godViewWindow[i][j] = new JLabel();

            }
        }
    }

    GridBagConstraints gbc = new GridBagConstraints();
    GridBagConstraints gbcForPanel = new GridBagConstraints();

    /**
     * Adds all of the components and panels to the frame
     */
    public void setUpServerGUI() {
        DODServerGUIFrame = new JFrame("DOD Server");

        DODServerGUIFrame.setSize((map.getMapWidth()*50)+100, (map.getMapHeight()*50)+300);
        DODServerGUIFrame.getContentPane().setBackground(Color.lightGray);
        DODServerGUIFrame.setResizable(false);
        DODServerGUIFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        DODServerGUIFrame.setLayout(new GridBagLayout());

        /**
         * Panel to store the panel which displays the gods Eye View
         */
        JPanel lookOuterPanel = new JPanel();
        lookOuterPanel.setLayout(new GridBagLayout());
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridheight = 3;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.BOTH;
        lookOuterPanel.setPreferredSize(new Dimension((map.getMapWidth()*50)+100, (map.getMapHeight()*50)+100));
        lookOuterPanel.setBackground(Color.black);
        DODServerGUIFrame.getContentPane().add(lookOuterPanel, gbc);

        /**
         * Panel to display the gods Eye View
         */
        lookInnerPanel = new JPanel();
        lookInnerPanel.setLayout(new GridLayout(map.getMapHeight(),map.getMapWidth()));
        gbc.gridx = 0;
        gbc.gridy = 0;
        lookInnerPanel.setPreferredSize(new Dimension(map.getMapWidth()*50, map.getMapHeight()*50));
        lookInnerPanel.setBackground(Color.red);
        lookOuterPanel.add(lookInnerPanel, gbc);

        /**
         * Panel to store IP Address and Port number of the server as well as the hide button of the god eye view
         */
        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new GridBagLayout());
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridheight = 1;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.BOTH;
        controlPanel.setPreferredSize(new Dimension(1200, 200));
        controlPanel.setBackground(Color.pink);
        DODServerGUIFrame.getContentPane().add(controlPanel, gbc);

        /**
         * Fills the fram with the panels
         */
        DODServerGUIFrame.pack();

        /**
         * Insets to create the correct amount of space between components
         */
        gbcForPanel.insets = new Insets(10,10,10,10);


        /**
         * A button to hide the visibility of the gods eye view
         */
        JButton hideGodView = new JButton("Hide");
        gbcForPanel.gridx = 0;
        gbcForPanel.gridy = 0;
        gbcForPanel.gridheight = 2;
        gbcForPanel.gridwidth = 3;
        gbcForPanel.fill = GridBagConstraints.BOTH;
        controlPanel.add(hideGodView, gbcForPanel);

        hideGodView.addActionListener(new ActionListener() {

            public synchronized void actionPerformed(ActionEvent e) {
                if(hideGodView.getText().equals("Hide"))
                {
                    lookInnerPanel.setVisible(false);
                    hideGodView.setText("Show");
                }
                else
                {
                    lookInnerPanel.setVisible(true);
                    hideGodView.setText("Hide");
                }
            }
        });



        JLabel portNumberLbl = new JLabel("Port Number:");
        gbcForPanel.gridx = 3;
        gbcForPanel.gridy = 0;
        gbcForPanel.gridheight = 1;
        gbcForPanel.gridwidth = 1;
        gbcForPanel.fill = GridBagConstraints.BOTH;
        controlPanel.add(portNumberLbl, gbcForPanel);

        /**
         * Text field to store the port number of the server
         */
        PortField = new JTextField(10);
        PortField.setEditable(true);
        gbcForPanel.gridx = 4;
        gbcForPanel.gridy = 0;
        gbcForPanel.gridheight = 1;
        gbcForPanel.gridwidth = 3;
        gbcForPanel.fill = GridBagConstraints.BOTH;
        controlPanel.add(PortField,gbcForPanel);

        JLabel IPAddress = new JLabel("IP Address:");
        gbcForPanel.gridx = 3;
        gbcForPanel.gridy = 1;
        gbcForPanel.gridheight = 1;
        gbcForPanel.gridwidth = 1;
        gbcForPanel.fill = GridBagConstraints.BOTH;
        controlPanel.add(IPAddress, gbcForPanel);

        /**
         * Text field to store the IP Address of the server
         */
        AddressField = new JTextField(10);
        AddressField.setEditable(false);
        gbcForPanel.gridx = 4;
        gbcForPanel.gridy = 1;
        gbcForPanel.gridheight = 1;
        gbcForPanel.gridwidth = 3;
        gbcForPanel.fill = GridBagConstraints.BOTH;
        controlPanel.add(AddressField,gbcForPanel);

        /**
         * Button to change the port of the server to the ones inputted in the text fields
         */
        JButton changePort = new JButton("Change");
        gbcForPanel.gridx = 7;
        gbcForPanel.gridy = 0;
        gbcForPanel.gridheight = 2;
        gbcForPanel.gridwidth = 1;
        gbcForPanel.fill = GridBagConstraints.BOTH;
        controlPanel.add(changePort, gbcForPanel);

        changePort.addActionListener(new ActionListener() {

            public synchronized void actionPerformed(ActionEvent e) {
                if(PortField.getText().matches("[0-9]+") && Integer.parseInt(PortField.getText()) < 65535)
                {
                    if(PortField.getText().equals(Integer.toString(portNumber)))
                    {
                        JOptionPane.showMessageDialog(DODServerGUIFrame, "You are already using this port");
                    }
                    else
                    {
                        portNumber = Integer.parseInt(PortField.getText());
                        setServerPort(portNumber);
                    }
                }
                else{
                    JOptionPane.showMessageDialog(DODServerGUIFrame, "Not a valid port number");
                }
            }
        });
    }

    /**
     * Method to initially put images in the labels and put them on the map
     */
    public void printGodView(char[][] mapArray) {
        int i, j = 0; // i = row count, j = column count

        for (i = 0; i < map.getMapHeight(); i++) {
            for(j=0;j<map.getMapWidth();j++)
            {
                putInImage((mapArray[i][j]), i, j);
            }
        }
    }

    /**
     * Method to initially store images in the labels and add them to the panel
     */
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
        lookInnerPanel.add(godViewWindow[i][j]);
    }


    private void setServerPort(int portNumber)
    {
        try {
            killPlayers();
            serverSocket.close();
            serverSocket = new ServerSocket((portNumber));
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void killPlayers()
    {
        for(int i=0; i<threads.size();i++) {
            threads.get(i).killPlayer(threads.get(i));
        }
    }

    /**
     * The DOD server will be made using a port number
     * Then the server socket is made allowing clients to join
     * then a user is created after the client joins the server
     * Then a thread of the new client is created
     */
    private static int portNumber;

    public static void main(String[] args) throws IOException {

        DODServerGUI ServerGUI = new DODServerGUI();

        if (args.length != 1) {
            System.err.println("Usage: java DODServerGUI <port number>");
            System.exit(1);
        }
        portNumber = Integer.parseInt(args[0]);
        int playerID = 0;
        User user;
        char type;
        String name = "";


        ServerGUI.makeGUI();

        //setting up the chat file for the next game
        ServerGUI.game.chatLogger.chatLog("---------------------------------------------------------------------------------");
        ServerGUI.game.chatLogger.chatLog("Game Started: " + getTime());

        PortField.setText(Integer.toString(portNumber));
        AddressField.setText((InetAddress.getLocalHost().getHostAddress()).toString());

        // initialising the god view thread so it is constantly updated
        new GodViewThread(ServerGUI, ServerGUI.game, lookInnerPanel, godViewWindow).start();

        try {
            ServerGUI.serverSocket = new ServerSocket(portNumber);
            while (true) {
                Socket socket1 = null;
                try {
                    socket1 = ServerGUI.serverSocket.accept();
                } catch (SocketException e) {
                    System.err.println("in first catch");
                    continue;
                }

                BufferedReader in = new BufferedReader(new InputStreamReader(socket1.getInputStream()));
                String playerType = in.readLine();

                if(playerType.equals("human"))
                {
                    type = 'P';
                    System.out.println("human connected");
                    user = new User(ServerGUI.map.getMapWidth(), ServerGUI.map.getMapHeight(), playerID, 0, type, socket1, name);
                    ThreadClient newthread = new ThreadClient(socket1,ServerGUI.game,playerID, user, type, name);
                    ServerGUI.threads.add(newthread);
                    newthread.start();
                    playerID++;
                }
                else
                {
                    type = 'B';
                    System.out.println("bot connected");
                    user = new User(ServerGUI.map.getMapWidth(), ServerGUI.map.getMapHeight(), playerID, 0, type, socket1, name);
                    ThreadClient newthread = new ThreadClient(socket1,ServerGUI.game,playerID, user, type, name);
                    ServerGUI.threads.add(newthread);
                    newthread.start();
                    playerID++;
                }
            }
        } catch (SocketException e) {
            System.err.println(e.getLocalizedMessage());
        } catch (IOException e) {
            System.err.println("Could not listen on port " + portNumber);
            System.exit(1);
        }
    }

    private static String getTime() {
        //getting current date and time using Date class
        DateFormat DF = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
        Calendar cal = Calendar.getInstance();
        return DF.format(cal.getTime());
    }
}