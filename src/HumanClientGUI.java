import com.sun.deploy.panel.JavaPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Random;

public class HumanClientGUI{


    private static Socket clientSocket;
    private static PrintWriter out;
    private static BufferedReader in;

    private JLabel goldCollected;
    private JLabel CommandStatus;
    private  int gold = 0;
    private static JTextField PortField;
    private static JTextField IPField;
    private boolean winner = false;

    /**
     * different layouts for the different panels
     */
    GridBagConstraints gbc = new GridBagConstraints();
    GridBagConstraints gbcForPanel = new GridBagConstraints();
    GridBagConstraints gbcForChatPanel = new GridBagConstraints();
    GridBagConstraints gbcForIPPanel = new GridBagConstraints();

    /**
     * The images to be put into the look grid
     */
    private ImageIcon floor = new ImageIcon(new ImageIcon("images/floor.png").getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT));
    private ImageIcon goldimage = new ImageIcon(new ImageIcon("images/gold.png").getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT));
    private ImageIcon human = new ImageIcon(new ImageIcon("images/human.png").getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT));
    private ImageIcon human2 = new ImageIcon(new ImageIcon("images/human2.png").getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT));
    private ImageIcon bot = new ImageIcon(new ImageIcon("images/bot.png").getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT));
    private ImageIcon exit = new ImageIcon(new ImageIcon("images/exit.png").getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT));
    private ImageIcon wall = new ImageIcon(new ImageIcon("images/wall.png").getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT));
    private ImageIcon lava = new ImageIcon(new ImageIcon("images/lava.png").getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT));


    /**
     * 5x5 array to hold the look screen
     */
    private JLabel[][] lookWindow = new JLabel[5][5];

    private JFrame HumanClientGUIFrame;

    /**
     * constructor to set up the GUI
     */
    public HumanClientGUI()
    {
        setUpPlayGUI();
        HumanClientGUIFrame.setVisible(true);
    }


    /*
     * set up play GUI simply creates he frame and add all the necessary components to it
     */
    public void setUpPlayGUI(){
        HumanClientGUIFrame = new JFrame("Human Client");

        HumanClientGUIFrame.setSize(900,800);
        HumanClientGUIFrame.getContentPane().setBackground(Color.lightGray);
        HumanClientGUIFrame.setResizable(false);
        HumanClientGUIFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        HumanClientGUIFrame.setLayout(new GridBagLayout());

        /*
         * The panel that holds the inner panel to show the user their view
         */
        JPanel lookOuterPanel = new JPanel();
        lookOuterPanel.setLayout(new GridBagLayout());
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridheight = 2;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.BOTH;
        lookOuterPanel.setPreferredSize(new Dimension(600,600));
        lookOuterPanel.setBackground(Color.black);
        HumanClientGUIFrame.getContentPane().add(lookOuterPanel, gbc);

        /*
         * The panel that holds the controls allowing the use to move the player and pickup gold
         */
        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new GridBagLayout());
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.gridheight = 1;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.BOTH;
        controlPanel.setPreferredSize(new Dimension(300,600));
        controlPanel.setBackground(Color.red);
        HumanClientGUIFrame.getContentPane().add(controlPanel, gbc);

        /*
         * The panel that holds the components to chat, the message window, message field, the send button
         */
        JPanel chatPanel = new JPanel();
        chatPanel.setLayout(new GridBagLayout());
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridheight = 1;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.BOTH;
        chatPanel.setPreferredSize(new Dimension(600,200));
        chatPanel.setBackground(Color.green);
        HumanClientGUIFrame.getContentPane().add(chatPanel, gbc);

        /*
         * The panel that holds the IP address and port number of the server
         */
        JPanel IPPanel = new JPanel();
        IPPanel.setLayout(new GridBagLayout());
        gbc.gridx = 2;
        gbc.gridy = 2;
        gbc.gridheight = 1;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.BOTH;
        IPPanel.setPreferredSize(new Dimension(300,200));
        IPPanel.setBackground(Color.pink);
        HumanClientGUIFrame.getContentPane().add(IPPanel, gbc);

        /*
         * Makes sure that the frame is filled and puts all of the panels in the correct place
         */
        HumanClientGUIFrame.pack();


        /*
         * The panel that holds the JLabels to display the look function
         */
        JPanel lookInnerPanel = new JPanel();
        lookInnerPanel.setLayout(new GridLayout(5,5));
        lookInnerPanel.setPreferredSize(new Dimension(500,500));
        lookInnerPanel.setBackground(Color.white);
        lookOuterPanel.add(lookInnerPanel);


        /*
         * Insets allow spaces between components making the frame more aesthetically pleasing
         */
        gbcForPanel.insets = new Insets(20,10,20,10);
        gbcForChatPanel.insets = new Insets(5,20,5,20);
        gbcForIPPanel.insets = new Insets(5,10,10,10);

        /*
         * Adding the Labels to the array
         */
        for(int i=0;i<5;i++)
        {
            for(int j=0;j<5;j++)
            {
                lookWindow[i][j] = new JLabel();
            }
        }

        /*
         * Initially randomly filling the labels (will be correctly filed in another method)
         */
        for(int i=0;i<5;i++)
        {
            for(int j=0;j<5;j++)
            {
                Random rand = new Random();
                int u = rand.nextInt(8);
                switch (u){
                    case 0:
                        lookWindow[i][j].setIcon(goldimage);
                        break;
                    case 1:
                        lookWindow[i][j].setIcon(exit);
                        break;
                    case 2:
                        lookWindow[i][j].setIcon(floor);
                        break;
                    case 3:
                        lookWindow[i][j].setIcon(wall);
                        break;
                    case 4:
                        lookWindow[i][j].setIcon(lava);
                        break;
                    case 5:
                        lookWindow[i][j].setIcon(human);
                        break;
                    case 6:
                        lookWindow[i][j].setIcon(human2);
                        break;
                    case 7:
                        lookWindow[i][j].setIcon(bot);
                        break;
                }

                lookInnerPanel.add(lookWindow[i][j]);
            }

        }

        /*
         * Hello button functions as the "hello" command
         */
        JButton HelloButton = new JButton("Hello");
        gbcForPanel.gridx = 0;
        gbcForPanel.gridy = 0;
        gbcForPanel.gridheight = 1;
        gbcForPanel.gridwidth = 4;
        gbcForPanel.fill = GridBagConstraints.BOTH;
        controlPanel.add(HelloButton, gbcForPanel);

        HelloButton.addActionListener(new ActionListener() {

            public synchronized void actionPerformed(ActionEvent e) {
                out.println("hello");
                try {
                    CommandStatus.setText("Command Status: " + in.readLine());
                } catch (IOException e1) {
                    HumanClientGUIFrame.dispose();
                    System.exit(0);
                }
            }
        });

        /*
         * Pick up button functions as the "pickup" command
         */
        JButton PickupButton = new JButton("Pick Up");
        gbcForPanel.gridx = 0;
        gbcForPanel.gridy = 1;
        gbcForPanel.gridheight = 1;
        gbcForPanel.gridwidth = 4;
        gbcForPanel.fill = GridBagConstraints.BOTH;
        controlPanel.add(PickupButton, gbcForPanel);
        PickupButton.setForeground(Color.BLACK);
        PickupButton.setBackground(Color.YELLOW);
        PickupButton.addActionListener(new ActionListener() {

            public synchronized void actionPerformed(ActionEvent e) {
                out.println("pickup");
                try {
                    String pickUpLine = in.readLine();

                    if(pickUpLine.contains("SUCCESS"))
                    {
                        CommandStatus.setText("Command Status: " + pickUpLine);
                        gold++;
                    }
                    else
                    {
                        CommandStatus.setText("Command Status: " + pickUpLine);
                    }
                    goldCollected.setText("Gold Collected: " + gold);
                } catch (IOException e1) {
                    HumanClientGUIFrame.dispose();
                    System.exit(0);
                }

            }
        });

        /*
         * North button functions as the "move n" command
         */
        JButton NorthButton = new JButton("N");
        gbcForPanel.gridx = 1;
        gbcForPanel.gridy = 2;
        gbcForPanel.gridheight = 1;
        gbcForPanel.gridwidth = 1;
        gbcForPanel.fill = GridBagConstraints.BOTH;
        controlPanel.add(NorthButton, gbcForPanel);

        NorthButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                out.println("move n");
                try {
                    String commandReturn = in.readLine();
                    CommandStatus.setText("Command Status: " + commandReturn);
                    checkWin(commandReturn);
                } catch (IOException e1) {
                    HumanClientGUIFrame.dispose();
                    System.exit(0);
                }
            }
        });


        /*
         * South button functions as the "move s" command
         */
        JButton SouthButton = new JButton("S");
        gbcForPanel.gridx = 1;
        gbcForPanel.gridy = 4;
        gbcForPanel.fill = GridBagConstraints.BOTH;
        controlPanel.add(SouthButton, gbcForPanel);

        SouthButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                out.println("move s");
                try {
                    String commandReturn = in.readLine();
                    CommandStatus.setText("Command Status: " + commandReturn);
                    checkWin(commandReturn);
                } catch (IOException e1) {
                    HumanClientGUIFrame.dispose();
                    System.exit(0);
                }
            }
        });


        /*
         * East button functions as the "move e" command
         */
        JButton EastButton = new JButton("E");
        gbcForPanel.gridx = 3;
        gbcForPanel.gridy = 3;
        gbcForPanel.fill = GridBagConstraints.BOTH;
        controlPanel.add(EastButton, gbcForPanel);

        EastButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                out.println("move e");
                try {
                    String commandReturn = in.readLine();
                    CommandStatus.setText("Command Status: " + commandReturn);
                    checkWin(commandReturn);
                } catch (IOException e1) {
                    HumanClientGUIFrame.dispose();
                    System.exit(0);
                }
            }
        });


        /*
         * West button functions as the "move w" command
         */
        JButton WestButton = new JButton("W");
        gbcForPanel.gridx = 0;
        gbcForPanel.gridy = 3;
        gbcForPanel.fill = GridBagConstraints.BOTH;
        controlPanel.add(WestButton, gbcForPanel);

        WestButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                out.println("move w");
                try {
                    String commandReturn = in.readLine();
                    CommandStatus.setText("Command Status: " + commandReturn);
                    checkWin(commandReturn);
                } catch (IOException e1) {
                    if(!winner)
                    {
                        JOptionPane.showMessageDialog(HumanClientGUIFrame,"Sorry, you lost");
                    }
                    HumanClientGUIFrame.dispose();
                    System.exit(0);
                }
            }
        });


        /*
         * Quit button functions as the "quit" command
         */
        JButton QuitButton = new JButton("Quit");
        gbcForPanel.gridx = 0;
        gbcForPanel.gridy = 5;
        gbcForPanel.gridheight = 1;
        gbcForPanel.gridwidth = 4;
        gbcForPanel.fill = GridBagConstraints.BOTH;
        QuitButton.setForeground(Color.RED);
        QuitButton.setBackground(Color.BLACK);
        controlPanel.add(QuitButton, gbcForPanel);

        QuitButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                out.println("quit");
                HumanClientGUIFrame.dispose();
                System.exit(0);
            }
        });

        /*
         * Displays how much gold the user has
         */
        goldCollected = new JLabel("Gold Collected: " + gold);
        gbcForPanel.gridx = 0;
        gbcForPanel.gridy = 7;
        gbcForPanel.gridheight = 1;
        gbcForPanel.gridwidth = 5;
        gbcForPanel.fill = GridBagConstraints.BOTH;
        goldCollected.setFont (goldCollected.getFont ().deriveFont (24.0f));
        controlPanel.add(goldCollected, gbcForPanel);

        /*
         * Returns the response from their input
         */
        CommandStatus = new JLabel("Command Status: ");
        gbcForPanel.gridx = 0;
        gbcForPanel.gridy = 8;
        gbcForPanel.gridheight = 1;
        gbcForPanel.gridwidth = 5;
        gbcForPanel.fill = GridBagConstraints.BOTH;
        goldCollected.setFont (CommandStatus.getFont ().deriveFont (20.0f));
        controlPanel.add(CommandStatus, gbcForPanel);

        /*
         * chatWindow displays the history of the chat
         */
        JTextArea chatWindow = new JTextArea(7, 40);
        chatWindow.setEditable(false);
        //JScrollPane scrollPane = new JScrollPane(chatWindow);
        gbcForChatPanel.gridx = 0;
        gbcForChatPanel.gridy = 0;
        gbcForChatPanel.gridheight = 3;
        gbcForChatPanel.gridwidth = 1;
        gbcForChatPanel.fill = GridBagConstraints.BOTH;
        chatPanel.add(chatWindow,gbcForChatPanel);

        /*
         * chatField allows the user to type their next message
         */
        JTextField chatField = new JTextField(40);
        chatField.setEditable(true);
        gbcForChatPanel.gridx = 0;
        gbcForChatPanel.gridy = 4;
        gbcForChatPanel.gridheight = 1;
        gbcForChatPanel.gridwidth = 1;
        gbcForChatPanel.fill = GridBagConstraints.BOTH;
        chatPanel.add(chatField,gbcForChatPanel);

        /*
         * the send button sends the message to the other players
         */
        JButton send = new JButton("Send");
        gbcForChatPanel.gridx = 1;
        gbcForChatPanel.gridy = 4;
        gbcForChatPanel.gridheight = 1;
        gbcForChatPanel.gridwidth = 1;
        gbcForChatPanel.fill = GridBagConstraints.BOTH;
        chatPanel.add(send, gbcForChatPanel);

        /*
         * clear removes the chat history from the screen // might replace with drop down of players for advanced chat
         */
        JButton clear = new JButton("Clear");
        gbcForChatPanel.gridx = 1;
        gbcForChatPanel.gridy = 0;
        gbcForChatPanel.gridheight = 3;
        gbcForChatPanel.gridwidth = 1;
        gbcForChatPanel.fill = GridBagConstraints.BOTH;
        chatPanel.add(clear, gbcForChatPanel);

        JLabel ipAddress = new JLabel("IP Address:");
        gbcForIPPanel.gridx = 0;
        gbcForIPPanel.gridy = 0;
        gbcForIPPanel.gridheight = 1;
        gbcForIPPanel.gridwidth = 1;
        gbcForIPPanel.fill = GridBagConstraints.BOTH;
        goldCollected.setFont (goldCollected.getFont ().deriveFont (24.0f));
        IPPanel.add(ipAddress, gbcForIPPanel);

        /*
         * IPField displays the IP address of the server
         */
        IPField = new JTextField(10);
        IPField.setEditable(true);
        gbcForIPPanel.gridx = 1;
        gbcForIPPanel.gridy = 0;
        gbcForIPPanel.gridheight = 1;
        gbcForIPPanel.gridwidth = 1;
        gbcForIPPanel.fill = GridBagConstraints.BOTH;
        IPPanel.add(IPField,gbcForIPPanel);

        JLabel portNumber = new JLabel("Port Number:");
        gbcForIPPanel.gridx = 0;
        gbcForIPPanel.gridy = 1;
        gbcForIPPanel.gridheight = 1;
        gbcForIPPanel.gridwidth = 1;
        gbcForIPPanel.fill = GridBagConstraints.BOTH;
        goldCollected.setFont (goldCollected.getFont ().deriveFont (24.0f));
        IPPanel.add(portNumber, gbcForIPPanel);

        /*
         * Port field displays the port number of the server
         */
        PortField = new JTextField(10);
        PortField.setEditable(true);
        gbcForIPPanel.gridx = 1;
        gbcForIPPanel.gridy = 1;
        gbcForIPPanel.gridheight = 1;
        gbcForIPPanel.gridwidth = 1;
        gbcForIPPanel.fill = GridBagConstraints.BOTH;
        IPPanel.add(PortField,gbcForIPPanel);

        /*
         * change port will switch the port number and connect to the server with the details currently in the fields
         */
        JButton changePort = new JButton("Change");
        gbcForIPPanel.gridx = 0;
        gbcForIPPanel.gridy = 2;
        gbcForIPPanel.gridheight = 1;
        gbcForIPPanel.gridwidth = 3;
        gbcForIPPanel.fill = GridBagConstraints.BOTH;
        IPPanel.add(changePort, gbcForIPPanel);
    }


    private void checkWin(String fromServer)
    {
        if(fromServer.equals("Congratulations!!!"))
        {
            JOptionPane.showMessageDialog(HumanClientGUIFrame,"You beat the Dungeon of Doom!");
            winner = true;

        }
    }
    /**
     * The object MapLook is a 2D array to hold the 5x5 grid when look is
     * called.
     */
    static Object[][] MapLook = new Object[5][5];

    /**
     * The function print-look calls the look function to get the surroundings
     * of the player. The string is then cut up into a string array of single
     * characters. One by one the characters are fed into the char array and for
     * each 5th character a new line in the array is made.
     */
    public void printLook(String lookstring) {
        String[] looklines;

        looklines = lookstring.split("");

        int i, j = 0; // i = line count, j = char count

        for (i = 0; i < lookstring.length(); i++) {
            if(i==12)
            {
                lookWindow[2][2].setIcon(human);
            }
            else
            {
                if (i % 5 == 0 && i != 0) {
                    j++;
                    putInImage(looklines[i], i % 5, j);
                } else {
                    if (j == 0) {
                        putInImage(looklines[i], i%5, j);
                    } else {
                        putInImage(looklines[i], i % 5, j);
                    }
                }
            }

        }
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


    /**
     * The human client uses the host name and the port number to connect to the host
     * Then the client socket is made so that it can send and receive data to and from the server
     * Then an introduction on how to use the chat comes up
     * Using a buffered reader to get input, it is then sent to the thread client class using the print writer,
     * and the processed command is returned for output
     */
    public static void main(String[] args) throws IOException {

        if (args.length != 2) {
            System.err.println(
                    "Usage: java HumanClient <host name> <port number>");
            System.exit(1);
        }

        String hostName = args[0];
        int portNumber = Integer.parseInt(args[1]);

        try {
            clientSocket = new Socket(hostName, portNumber);
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            out.println("human");

            String fromUser;

            JFrame nameWindow = new JFrame("Username Input");
            String name = JOptionPane.showInputDialog("Choose a username:");

            out.println(name);

            JOptionPane.showMessageDialog(nameWindow,"Welcome to dungeons of doom " + name + "!\n"
            + "You already know most of the commands, but with the new chat system there's a few more.\n"
            +"To chat publicly, type 'SHOUT message'\n"
            +"To chat privately, type 'SHOUT $name message'\n"
            +"To see the chat look in log.txt\n\n"
            +"GOOD LUCK!");

            // ChatThread to output anything to the terminal that is waiting to be displayed
            //new ChatThread(in).start();
            HumanClientGUI human = new HumanClientGUI();
            IPField.setText(hostName);
            PortField.setText(Integer.toString(portNumber));

            // initialising look thread so its always updated by their surroundings
            new LookThread(in, out, human).start();


        } catch (UnknownHostException e) {
            System.err.println("Don't know about host " + hostName);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to " + hostName);
            System.exit(1);
        }

    }

}
