package com.sprints.gui;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

class InstructionFrame extends JPanel implements ActionListener {
    Font btnFont = new Font("Times New Roman", Font.BOLD, 30);
    static GameFrame gameFrame;
    static {
        try {
            gameFrame = new GameFrame();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public InstructionFrame() throws IOException {
        // Setting up the dimensions and background color
        setPreferredSize (new Dimension(1094, 730));
        setLayout(null);
        setBackground(Color.BLACK);

        // The title background image
        ImageIcon titleIcon = IconBuilder.mainIcon("images/instructions.png");

        // Placing the background image
        JLabel imageLabel = new JLabel(titleIcon);
        imageLabel.setBounds (0, 0, 1094, 760);

        // Start button
        JButton startBtn = new JButton("CONTINUE");
        startBtn.setOpaque(true);
        startBtn.setForeground(Color.WHITE);
        startBtn.setBorderPainted(false);
        startBtn.setFont(btnFont);
        startBtn.setFocusPainted(false);
        startBtn.setBorder(null);
        startBtn.setContentAreaFilled(false);
        startBtn.addActionListener(this);
        startBtn.setActionCommand("continue");
        startBtn.setBounds (450, 530, 170, 60);

        add(startBtn);
        add(imageLabel);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equals("continue")) {
            try {
                TimeUnit.SECONDS.sleep(1);

                Frame.getScreen(gameFrame);
                Audio.playSound();
                GameFrame.setCountDown();
            } catch (IOException | InterruptedException | LineUnavailableException | UnsupportedAudioFileException ex) {
                ex.printStackTrace();
            }
        }

    }
}