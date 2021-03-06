package com.sprints.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

class LoseScreen extends JPanel implements ActionListener {
    static TitleFrame titleFrame;
    static {
        try {
            titleFrame = new TitleFrame();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static JLabel lossLabel;
    Font labelFont = new Font("Times New Roman", Font.BOLD, 17);
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

        //Label for reason behind losing
        lossLabel = new JLabel();
        lossLabel.setOpaque(true);
        lossLabel.setBorder(null);
        lossLabel.setFont(labelFont);
        lossLabel.setBackground(new Color(0,0,0,0));
        lossLabel.setForeground(Color.WHITE);


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

        add(lossLabel);
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
                    Frame.getScreen(titleFrame);
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