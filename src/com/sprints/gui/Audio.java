package com.sprints.gui;

import javax.imageio.ImageIO;
import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

class Audio {
    static Clip clip;
    private static boolean isSound = true;     // music is ON by default
    static float currentVolume = -17;
    static float previousVolume = -17;
    static FloatControl fc;
    private static URL sound = Audio.class.getResource("main.wav");

    private static final String soundPath = "data/audio/main.wav";
    private static File path = new File(soundPath);

    // get the audio clip used for sound
    static {
        try {
            clip = AudioSystem.getClip();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
    }


    // start in game music
    static void playSound() throws LineUnavailableException, IOException, UnsupportedAudioFileException {
        AudioInputStream ais = AudioSystem.getAudioInputStream(path);
        clip = AudioSystem.getClip();
        clip.open(ais);
        fc = (FloatControl)clip.getControl(FloatControl.Type.MASTER_GAIN);
        clip.setFramePosition(0);
        clip.start();
        fc.setValue(currentVolume);
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    // pause/re-play in game music
    static void toggleSound(JButton audBtn, JSlider audSlider) throws IOException {
        if (isSound) {

            clip.stop();
            isSound = false;
            String mute = "images/mute.png";
            Image audImg = img(mute);
            audBtn.setIcon(new ImageIcon(audImg));
            currentVolume = -80.0f;
            fc.setValue(currentVolume);
            audSlider.setValue(audSlider.getMinimum());
        } else {
            clip.start();
            isSound = true;
            String play = "images/play.png";
            Image audImg = img(play);
            audBtn.setIcon(new ImageIcon(audImg));
            fc.setValue(currentVolume);
            audSlider.setValue((int) previousVolume);
        }
    }

    private static Image img(String is) throws IOException {
        InputStream audio  =  classLoaderResourceStream(is);
        Image audImg = ImageIO.read(audio);
        ImageIcon audIcon = new ImageIcon(audImg);
        Image audImage = audIcon.getImage();
        Image audImg2 = audImage.getScaledInstance(33, 33,  Image.SCALE_DEFAULT);
        return audImg2;
    }

    private static InputStream classLoaderResourceStream(String file){
        InputStream is = GameFrame.class.getClassLoader().getResourceAsStream(file);
        return is;
    }
}
