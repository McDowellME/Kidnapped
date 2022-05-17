package com.sprints.gui;

import com.sprints.OurJSONParser;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.URL;
import java.util.Objects;

// Read resources as streams for jarring purposes
class ResourceReader {
    // read image files, return Image
    public static Image readImage(String file) throws IOException {
        InputStream imageIS = GameFrame.class.getClassLoader().getResourceAsStream(file);
        return ImageIO.read(imageIS);
    }

    // read text file, return BufferedReader
    public static InputStreamReader readText(String file) {
        return new InputStreamReader(Objects.requireNonNull(ResourceReader.class.getResourceAsStream(file)));
    }

    // read audio file, return AudioInputStream
    public static AudioInputStream readAudio(String file) throws UnsupportedAudioFileException, IOException {
        URL sound = Audio.class.getResource(file);
        return AudioSystem.getAudioInputStream(sound);
//        InputStream sound = Audio.class.getResourceAsStream(file);
//        return AudioSystem.getAudioInputStream(new BufferedInputStream(sound));
    }

    // read json, return InputStreamReader (not in use)
    public static InputStreamReader readJson(String file) {
        return new InputStreamReader(Objects.requireNonNull(OurJSONParser.class.getResourceAsStream(file)));
    }
}