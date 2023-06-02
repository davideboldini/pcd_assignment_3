package org.project;

import org.project.controller.Controller;

import java.util.UUID;

public class Main {

    public static void main(String[] args) throws Exception {

        String uniqueID = UUID.randomUUID().toString();
        String exchangeName = "Coop-Pixel-Art";
        String hostname = "localhost";

        Controller controller = new Controller(uniqueID, exchangeName, hostname);
        controller.startProgram();

    }
}
