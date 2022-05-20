package com.sprints.gui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class Frame implements ActionListener {
    private static JFrame frame;
    private static TitleFrame titleFrame;

    static {
        try {
            titleFrame = new TitleFrame();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public Frame() {
        frame = new JFrame("KIDNAPPED!");
        frame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(titleFrame);
        frame.setResizable(false);
        frame.pack();
        frame.setVisible(true);

    }

    public static void getScreen(JPanel panel){
        frame.getContentPane().removeAll();
        frame.getContentPane().add(panel);
        frame.revalidate();
        frame.repaint();
    }


    @Override
    public void actionPerformed(ActionEvent e) {

    }


}