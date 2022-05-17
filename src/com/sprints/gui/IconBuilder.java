package com.sprints.gui;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;


class IconBuilder {
    // create ImageIcon from Image
    public static ImageIcon imageIcon(String file, int width, int height, int scale) throws IOException {
        Image img = ResourceReader.readImage(file);
        Image imgScaled = img.getScaledInstance(width,height,scale);
        return new ImageIcon(imgScaled);
    }
    // build location ImageIcon
    public static ImageIcon locationIcon(String file) throws IOException {
        return IconBuilder.imageIcon(file,822,400, Image.SCALE_DEFAULT);
    }
    // build button ImageIcon
    public static ImageIcon buttonIcon(String file, int size) throws IOException {
        return IconBuilder.imageIcon(file, size, size, Image.SCALE_DEFAULT);
    }
    // build title, win, lose ImageIcon
    public static ImageIcon mainIcon(String file) throws IOException {
        return IconBuilder.imageIcon(file, 1094, 730, Image.SCALE_DEFAULT);
    }
}