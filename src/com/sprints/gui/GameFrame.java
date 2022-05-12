package com.sprints.gui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;

class GameFrame extends JPanel implements ActionListener {
    JTextField textField;
    JTextArea textArea, inventoryTextArea;
    JLabel inventoryLabel, locationLabel;
    JButton enterBtn, northBtn, eastBtn, southBtn, westBtn, audBtn;
    JSlider audSlider;
    Font txtFont = new Font("Times New Roman", Font.BOLD, 20);
    Font inventoryFont = new Font("Times New Roman", Font.BOLD, 20);

    public GameFrame() throws IOException {
        // Dimensions of the frame
        setPreferredSize (new Dimension(1094, 730));
        setLayout(null);
        setBackground(Color.BLACK);

        // Location image
        InputStream location = classLoaderResourceStream("images/placeholder.png");
        Image locImg = ImageIO.read(location);
        ImageIcon imageIcon = new ImageIcon(locImg);
        Image locImage = imageIcon.getImage();
        Image locImg2 = locImage.getScaledInstance(1094, 730,  Image.SCALE_SMOOTH);
        locationLabel = new JLabel(new ImageIcon(locImg2));
        locationLabel.setBounds (136, 20, 822, 400);

        // User input
        textField = new JTextField();
        textField.setHorizontalAlignment(SwingConstants.CENTER);
        textField.addActionListener( action ); // let me use enter key on keyboard
        textField.setFont(new Font("Calibri", Font.BOLD, 17));
        textField.setBounds(345,640,300,50);

        // Description
        textArea = new JTextArea();
        textArea.setBackground(Color.WHITE);
        textArea.setForeground(Color.BLACK);
        textArea.setFont(txtFont);
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea.setBounds(325,450,410,150);

        // Inventory
        inventoryTextArea = new JTextArea();
        inventoryTextArea.setBackground(Color.WHITE);
        inventoryTextArea.setForeground(Color.BLACK);
        inventoryTextArea.setFont(txtFont);
        inventoryTextArea.setEditable(false);
        inventoryTextArea.setLineWrap(true);
        inventoryTextArea.setBounds(100,450,160,150);

        // Inventory Label
        inventoryLabel = new JLabel("Inventory");
        inventoryLabel.setForeground(Color.RED);
        inventoryLabel.setFont(inventoryFont);
        inventoryLabel.setBounds(140,429,160,20);

        // Enter button
        enterBtn = new JButton("Enter");
        enterBtn.setOpaque(true);
        enterBtn.setForeground(Color.RED);
        enterBtn.setBorderPainted(false);
        enterBtn.setFont(txtFont);
        enterBtn.setFocusPainted(false);
        enterBtn.setBorder(null);
        enterBtn.setContentAreaFilled(false);
        enterBtn.addActionListener(this);
        enterBtn.setBounds (650, 640, 80, 50);

        // North button
        InputStream north  =  classLoaderResourceStream("images/button_n.png");
        Image nImg = ImageIO.read(north);
        ImageIcon nIcon = new ImageIcon(nImg);
        Image nImage = nIcon.getImage();
        Image nImg2 = nImage.getScaledInstance(33, 33,  Image.SCALE_DEFAULT);

        northBtn = new JButton();
        northBtn.setOpaque(true);
        northBtn.setBorderPainted(false);
        northBtn.setFont(txtFont);
        northBtn.setFocusPainted(false);
        northBtn.setBorder(null);
        northBtn.setContentAreaFilled(false);
        northBtn.addActionListener(this);
        northBtn.setIcon(new ImageIcon(nImg2));
        northBtn.setBounds (850, 450, 80, 50);

        // East button
        InputStream east  =  classLoaderResourceStream("images/button_e.png");
        Image eImg = ImageIO.read(east);
        ImageIcon eIcon = new ImageIcon(eImg);
        Image eImage = eIcon.getImage();
        Image eImg2 = eImage.getScaledInstance(33, 33,  Image.SCALE_DEFAULT);

        eastBtn = new JButton();
        eastBtn.setOpaque(true);
        eastBtn.setBorderPainted(false);
        eastBtn.setFont(txtFont);
        eastBtn.setFocusPainted(false);
        eastBtn.setBorder(null);
        eastBtn.setContentAreaFilled(false);
        eastBtn.addActionListener(this);
        eastBtn.setIcon(new ImageIcon(eImg2));
        eastBtn.setBounds (900, 500, 80, 50);

        // South Button
        InputStream south  =  classLoaderResourceStream("images/button_s.png");
        Image sImg = ImageIO.read(south);
        ImageIcon sIcon = new ImageIcon(sImg);
        Image sImage = sIcon.getImage();
        Image sImg2 = sImage.getScaledInstance(33, 33,  Image.SCALE_DEFAULT);

        southBtn = new JButton();
        southBtn.setOpaque(true);
        southBtn.setBorderPainted(false);
        southBtn.setFont(txtFont);
        southBtn.setFocusPainted(false);
        southBtn.setBorder(null);
        southBtn.setContentAreaFilled(false);
        southBtn.addActionListener(this);
        southBtn.setIcon(new ImageIcon(sImg2));
        southBtn.setBounds (850, 550, 80, 50);

        // West button
        InputStream west  =  classLoaderResourceStream("images/button_w.png");
        Image wImg = ImageIO.read(west);
        ImageIcon wIcon = new ImageIcon(wImg);
        Image wImage = wIcon.getImage();
        Image wImg2 = wImage.getScaledInstance(33, 33,  Image.SCALE_DEFAULT);

        westBtn = new JButton();
        westBtn.setOpaque(true);
        westBtn.setBorderPainted(false);
        westBtn.setFont(txtFont);
        westBtn.setFocusPainted(false);
        westBtn.setBorder(null);
        westBtn.setContentAreaFilled(false);
        westBtn.addActionListener(this);
        westBtn.setIcon(new ImageIcon(wImg2));
        westBtn.setBounds (800, 500, 80, 50);

        // Audio button
        InputStream audio  =  classLoaderResourceStream("images/play.png");
        Image audImg = ImageIO.read(audio);
        ImageIcon audIcon = new ImageIcon(audImg);
        Image audImage = audIcon.getImage();
        Image audImg2 = audImage.getScaledInstance(33, 33,  Image.SCALE_DEFAULT);

        audBtn = new JButton();
        audBtn.setOpaque(true);
        audBtn.setBorderPainted(false);
        audBtn.setFocusPainted(false);
        audBtn.setBorder(null);
        audBtn.setContentAreaFilled(false);
        audBtn.addActionListener(this);
        audBtn.setActionCommand("audToggle");
        audBtn.setIcon(new ImageIcon(audImg2));
        audBtn.setBounds (1000, 377, 80, 50);

        // Audio slider
        audSlider = new JSlider( JSlider.VERTICAL,-40,6,-17);
        audSlider.setOpaque(true);
        audSlider.setBackground(Color.WHITE);
        audSlider.addChangeListener(e -> {
            Audio.currentVolume = audSlider.getValue();
            // Barely audible past -40 so easier to mute at that level
            if(Audio.currentVolume == -40){
                Audio.currentVolume = -80;
            }
            System.out.println(Audio.currentVolume);
            Audio.fc.setValue(Audio.currentVolume);
        });
        audSlider.setBounds (1025, 250, 31, 120);

        add(locationLabel);
        add(textField);
        add(textArea);
        add(inventoryTextArea);
        add(inventoryLabel);
        add(enterBtn);
        add(northBtn);
        add(eastBtn);
        add(southBtn);
        add(westBtn);
        add(audBtn);
        add(audSlider);
        setVisible(true);

    }
    // for Jar-ing purposes
    private static InputStream classLoaderResourceStream(String file){
        InputStream is = GameFrame.class.getClassLoader().getResourceAsStream(file);
        return is;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equals("audToggle")){
            try {
                Audio.toggleSound(audBtn, audSlider);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    // For the key 'enter'
    Action action = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
        }
    };
}