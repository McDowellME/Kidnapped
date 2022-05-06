package com.sprints;

import javax.sound.sampled.*;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

class MusicPlayer {


    static Clip clip;
    private static boolean isSound = true;     // music is ON by default

    static {
        try {
            clip = AudioSystem.getClip();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
    }


    MusicPlayer() throws LineUnavailableException {
    }


    // start in game music
    static void playSound(String fileName) throws LineUnavailableException, IOException, UnsupportedAudioFileException {
        InputStream is = MusicPlayer.class.getResourceAsStream(fileName);
        AudioInputStream ais = AudioSystem.getAudioInputStream(new BufferedInputStream(is));
        clip.open(ais);
        clip.start();
        clip.loop(-1);
    }

    // pause/re-play in game music
    static void toggleSound() throws LineUnavailableException, IOException, UnsupportedAudioFileException {
        if (isSound) {
            clip.stop();
            isSound = false;
        } else {
            clip.start();
            isSound = true;
        }
    }


    public static void lowerSoundVolume() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        if (!isSound) {
            toggleSound();
        }
        FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        gainControl.setValue(-6.0f);
    }


    public static void raiseSoundVolume() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        if (!isSound) {
            toggleSound();
        }
        FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        gainControl.setValue(+6.0f);
    }
}
