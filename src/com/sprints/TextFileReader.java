package com.sprints;

import com.sprints.controller.App;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

class TextFileReader {
    // ******** Class Singleton **********
    private static TextFileReader fileReader = null;

    // ******** Singleton Instantiation **********
    // only one instance needed
    public static TextFileReader getInstance() {
        if(fileReader == null) {
            fileReader = new TextFileReader();
        }
        return fileReader;
    }

    // ******** Business Methods **********
    // read text files. May need elsewhere in future versions of Game
    void txtFileReader(String filename) throws IOException {
        try (var in = App.class.getResourceAsStream(filename)) {
            Scanner scanner = new Scanner(in, StandardCharsets.UTF_8);
            while ( scanner.hasNextLine() ){
                if (filename == "/title.txt" || filename == "/gameover.txt") {
                    System.out.println("\u001B[31m" + scanner.nextLine() + "\u001B[37m");
                }
                else{
                    System.out.println(scanner.nextLine());
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Uncaught", e);
        }
    }

}