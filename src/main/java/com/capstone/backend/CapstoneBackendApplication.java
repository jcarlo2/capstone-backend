package com.capstone.backend;

import com.capstone.backend.gui.GUI;
import com.formdev.flatlaf.FlatDarculaLaf;
import org.apache.commons.io.IOUtils;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.charset.StandardCharsets;

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

            test();
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

    private static void test() {
        try {
            FileInputStream fis = new FileInputStream("src/main/resources/file.txt");
            String str = IOUtils.toString(fis, StandardCharsets.UTF_8);
            StringBuilder temp = new StringBuilder();
            for(int i=0;i<str.toCharArray().length;i++) {
                int num = 1;
                if(str.charAt(i) == '(' && !Character.isAlphabetic(str.charAt(i + num))) {
                    temp.append(str.charAt(i));
                    while(true) if(str.charAt(i + num++) == '\'') break;
                    i += num - 1;
                }
                temp.append(str.charAt(i));
            }
            System.out.println("DONE");
            System.out.println(str);
            System.out.println(temp);
            System.out.println("DONE");
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
