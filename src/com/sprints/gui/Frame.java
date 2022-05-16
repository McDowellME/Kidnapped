package com.sprints.gui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class Frame implements ActionListener {
    private static JFrame frame;
    private static TitleFrame titleFrame;
    private static GameFrame gameFrame;
    private static LoseScreen loseScreen;
    private static WinScreen winScreen;

    static {
        try {
            titleFrame = new TitleFrame();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    static {
        try {
            gameFrame = new GameFrame();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    static {
        try {
            loseScreen = new LoseScreen();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    static {
        try {
            winScreen = new WinScreen();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public Frame() throws IOException, InterruptedException {
        frame = new JFrame("KIDNAPPED!");
        frame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(titleFrame);
        frame.setResizable(false);
        frame.pack();
        frame.setVisible(true);

    }

    public static void getTitleScreen(){
        frame.getContentPane().removeAll();
        frame.getContentPane().add(titleFrame);
        frame.revalidate();
        frame.repaint();
    }

    public static void getGameFrame() throws IOException, InterruptedException {
        frame.getContentPane().removeAll();
        frame.getContentPane().add(gameFrame);
        frame.revalidate();
        frame.repaint();
    }

    public static void getLoseScreen() throws IOException, InterruptedException {
        frame.getContentPane().removeAll();
        frame.getContentPane().add(loseScreen);
        frame.revalidate();
        frame.repaint();
    }

    public static void getWinScreen() throws IOException, InterruptedException {
        frame.getContentPane().removeAll();
        frame.getContentPane().add(winScreen);
        frame.revalidate();
        frame.repaint();
    }


    @Override
    public void actionPerformed(ActionEvent e) {

    }
}