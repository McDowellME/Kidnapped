package com.sprints.gui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;

class WinScreen extends JPanel implements ActionListener {

    Font btnFont = new Font("Times New Roman", Font.BOLD, 30);

    public WinScreen() throws IOException {
        // Setting up the dimensions and background color
        setPreferredSize (new Dimension(1094, 730));
        setLayout(null);
        setBackground(Color.BLACK);

        // The title background image
        InputStream main = classLoaderResourceStream("images/win.png");
        Image mainImg = ImageIO.read(main);
        ImageIcon imageIcon = new ImageIcon(mainImg);
        Image image = imageIcon.getImage();
        Image img2 = image.getScaledInstance(1094, 730,  Image.SCALE_SMOOTH);

        // Placing the background image
        JLabel imageLabel = new JLabel(new ImageIcon(img2));
        imageLabel.setBounds (0, 0, 1094, 730);

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

        add(exitBtn);
        add(playAgainBtn);
        add(imageLabel);
    }

    private static InputStream classLoaderResourceStream(String file){
        InputStream is = GameFrame.class.getClassLoader().getResourceAsStream(file);
        return is;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
        switch (e.getActionCommand()){
            case "play":
                TimeUnit.SECONDS.sleep(1);
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
            }
        }
    }