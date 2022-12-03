package com.capstone.backend;

import com.capstone.backend.gui.GUI;
import com.formdev.flatlaf.FlatDarculaLaf;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;

@SpringBootApplication
@EnableJpaRepositories("com.capstone.backend.repository")
public class CapstoneBackendApplication {
    private static File file;
    private static FileChannel channel;
    private static FileLock lock;
    public static void main(String[] args) {
        try {
            file = new File("file.lock");
            if(file.exists()) {
                file.delete();
            }

            channel = new RandomAccessFile(file, "rw").getChannel();
            lock = channel.tryLock();

            if(lock == null) {
                channel.close();
                JOptionPane.showMessageDialog(null,"Only one server can be run at a time.","Server", JOptionPane.ERROR_MESSAGE);
                throw new RuntimeException("Only one server can be run at a time.");
            }
            Thread shutdown = new Thread(CapstoneBackendApplication::unlock);
            Runtime.getRuntime().addShutdownHook(shutdown);
            SpringApplicationBuilder builder = new SpringApplicationBuilder(CapstoneBackendApplication.class);
            builder.headless(false);
            ConfigurableApplicationContext context = builder.run(args);
            EventQueue.invokeLater(() -> {
                try {
                    UIManager.setLookAndFeel(new FlatDarculaLaf());
                    context.getBean(GUI.class).setVisible(true);
                } catch (UnsupportedLookAndFeelException e) {
                    throw new RuntimeException(e);
                }
            });
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void unlock() {
        try {
            if(lock != null) {
                lock.release();
                channel.close();
                file.delete();
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
}
