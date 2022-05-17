package com.sprints.gui;

import com.sprints.OurJSONParser;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.Objects;

// Read resources as streams for jarring purposes
class ResourceReader {
    // read image files, return Image
    public static Image readImage(String file) throws IOException {
        InputStream imageIS = GameFrame.class.getClassLoader().getResourceAsStream(file);
        return ImageIO.read(imageIS);
    }

    // read text file, return BufferedReader
    public static BufferedReader readText(String file) throws FileNotFoundException {
        return new BufferedReader(new InputStreamReader(new FileInputStream(file)));
    }

    // read audio file



    // read json, return InputStreamReader (not in use)
    public static InputStreamReader readJson(String file) {
        return new InputStreamReader(Objects.requireNonNull(OurJSONParser.class.getResourceAsStream(file)));
    }
}