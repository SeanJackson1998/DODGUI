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

    GridBagConstraints gbc = new GridBagConstraints();
    GridBagConstraints gbcForPanel = new GridBagConstraints();
    GridBagConstraints gbcForChatPanel = new GridBagConstraints();
    GridBagConstraints gbcForIPPanel = new GridBagConstraints();

    private ImageIcon floor = new ImageIcon(new ImageIcon("images/floor.png").getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT));
    private ImageIcon gold = new ImageIcon(new ImageIcon("images/gold.png").getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT));
    private ImageIcon human = new ImageIcon(new ImageIcon("images/human.png").getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT));
    private ImageIcon human2 = new ImageIcon(new ImageIcon("images/human2.png").getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT));
    private ImageIcon bot = new ImageIcon(new ImageIcon("images/bot.png").getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT));
    private ImageIcon exit = new ImageIcon(new ImageIcon("images/exit.png").getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT));
    private ImageIcon wall = new ImageIcon(new ImageIcon("images/wall.png").getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT));
    private ImageIcon lava = new ImageIcon(new ImageIcon("images/lava.png").getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT));


    private JLabel[][] lookWindow = new JLabel[5][5];

    private JFrame HumanClientGUIFrame;

    public HumanClientGUI()
    {
        setUpPlayGUI();
        HumanClientGUIFrame.setVisible(true);
    }

    public void setUpPlayGUI(){
        HumanClientGUIFrame = new JFrame("Human Client");

        HumanClientGUIFrame.setSize(900,800);
        HumanClientGUIFrame.getContentPane().setBackground(Color.lightGray);
        HumanClientGUIFrame.setResizable(false);
        HumanClientGUIFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        HumanClientGUIFrame.setLayout(new GridBagLayout());

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

        HumanClientGUIFrame.pack();


        JPanel lookInnerPanel = new JPanel();
        lookInnerPanel.setLayout(new GridLayout(5,5));
        lookInnerPanel.setPreferredSize(new Dimension(500,500));
        lookInnerPanel.setBackground(Color.white);
        lookOuterPanel.add(lookInnerPanel);



        gbcForPanel.insets = new Insets(20,10,20,10);
        gbcForChatPanel.insets = new Insets(5,20,5,20);
        gbcForIPPanel.insets = new Insets(5,10,10,10);

        // ##############################################################
        // look inner panel components

        for(int i=0;i<5;i++)
        {
            for(int j=0;j<5;j++)
            {
                lookWindow[i][j] = new JLabel();
            }
        }

        for(int i=0;i<5;i++)
        {
            for(int j=0;j<5;j++)
            {
                Random rand = new Random();
                int u = rand.nextInt(8);
                switch (u){
                    case 0:
                        lookWindow[i][j].setIcon(gold);
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
        // ###############################################################
        // controls panel components
        JButton HelloButton = new JButton("Hello");
        gbcForPanel.gridx = 0;
        gbcForPanel.gridy = 0;
        gbcForPanel.gridheight = 1;
        gbcForPanel.gridwidth = 4;
        gbcForPanel.fill = GridBagConstraints.BOTH;
        controlPanel.add(HelloButton, gbcForPanel);

        HelloButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                JOptionPane.showMessageDialog(HumanClientGUIFrame, "hi");

                out.println("hello");
                try {
                    JOptionPane.showMessageDialog(HumanClientGUIFrame, in.readLine());
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });

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

            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(HumanClientGUIFrame, "GOLD!!");
            }
        });

        JButton NorthButton = new JButton("N");
        gbcForPanel.gridx = 1;
        gbcForPanel.gridy = 2;
        gbcForPanel.gridheight = 1;
        gbcForPanel.gridwidth = 1;
        gbcForPanel.fill = GridBagConstraints.BOTH;
        controlPanel.add(NorthButton, gbcForPanel);

        JButton SouthButton = new JButton("S");
        gbcForPanel.gridx = 1;
        gbcForPanel.gridy = 4;
        gbcForPanel.fill = GridBagConstraints.BOTH;
        controlPanel.add(SouthButton, gbcForPanel);

        JButton EastButton = new JButton("E");
        gbcForPanel.gridx = 3;
        gbcForPanel.gridy = 3;
        gbcForPanel.fill = GridBagConstraints.BOTH;
        controlPanel.add(EastButton, gbcForPanel);

        JButton WestButton = new JButton("W");
        gbcForPanel.gridx = 0;
        gbcForPanel.gridy = 3;
        gbcForPanel.fill = GridBagConstraints.BOTH;
        controlPanel.add(WestButton, gbcForPanel);

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
                JOptionPane.showMessageDialog(HumanClientGUIFrame, "CYA");
                HumanClientGUIFrame.dispose();
            }
        });


      int gold = 0;
        JLabel goldCollected = new JLabel("Gold collected: " + gold);
        gbcForPanel.gridx = 0;
        gbcForPanel.gridy = 7;
        gbcForPanel.gridheight = 1;
        gbcForPanel.gridwidth = 5;
        gbcForPanel.fill = GridBagConstraints.BOTH;
        goldCollected.setFont (goldCollected.getFont ().deriveFont (24.0f));
        controlPanel.add(goldCollected, gbcForPanel);


        // #########################################################################
        // chat panel components
        JTextArea chatWindow = new JTextArea(7, 40);
        chatWindow.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(chatWindow);
        gbcForChatPanel.gridx = 0;
        gbcForChatPanel.gridy = 0;
        gbcForChatPanel.gridheight = 3;
        gbcForChatPanel.gridwidth = 1;
        gbcForChatPanel.fill = GridBagConstraints.BOTH;
        chatPanel.add(chatWindow,gbcForChatPanel);

        JTextField chatField = new JTextField(40);
        chatField.setEditable(true);
        gbcForChatPanel.gridx = 0;
        gbcForChatPanel.gridy = 4;
        gbcForChatPanel.gridheight = 1;
        gbcForChatPanel.gridwidth = 1;
        gbcForChatPanel.fill = GridBagConstraints.BOTH;
        chatPanel.add(chatField,gbcForChatPanel);

        JButton send = new JButton("Send");
        gbcForChatPanel.gridx = 1;
        gbcForChatPanel.gridy = 4;
        gbcForChatPanel.gridheight = 1;
        gbcForChatPanel.gridwidth = 1;
        gbcForChatPanel.fill = GridBagConstraints.BOTH;
        chatPanel.add(send, gbcForChatPanel);

        JButton clear = new JButton("Clear");
        gbcForChatPanel.gridx = 1;
        gbcForChatPanel.gridy = 0;
        gbcForChatPanel.gridheight = 3;
        gbcForChatPanel.gridwidth = 1;
        gbcForChatPanel.fill = GridBagConstraints.BOTH;
        chatPanel.add(clear, gbcForChatPanel);

        //##############################################################################
        // IP panel components

        JLabel ipAddress = new JLabel("IP Address:");
        gbcForIPPanel.gridx = 0;
        gbcForIPPanel.gridy = 0;
        gbcForIPPanel.gridheight = 1;
        gbcForIPPanel.gridwidth = 1;
        gbcForIPPanel.fill = GridBagConstraints.BOTH;
        goldCollected.setFont (goldCollected.getFont ().deriveFont (24.0f));
        IPPanel.add(ipAddress, gbcForIPPanel);


        JTextField IPField = new JTextField(10);
        chatField.setEditable(true);
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

        JTextField PortField = new JTextField(10);
        PortField.setEditable(true);
        gbcForIPPanel.gridx = 1;
        gbcForIPPanel.gridy = 1;
        gbcForIPPanel.gridheight = 1;
        gbcForIPPanel.gridwidth = 1;
        gbcForIPPanel.fill = GridBagConstraints.BOTH;
        IPPanel.add(PortField,gbcForIPPanel);

        JButton changePort = new JButton("Change");
        gbcForIPPanel.gridx = 0;
        gbcForIPPanel.gridy = 2;
        gbcForIPPanel.gridheight = 1;
        gbcForIPPanel.gridwidth = 3;
        gbcForIPPanel.fill = GridBagConstraints.BOTH;
        IPPanel.add(changePort, gbcForIPPanel);

        // make the labels for the ip and port number
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
    public void printLook() {
        GameLogic Game = new GameLogic();
        String lookstring = null;// = Game.look();
        String[] looklines;

        looklines = lookstring.split("(?!^)");

        int i, j = 0; // i = line count, j = char count

        for (i = 0; i < lookstring.length(); i++) {
            if(i==12)
            {
                lookWindow[i][j].setIcon(human);
            }
            else
            {
                if (i % 5 == 0 && i != 0) {
                    j++;
                    putInImage(looklines[i], i % j, j);
                } else {
                    if (j == 0) {
                        putInImage(looklines[i], i, j);
                    } else {
                        putInImage(looklines[i], i % j, j);
                    }
                }
            }

        }
    }

    private void putInImage(String tile, int i, int j) {

        switch (tile){
            case "P":
                lookWindow[i][j].setIcon(human2);
                break;
            case "B":
                lookWindow[i][j].setIcon(bot);
                break;
            case ".":
                lookWindow[i][j].setIcon(floor);
                break;
            case "#":
                lookWindow[i][j].setIcon(wall);
                break;
            case "X":
                lookWindow[i][j].setIcon(lava);
                break;
            case "G":
                lookWindow[i][j].setIcon(gold);
                break;
            case "E":
                lookWindow[i][j].setIcon(exit);
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

            // OutThread to output anything to the terminal that is waiting to be displayed
            //new OutThread(in).start();


            HumanClientGUI human = new HumanClientGUI();

        } catch (UnknownHostException e) {
            System.err.println("Don't know about host " + hostName);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to " + hostName);
            System.exit(1);
        }

    }

}
