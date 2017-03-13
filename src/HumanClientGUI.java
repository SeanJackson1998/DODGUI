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

public class HumanClientGUI{

    GridBagConstraints gbc = new GridBagConstraints();
    GridBagConstraints gbcForPanel = new GridBagConstraints();
    GridBagConstraints gbcForChatPanel = new GridBagConstraints();

    private JFrame HumanClientGUIFrame;

    public HumanClientGUI()
    {
        introGUIS();
        setUpPlayGUI();
        HumanClientGUIFrame.setVisible(true);
    }

    private void introGUIS() {

    }

    public void setUpPlayGUI(){
        HumanClientGUIFrame = new JFrame("Human Client");
        HumanClientGUIFrame.setLayout(new FlowLayout());

        HumanClientGUIFrame.setSize(900,800);
        HumanClientGUIFrame.getContentPane().setBackground(Color.lightGray);
        HumanClientGUIFrame.setResizable(false);
        HumanClientGUIFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        HumanClientGUIFrame.setLayout(new GridBagLayout());

        JPanel lookPanel = new JPanel();
        lookPanel.setLayout(new GridBagLayout());
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridheight = 2;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        lookPanel.setPreferredSize(new Dimension(600,600));
        lookPanel.setBackground(Color.black);
        HumanClientGUIFrame.getContentPane().add(lookPanel, gbc);

        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new GridBagLayout());
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.gridheight = 1;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        controlPanel.setPreferredSize(new Dimension(300,600));
        controlPanel.setBackground(Color.red);
        HumanClientGUIFrame.getContentPane().add(controlPanel, gbc);

        JPanel chatPanel = new JPanel();
        chatPanel.setLayout(new GridBagLayout());
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridheight = 1;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        chatPanel.setPreferredSize(new Dimension(600,200));
        chatPanel.setBackground(Color.green);
        HumanClientGUIFrame.getContentPane().add(chatPanel, gbc);

        JPanel IPPanel = new JPanel();
        IPPanel.setLayout(new GridBagLayout());
        gbc.gridx = 2;
        gbc.gridy = 2;
        gbc.gridheight = 1;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        IPPanel.setPreferredSize(new Dimension(300,200));
        IPPanel.setBackground(Color.blue);
        HumanClientGUIFrame.getContentPane().add(IPPanel, gbc);

        HumanClientGUIFrame.pack();

        String[] columns = new String[] { // The column heads are blank as this
                // table doesn't need any
                "", "", "", "", "" };
        JTable table = new JTable(MapLook, columns); // the table contains the array
        gbcForPanel.gridx = 0;
        gbcForPanel.gridy = 0;

        gbcForPanel.gridheight = 5;
        gbcForPanel.gridwidth = 5;
        gbcForPanel.fill = GridBagConstraints.VERTICAL;
        gbcForPanel.fill = GridBagConstraints.HORIZONTAL;
        table.setForeground(Color.DARK_GRAY);

        // MapLook with the column
        // headings 'columns'
        table.setFont(new Font("Dialog", Font.PLAIN, 26));
        table.setRowHeight(100);
        table.getColumnModel().getColumn(0).setMinWidth(100);
        table.getColumnModel().getColumn(1).setMinWidth(100);
        table.getColumnModel().getColumn(2).setMinWidth(100);
        table.getColumnModel().getColumn(3).setMinWidth(100);
        table.getColumnModel().getColumn(4).setMinWidth(100);

        lookPanel.add(table);
        /**
         * The table is declared above and then the look function populates it
         * with the map data. The the repaint command updates the table visibly
         * for the user on the frame.
         */
        //printLook();
        //table.repaint();




        gbcForPanel.insets = new Insets(20,10,20,10);
        gbcForChatPanel.insets = new Insets(5,20,5,20);


        JButton HelloButton = new JButton("Hello");
        gbcForPanel.gridx = 0;
        gbcForPanel.gridy = 0;
        gbcForPanel.gridheight = 1;
        gbcForPanel.gridwidth = 4;
        gbcForPanel.fill = GridBagConstraints.VERTICAL;
        gbcForPanel.fill = GridBagConstraints.HORIZONTAL;
        controlPanel.add(HelloButton, gbcForPanel);

        HelloButton.setForeground(Color.BLACK);
        HelloButton.setBackground(Color.YELLOW);
        HelloButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(HumanClientGUIFrame, "HI!");
            }
        });


        JButton PickupButton = new JButton("Pick Up");
        gbcForPanel.gridx = 0;
        gbcForPanel.gridy = 1;
        gbcForPanel.gridheight = 1;
        gbcForPanel.gridwidth = 4;
        gbcForPanel.fill = GridBagConstraints.VERTICAL;
        gbcForPanel.fill = GridBagConstraints.HORIZONTAL;
        controlPanel.add(PickupButton, gbcForPanel);

        PickupButton.setForeground(Color.ORANGE);
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
        gbcForPanel.fill = GridBagConstraints.VERTICAL;
        gbcForPanel.fill = GridBagConstraints.HORIZONTAL;
        controlPanel.add(NorthButton, gbcForPanel);

        JButton SouthButton = new JButton("S");
        gbcForPanel.gridx = 1;
        gbcForPanel.gridy = 4;
        gbcForPanel.fill = GridBagConstraints.VERTICAL;
        gbcForPanel.fill = GridBagConstraints.HORIZONTAL;
        controlPanel.add(SouthButton, gbcForPanel);

        JButton EastButton = new JButton("E");
        gbcForPanel.gridx = 3;
        gbcForPanel.gridy = 3;
        gbcForPanel.fill = GridBagConstraints.VERTICAL;
        gbcForPanel.fill = GridBagConstraints.HORIZONTAL;
        controlPanel.add(EastButton, gbcForPanel);

        JButton WestButton = new JButton("W");
        gbcForPanel.gridx = 0;
        gbcForPanel.gridy = 3;
        gbcForPanel.fill = GridBagConstraints.VERTICAL;
        gbcForPanel.fill = GridBagConstraints.HORIZONTAL;
        controlPanel.add(WestButton, gbcForPanel);

        JButton QuitButton = new JButton("Quit");
        gbcForPanel.gridx = 0;
        gbcForPanel.gridy = 5;
        gbcForPanel.gridheight = 1;
        gbcForPanel.gridwidth = 4;
        gbcForPanel.fill = GridBagConstraints.VERTICAL;
        gbcForPanel.fill = GridBagConstraints.HORIZONTAL;
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
        gbcForPanel.fill = GridBagConstraints.VERTICAL;
        gbcForPanel.fill = GridBagConstraints.HORIZONTAL;
        goldCollected.setFont (goldCollected.getFont ().deriveFont (24.0f));
        controlPanel.add(goldCollected, gbcForPanel);



        JTextArea chatWindow = new JTextArea(7, 40);
        chatWindow.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(chatWindow);
        gbcForChatPanel.gridx = 0;
        gbcForChatPanel.gridy = 0;
        gbcForChatPanel.gridheight = 3;
        gbcForChatPanel.gridwidth = 1;
        gbcForChatPanel.fill = GridBagConstraints.VERTICAL;
        gbcForChatPanel.fill = GridBagConstraints.HORIZONTAL;
        chatPanel.add(chatWindow,gbcForChatPanel);

        JTextField chatField = new JTextField(40);
        chatField.setEditable(true);
        gbcForChatPanel.gridx = 0;
        gbcForChatPanel.gridy = 4;
        gbcForChatPanel.gridheight = 1;
        gbcForChatPanel.gridwidth = 1;
        gbcForChatPanel.fill = GridBagConstraints.VERTICAL;
        gbcForChatPanel.fill = GridBagConstraints.HORIZONTAL;
        chatPanel.add(chatField,gbcForChatPanel);

        JButton send = new JButton("Send");
        gbcForChatPanel.gridx = 1;
        gbcForChatPanel.gridy = 4;
        gbcForChatPanel.gridheight = 1;
        gbcForChatPanel.gridwidth = 1;
        gbcForChatPanel.fill = GridBagConstraints.VERTICAL;
        gbcForChatPanel.fill = GridBagConstraints.HORIZONTAL;
        chatPanel.add(send, gbcForChatPanel);

        JButton clear = new JButton("Clear");
        gbcForChatPanel.gridx = 1;
        gbcForChatPanel.gridy = 0;
        gbcForChatPanel.gridheight = 3;
        gbcForChatPanel.gridwidth = 1;
        gbcForChatPanel.fill = GridBagConstraints.VERTICAL;
        gbcForChatPanel.fill = GridBagConstraints.HORIZONTAL;
        chatPanel.add(clear, gbcForChatPanel);

        // make the text window for chat
        // make the text filed for the chat
        // make the send button for the chat
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
//        GameLogic Game = new GameLogic();
//
//        String lookstring = Game.look();
//        String[] looklines;
//
//        looklines = lookstring.split("(?!^)");
//
//        int i, j = 0; // i = line count, j = char count
//
//        for (i = 0; i < lookstring.length(); i++) {
//            if (i % 5 == 0 && i != 0) {
//                j++;
//                MapLook[j][i % 5] = looklines[i];
//            } else
//                MapLook[j][i % 5] = looklines[i];
//        }

    }


    /**
     * The human client uses the host name and the port number to connect to the host
     * Then the client socket is made so that it can send and receive data to and from the server
     * Then an introduction on how to use the chat comes up
     * Using a buffered reader to get input, it is then sent to the thread client class using the print writer,
     * and the processed command is returned for output
     */
    public static void main(String[] args) throws IOException {

        HumanClientGUI human = new HumanClientGUI();

        /*if (args.length != 2) {
            System.err.println(
                    "Usage: java HumanClient <host name> <port number>");
            System.exit(1);
        }

        String hostName = args[0];
        int portNumber = Integer.parseInt(args[1]);

        try (
                Socket clientSocket = new Socket(hostName, portNumber);
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        ) {
            BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));

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
            new OutThread(in).start();


            // while(quitbool!=quit) output to the server, if quit is clicked, change this boolean
            while (!(fromUser = stdIn.readLine()).equalsIgnoreCase("quit")) {
                System.out.println(name + ": " + fromUser);
                out.println(fromUser);
            }

            System.out.println(name + ": " + fromUser);
            out.println(fromUser);

        } catch (UnknownHostException e) {
            System.err.println("Don't know about host " + hostName);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to " + hostName);
            System.exit(1);
        }

        */
    }

}
