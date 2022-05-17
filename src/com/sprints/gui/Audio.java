package com.sprints.gui;

import javax.sound.sampled.*;
import javax.swing.*;
import java.io.IOException;
import java.net.URL;

class Audio {
    static Clip clip;
    private static boolean isSound = true;     // music is ON by default
    static float currentVolume = -17;
    static float previousVolume = -17;
    static FloatControl fc;
    private static URL sound = Audio.class.getResource("main.wav");
//    private static InputStream sound = Audio.class.getResourceAsStream("data/audio/main.wav");

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
        AudioInputStream ais = AudioSystem.getAudioInputStream(sound);
//        AudioInputStream ais = AudioSystem.getAudioInputStream(new BufferedInputStream(sound));
        clip = AudioSystem.getClip();
        clip.open(ais);
        fc = (FloatControl)clip.getControl(FloatControl.Type.MASTER_GAIN);
        clip.setFramePosition(0);
        clip.start();
        fc.setValue(currentVolume);
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    static void stopSound(){
        clip.stop();
        clip.close();
    }

    // pause/re-play in game music
    static void toggleSound(JButton audBtn, JSlider audSlider) throws IOException {
        if (isSound) {

            previousVolume = currentVolume;
            currentVolume = -80.0f;

            clip.stop();
            isSound = false;
            String mute = "images/mute.png";
            ImageIcon audIcon = IconBuilder.buttonIcon(mute,33);
            audBtn.setIcon(audIcon);
            fc.setValue(currentVolume);
            audSlider.setValue(audSlider.getMinimum());
        } else {

            currentVolume = previousVolume;

            clip.start();
            isSound = true;
            String play = "images/play.png";
            ImageIcon audIcon = IconBuilder.buttonIcon(play,33);
            audBtn.setIcon(audIcon);
            fc.setValue(currentVolume);
            audSlider.setValue((int) currentVolume);
        }
    }
}
