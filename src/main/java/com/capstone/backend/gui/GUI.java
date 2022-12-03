package com.capstone.backend.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.InetAddress;

public class GUI extends JFrame {
    private final JPanel panel = new JPanel();

    public GUI() {
        setTitle("Server");
        setSize(400,200);

        setPanel();

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                setExtendedState(JFrame.ICONIFIED);
            }

            @Override
            public void windowIconified(WindowEvent e) {
                setExtendedState(JFrame.ICONIFIED);
            }

        });

        add(panel);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    }

    private void setPanel() {
        try {
            InetAddress myIP = InetAddress.getLocalHost();
            JTextField ipTextField = new JTextField("SERVER IP: " + myIP.getHostAddress());
            JButton close = new JButton("Close Server");

            ipTextField.setHorizontalAlignment(JTextField.CENTER);
            ipTextField.setFont(new Font("Courier", Font.BOLD,18));
            ipTextField.setEditable(false);

            close.addActionListener(e -> {
                int num = JOptionPane.showConfirmDialog(null,"Do you want to close the server?","Server",JOptionPane.YES_NO_OPTION);
                if(num == 0) System.exit(0);
            });

            panel.setLayout(new BorderLayout());
            panel.add(ipTextField,BorderLayout.CENTER);
            panel.add(close,BorderLayout.SOUTH);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
