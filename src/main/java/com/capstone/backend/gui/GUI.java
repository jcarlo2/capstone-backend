package com.capstone.backend.gui;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;

public class GUI extends JFrame {

    private final JPanel panel = new JPanel();

    @Autowired
    private Environment environment;

    public GUI() {
        setTitle("Computerized Sales And Inventory Server");
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
            JPanel top = new JPanel();
            JButton close = new JButton("CLOSE SERVER");
            JButton export = new JButton("EXPORT ALL DATA");

            ipTextField.setHorizontalAlignment(JTextField.CENTER);
            ipTextField.setFont(new Font("Courier", Font.BOLD,18));
            ipTextField.setEditable(false);

            top.setLayout(new GridLayout(1,2));
            top.add(export);
            top.add(close);

            close.addActionListener(e -> {
                int num = JOptionPane.showConfirmDialog(null,"Do you want to close the server?","Server",JOptionPane.YES_NO_OPTION);
                if(num == 0) System.exit(0);
            });

            export.addActionListener(e -> {
                try {
                    String documentPath = FileSystemView.getFileSystemView().getDefaultDirectory().getPath();
                    String filename = "\\hbc-all-data.sql";
                    File file = new File(documentPath + filename);
                    filename = createFile(file,documentPath,filename);
                    if(backup(filename)) JOptionPane.showMessageDialog(null,"All data is exported successfully.");
                    else JOptionPane.showMessageDialog(null,"Failed to export data.\nTry Again!!");
                } catch (IOException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null,"Environment Path is not set for mysql.");
                }
            });

            panel.setLayout(new BorderLayout());
            panel.add(top,BorderLayout.NORTH);
            panel.add(ipTextField,BorderLayout.CENTER);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String createFile(@NotNull File file, String documentPath, String filename) throws IOException {
        int num = 1;
        while (!file.createNewFile()) {
            filename = String.format("\\hbc-all-data(%s).sql", num++);
            file = new File(documentPath + filename);
        }
        return filename;
    }

    public boolean backup(String filename) {
        String password = environment.getProperty("spring.datasource.password");
        String documentPath = FileSystemView.getFileSystemView().getDefaultDirectory().getPath() + File.separator + filename;
        String command = "mysqldump -uroot -p" + password + " --add-drop-table --databases retail_management -r " + documentPath;

        int processComplete = 0;
        try {
            Process process = Runtime.getRuntime().exec(command);
            processComplete = process.waitFor();
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
        return processComplete == 0;
    }
}
