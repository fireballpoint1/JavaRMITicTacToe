package client.gui;

import client.*;
import java.awt.*;
import javax.swing.*;

public class MenuPanel {
    private final JPanel panel;
    private final JLabel lHost;
    private final JTextField tfHost;
    private final JLabel lPort;
    private final JTextField tfPort;
    private final JButton bConnectDisconnect;
    private final JButton bQuit;
    
    public MenuPanel(ClientAction clientAction, ClientReaction clientReaction) {
        clientAction.setMenuPanel(this);
        clientReaction.setMenuPanel(this);
        
        panel = new JPanel();
        panel.setPreferredSize(new Dimension(0, 30));
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        
        lHost = new JLabel(Text.HOST + ": ");
        tfHost = new JTextField("localhost");
        tfHost.setMaximumSize(new Dimension(100, 20));
        tfHost.setPreferredSize(new Dimension(100, 20));
        
        lPort = new JLabel(Text.PORT + ": ");
        tfPort = new JTextField("9000");
        tfPort.setMaximumSize(new Dimension(50, 20));
        tfPort.setPreferredSize(new Dimension(50, 20));
        
        bConnectDisconnect = new JButton(Text.CONNECT);
        bConnectDisconnect.setMaximumSize(new Dimension(130, 20));
        bConnectDisconnect.setPreferredSize(new Dimension(130, 20));
        bConnectDisconnect.addActionListener(e -> {
            clientAction.connectDisconnect();
        });
        
        bQuit = new JButton(Text.QUIT);
        bQuit.setMaximumSize(new Dimension(100, 20));
        bQuit.setPreferredSize(new Dimension(100, 20));
        bQuit.addActionListener(e -> {
            clientAction.quit();
        });
        
        panel.add(Box.createHorizontalStrut(5));
        panel.add(lHost);
        panel.add(tfHost);
        panel.add(Box.createHorizontalStrut(5));
        panel.add(lPort);
        panel.add(tfPort);
        panel.add(Box.createHorizontalStrut(5));
        panel.add(bConnectDisconnect);
        panel.add(Box.createHorizontalGlue());
        panel.add(bQuit);
        panel.add(Box.createHorizontalStrut(5));
        panel.setVisible(true);
    }
    
    public JPanel getPanel() {
        return panel;
    }
    
    public String getHost() {
        return tfHost.getText();
    }
    
    public String getPort() {
        return tfPort.getText();
    }
    
    public void lockMenu() {
        tfHost.setEditable(false);
        tfPort.setEditable(false);
        bConnectDisconnect.setText(Text.DISCONNECT);
    }
    
    public void unlockMenu() {
        tfHost.setEditable(true);
        tfPort.setEditable(true);
        bConnectDisconnect.setText(Text.CONNECT);
    }
}