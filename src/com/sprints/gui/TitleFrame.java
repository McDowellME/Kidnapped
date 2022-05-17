package com.sprints.gui;

import com.sprints.controller.App;

import javax.imageio.ImageIO;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.concurrent.TimeUnit;

public class TitleFrame extends JPanel implements ActionListener {

    Font btnFont = new Font("Times New Roman", Font.BOLD, 30);

    public TitleFrame() throws IOException {
        // Setting up the dimensions and background color
        setPreferredSize (new Dimension(1094, 730));
        setLayout(null);
        setBackground(Color.BLACK);

        // The title background image
        ImageIcon titleIcon = IconBuilder.mainIcon("images/main2.png");

        // Placing the background image
        JLabel imageLabel = new JLabel(titleIcon);
        imageLabel.setBounds (0, 0, 1094, 730);

        // Start button
        JButton startBtn = new JButton("START");
        startBtn.setOpaque(true);
        startBtn.setForeground(Color.RED);
        startBtn.setBorderPainted(false);
        startBtn.setFont(btnFont);
        startBtn.setFocusPainted(false);
        startBtn.setBorder(null);
        startBtn.setContentAreaFilled(false);
        startBtn.addActionListener(this);
        startBtn.setActionCommand("start");
        startBtn.setBounds (450, 530, 150, 60);

        add(startBtn);
        add(imageLabel);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equals("start")) {
            try {
                TimeUnit.SECONDS.sleep(1);

                Frame.getGameFrame();
                Audio.playSound();
                GameFrame.setCountDown();
            } catch (IOException | InterruptedException | LineUnavailableException | UnsupportedAudioFileException ex) {
                ex.printStackTrace();
            }
        }

    }
}