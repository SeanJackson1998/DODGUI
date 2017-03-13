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

        HumanClientGUIFrame.setSize(500,500);
        HumanClientGUIFrame.getContentPane().setBackground(Color.lightGray);
        HumanClientGUIFrame.setResizable(false);
        HumanClientGUIFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        HumanClientGUIFrame.setLayout(new GridBagLayout());

        
        gbc.insets = new Insets(5,5,5,5);


        String[] columns = new String[] { // The column heads are blank as this
                // table doesn't need any
                "", "", "", "", "" };
        JTable table = new JTable(MapLook, columns); // the table contains the array
        gbc.gridx = 0;
        gbc.gridy = 0;

        gbc.gridheight = 5;
        gbc.gridwidth = 5;
        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        table.setForeground(Color.DARK_GRAY);
        // MapLook with the column
        // headings 'columns'
        table.setFont(new Font("Dialog", Font.PLAIN, 26));
        table.setRowHeight(50);
        table.getColumnModel().getColumn(0).setMinWidth(50);
        table.getColumnModel().getColumn(1).setMinWidth(50);
        table.getColumnModel().getColumn(2).setMinWidth(50);
        table.getColumnModel().getColumn(3).setMinWidth(50);
        table.getColumnModel().getColumn(4).setMinWidth(50);

        HumanClientGUIFrame.getContentPane().add(table);
        /**
         * The table is declared above and then the look function populates it
         * with the map data. The the repaint command updates the table visibly
         * for the user on the frame.
         */
        //printLook();
        //table.repaint();




        JButton HelloButton = new JButton("Hello");
        gbc.gridx = 6;
        gbc.gridy = 0;

        gbc.gridheight = 1;
        gbc.gridwidth = 3;
        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        //HelloButton.setBounds(50,50,50,50);
        HumanClientGUIFrame.add(HelloButton, gbc);

        HelloButton.setForeground(Color.BLACK);
        HelloButton.setBackground(Color.YELLOW);
        HelloButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(HumanClientGUIFrame, "HI!");
            }
        });


        JButton PickupButton = new JButton("Pick Up");
        gbc.gridx = 9;
        gbc.gridy = 0;

        gbc.gridheight = 1;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        HumanClientGUIFrame.add(PickupButton, gbc);

        PickupButton.setForeground(Color.ORANGE);
        PickupButton.setBackground(Color.YELLOW);
        PickupButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(HumanClientGUIFrame, "GOLD!!");
            }
        });

        JButton NorthButton = new JButton("N");
        gbc.gridx = 6;
        gbc.gridy = 1;

        gbc.gridheight = 1;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        //HelloButton.setBounds(50,50,50,50);
        HumanClientGUIFrame.add(NorthButton, gbc);

        JButton SouthButton = new JButton("S");
        gbc.gridx = 6;
        gbc.gridy = 3;

        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        //HelloButton.setBounds(50,50,50,50);
        HumanClientGUIFrame.add(SouthButton, gbc);

        JButton EastButton = new JButton("E");
        gbc.gridx = 7;
        gbc.gridy = 2;

        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        //HelloButton.setBounds(50,50,50,50);
        HumanClientGUIFrame.add(EastButton, gbc);

        JButton WestButton = new JButton("W");
        gbc.gridx = 5;
        gbc.gridy = 2;

        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        //HelloButton.setBounds(50,50,50,50);
        HumanClientGUIFrame.add(WestButton, gbc);



        JButton QuitButton = new JButton("Quit");
        gbc.gridx = 8;
        gbc.gridy = 3;
        gbc.gridheight = 2;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        QuitButton.setForeground(Color.RED);
        QuitButton.setBackground(Color.BLACK);

        HumanClientGUIFrame.add(QuitButton, gbc);

        QuitButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(HumanClientGUIFrame, "CYA");
                HumanClientGUIFrame.dispose();
            }
        });
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
