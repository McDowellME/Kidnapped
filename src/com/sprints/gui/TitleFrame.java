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

public class TitleFrame extends JPanel implements ActionListener {

    Font btnFont = new Font("Times New Roman", Font.BOLD, 30);

    public TitleFrame() throws IOException {
        // Setting up the dimensions and background color
        setPreferredSize (new Dimension(1094, 730));
        setLayout(null);
        setBackground(Color.BLACK);

        // The title background image
        InputStream main = classLoaderResourceStream("images/main2.png");
        Image mainImg = ImageIO.read(main);
        ImageIcon imageIcon = new ImageIcon(mainImg);
        Image image = imageIcon.getImage();
        Image img2 = image.getScaledInstance(1094, 730,  Image.SCALE_SMOOTH);

        // Placing the background image
        JLabel imageLabel = new JLabel(new ImageIcon(img2));
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
        startBtn.setBounds (280, 500, 500, 100);

        add(startBtn);
        add(imageLabel);
    }

    private static InputStream classLoaderResourceStream(String file){
        InputStream is = GameFrame.class.getClassLoader().getResourceAsStream(file);
        return is;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equals("start")) {
            try {
                Frame.getGameFrame();
                Audio.playSound();
            } catch (IOException | InterruptedException | LineUnavailableException | UnsupportedAudioFileException ex) {
                ex.printStackTrace();
            }
        }

    }
}