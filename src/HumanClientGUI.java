import com.sun.deploy.panel.JavaPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HumanClientGUI{
    private static void makeHumanGui()
    {
        final JFrame HumanClientGUIFrame = new JFrame("Human Client");
        HumanClientGUIFrame.setLayout(new FlowLayout());

        HumanClientGUIFrame.setSize(500,500);
        HumanClientGUIFrame.getContentPane().setBackground(Color.black);
        HumanClientGUIFrame.setResizable(false);
        HumanClientGUIFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel HumanPanel = new JPanel();
        HumanPanel.setBackground(Color.BLUE);
        HumanPanel.setSize(new Dimension(100,100));

        JButton HelloButton = new JButton("Hello");
        HelloButton.setBounds(50,50,50,50);
        HumanClientGUIFrame.add(HelloButton);

        HelloButton.setForeground(Color.ORANGE);
        HelloButton.setBackground(Color.DARK_GRAY);
        HelloButton.addActionListener(new ActionListener() {
            /**
             * After the CONTROLS button has been clicked, the main menu window
             * is closed, and a new instantiation of the controls window is
             * made, bringing up the controls window.
             */
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(HumanClientGUIFrame, "CYA");
                HumanClientGUIFrame.dispose();
            }
        });

        HumanClientGUIFrame.add(HumanPanel);

        JLabel centreLabel = new JLabel("I'm centre", JLabel.CENTER);
        centreLabel.setSize(100,100);

        HumanPanel.add(centreLabel);

        HumanClientGUIFrame.setVisible(true);
    }






    public static void main(String args[])
    {
        makeHumanGui();
    }
}
