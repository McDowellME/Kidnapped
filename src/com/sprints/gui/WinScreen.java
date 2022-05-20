package com.sprints.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

class WinScreen extends JPanel implements ActionListener {
    static TitleFrame titleFrame;
    static {
        try {
            titleFrame = new TitleFrame();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    Font labelFont = new Font("Times New Roman", Font.BOLD, 17);
    Font btnFont = new Font("Times New Roman", Font.BOLD, 30);

    public WinScreen() throws IOException {
        // Setting up the dimensions and background color
        setPreferredSize (new Dimension(1094, 730));
        setLayout(null);
        setBackground(Color.BLACK);

        // The title background image
        ImageIcon winIcon = IconBuilder.mainIcon("images/win.png");

        // Placing the background image
        JLabel imageLabel = new JLabel(winIcon);
        imageLabel.setBounds (0, 0, 1094, 730);

        //Label for reason behind win
        JLabel winLabel = new JLabel();
        winLabel.setOpaque(true);
        winLabel.setBorder(null);
        winLabel.setFont(labelFont);
        winLabel.setBackground(new Color(0,0,0,0));
        winLabel.setForeground(Color.white);
        winLabel.setText("<html> &emsp &emsp &emsp &emsp &emsp You pull the book from the shelf and hear the faint sound of gears rotating.<br/> The shelf slides left, revealing a door. You step through and awake once more...It was all a terrible dream.</html>");
        winLabel.setBounds(160, 300, 1000, 50);


        JLabel blackLabel = new JLabel();
        blackLabel.setOpaque(true);
        blackLabel.setBorder(null);
        blackLabel.setFont(labelFont);
        blackLabel.setBackground(Color.BLACK);
        blackLabel.setBounds(560, 309, 28, 36);

        // Play Again button
        JButton playAgainBtn = new JButton("PLAY AGAIN");
        playAgainBtn.setOpaque(true);
        playAgainBtn.setForeground(new Color(22,139,6));
        playAgainBtn.setBorderPainted(false);
        playAgainBtn.setFont(btnFont);
        playAgainBtn.setFocusPainted(false);
        playAgainBtn.setBorder(null);
        playAgainBtn.setContentAreaFilled(false);
        playAgainBtn.addActionListener(this);
        playAgainBtn.setActionCommand("play");
        playAgainBtn.setBounds (438, 490, 200, 60);

        // Exit button
        JButton exitBtn = new JButton("EXIT");
        exitBtn.setOpaque(true);
        exitBtn.setForeground(new Color(22,139,6));
        exitBtn.setBorderPainted(false);
        exitBtn.setFont(btnFont);
        exitBtn.setFocusPainted(false);
        exitBtn.setBorder(null);
        exitBtn.setContentAreaFilled(false);
        exitBtn.addActionListener(this);
        exitBtn.setActionCommand("exit");
        exitBtn.setBounds (465, 560, 150, 60);

        add(winLabel);
        add(exitBtn);
        add(playAgainBtn);
        add(blackLabel);
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
            }
        catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}