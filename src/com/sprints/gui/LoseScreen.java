package com.sprints.gui;

import com.sprints.OurJSONParser;
import com.sprints.Player;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;

class LoseScreen extends JPanel implements ActionListener {
    Font btnFont = new Font("Times New Roman", Font.BOLD, 30);

    public LoseScreen() throws IOException {
        // Setting up the dimensions and background color
        setPreferredSize (new Dimension(1094, 730));
        setLayout(null);
        setBackground(Color.BLACK);

        // The title background image
        ImageIcon loseIcon = IconBuilder.mainIcon("images/lost.jpg");

        // Placing the background image
        JLabel imageLabel = new JLabel(loseIcon);
        imageLabel.setBounds (0, 0, 1094, 730);

        // Play Again button
        JButton playAgainBtn = new JButton("PLAY AGAIN");
        playAgainBtn.setOpaque(true);
        playAgainBtn.setForeground(Color.RED);
        playAgainBtn.setBorderPainted(false);
        playAgainBtn.setFont(btnFont);
        playAgainBtn.setFocusPainted(false);
        playAgainBtn.setBorder(null);
        playAgainBtn.setContentAreaFilled(false);
        playAgainBtn.addActionListener(this);
        playAgainBtn.setActionCommand("play");
        playAgainBtn.setBounds (423, 460, 200, 60);


        // Exit button
        JButton exitBtn = new JButton("EXIT");
        exitBtn.setOpaque(true);
        exitBtn.setForeground(Color.RED);
        exitBtn.setBorderPainted(false);
        exitBtn.setFont(btnFont);
        exitBtn.setFocusPainted(false);
        exitBtn.setBorder(null);
        exitBtn.setContentAreaFilled(false);
        exitBtn.addActionListener(this);
        exitBtn.setActionCommand("exit");
        exitBtn.setBounds (450, 530, 150, 60);

        add(exitBtn);
        add(playAgainBtn);
        add(imageLabel);
    }

    @Override
    public void actionPerformed(ActionEvent e){
        try {
            //GameFrame.resetGameField();
            switch (e.getActionCommand()){
                case "play":
                    TimeUnit.SECONDS.sleep(1);
                    //reset();
                    Frame.getTitleScreen();
                    break;
                case "exit":
                    TimeUnit.SECONDS.sleep(1);
                    System.exit(0);
                    break;
                default:
            }
        }catch (InterruptedException ex) {
            ex.printStackTrace();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

}