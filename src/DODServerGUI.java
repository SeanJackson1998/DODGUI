import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by skatt on 16/03/2017.
 */
public class DODServerGUI {

    private JLabel[][] lookWindow = new JLabel[5][5];

    private JFrame DODServerGUIFrame;

    public DODServerGUI()
    {
        setUpServerGUI();
        DODServerGUIFrame.setVisible(true);
    }


    GridBagConstraints gbc = new GridBagConstraints();
    GridBagConstraints gbcForPanel = new GridBagConstraints();

    public void setUpServerGUI() {
        DODServerGUIFrame = new JFrame("DOD Server");

        DODServerGUIFrame.setSize(1200, 800);
        DODServerGUIFrame.getContentPane().setBackground(Color.lightGray);
        DODServerGUIFrame.setResizable(false);
        DODServerGUIFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        DODServerGUIFrame.setLayout(new GridBagLayout());

        JPanel lookOuterPanel = new JPanel();
        lookOuterPanel.setLayout(new GridBagLayout());
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridheight = 3;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        lookOuterPanel.setPreferredSize(new Dimension(1200, 600));
        lookOuterPanel.setBackground(Color.black);
        DODServerGUIFrame.getContentPane().add(lookOuterPanel, gbc);

        


        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new GridBagLayout());
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridheight = 1;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        controlPanel.setPreferredSize(new Dimension(1200, 200));
        controlPanel.setBackground(Color.pink);
        DODServerGUIFrame.getContentPane().add(controlPanel, gbc);

        DODServerGUIFrame.pack();

        gbcForPanel.insets = new Insets(10,10,10,10);

        JButton hideGodView = new JButton("Hide");
        gbcForPanel.gridx = 0;
        gbcForPanel.gridy = 0;
        gbcForPanel.gridheight = 2;
        gbcForPanel.gridwidth = 3;
        gbcForPanel.fill = GridBagConstraints.VERTICAL;
        gbcForPanel.fill = GridBagConstraints.HORIZONTAL;
        controlPanel.add(hideGodView, gbcForPanel);

        JLabel portNumber = new JLabel("Port Number:");
        gbcForPanel.gridx = 3;
        gbcForPanel.gridy = 0;
        gbcForPanel.gridheight = 1;
        gbcForPanel.gridwidth = 1;
        gbcForPanel.fill = GridBagConstraints.VERTICAL;
        gbcForPanel.fill = GridBagConstraints.HORIZONTAL;
        controlPanel.add(portNumber, gbcForPanel);

        JTextField PortField = new JTextField(10);
        PortField.setEditable(true);
        gbcForPanel.gridx = 4;
        gbcForPanel.gridy = 0;
        gbcForPanel.gridheight = 1;
        gbcForPanel.gridwidth = 1;
        gbcForPanel.fill = GridBagConstraints.VERTICAL;
        gbcForPanel.fill = GridBagConstraints.HORIZONTAL;
        controlPanel.add(PortField,gbcForPanel);

        JLabel IPAddress = new JLabel("IP Address:");
        gbcForPanel.gridx = 3;
        gbcForPanel.gridy = 1;
        gbcForPanel.gridheight = 1;
        gbcForPanel.gridwidth = 1;
        gbcForPanel.fill = GridBagConstraints.VERTICAL;
        gbcForPanel.fill = GridBagConstraints.HORIZONTAL;
        controlPanel.add(IPAddress, gbcForPanel);

        JTextField AddressField = new JTextField(10);
        AddressField.setEditable(true);
        gbcForPanel.gridx = 4;
        gbcForPanel.gridy = 1;
        gbcForPanel.gridheight = 1;
        gbcForPanel.gridwidth = 1;
        gbcForPanel.fill = GridBagConstraints.VERTICAL;
        gbcForPanel.fill = GridBagConstraints.HORIZONTAL;
        controlPanel.add(AddressField,gbcForPanel);

        JButton changePort = new JButton("Change");
        gbcForPanel.gridx = 5;
        gbcForPanel.gridy = 0;
        gbcForPanel.gridheight = 2;
        gbcForPanel.gridwidth = 1;
        gbcForPanel.fill = GridBagConstraints.VERTICAL;
        gbcForPanel.fill = GridBagConstraints.HORIZONTAL;
        controlPanel.add(changePort, gbcForPanel);

    }


    public static void main(String[] args) throws IOException {

        DODServerGUI dodServer = new DODServerGUI();
        /*if (args.length != 1) {
            System.err.println("Usage: java DODMultiServer <port number>");
            System.exit(1);
        }
        int portNumber = Integer.parseInt(args[0]);
        int playerID = 0;
        GameLogic game = new GameLogic();
        Map map = new Map();
        map.readMap();
        User user;
        char type;
        String name = "";

        // setting up the chat file for the next game
        game.chatLogger.chatLog("---------------------------------------------------------------------------------");
        game.chatLogger.chatLog("Game Started: " + getTime());

        try (ServerSocket serverSocket = new ServerSocket(portNumber)) {
            while (true) {
                Socket socket1 = serverSocket.accept();
                BufferedReader in = new BufferedReader(new InputStreamReader(socket1.getInputStream()));
                String playerType = in.readLine();

                if(playerType.equals("human"))
                {
                    type = 'P';
                    System.out.println("human connected");
                    user = new User(map.getMapWidth(), map.getMapHeight(), playerID, 0, type, socket1, name);
                    new ThreadClient(socket1,game,playerID, user, type, name).start();
                    playerID++;
                }
                else
                {
                    type = 'B';
                    System.out.println("bot connected");
                    user = new User(map.getMapWidth(), map.getMapHeight(), playerID, 0, type, socket1, name);
                    new ThreadClient(socket1,game,playerID, user, type, name).start();
                    playerID++;
                }
            }
        } catch (SocketException e) {
            System.err.println(e.getLocalizedMessage());
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Could not listen on port " + portNumber);
            System.exit(1);
        }*/
    }

    private static String getTime() {
        //getting current date and time using Date class
        DateFormat DF = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
        Calendar cal = Calendar.getInstance();
        return DF.format(cal.getTime());
    }
}