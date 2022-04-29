package com.sprints.clients;

import com.sprints.controller.App;
import org.json.simple.parser.ParseException;

import java.io.IOException;

class Main {
    public static void main(String[] args) throws IOException, ParseException {
        App app = new App();
        app.execute();
    }
}