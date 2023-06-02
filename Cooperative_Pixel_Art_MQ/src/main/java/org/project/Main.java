package org.project;

import org.project.controller.Controller;
import org.project.controller.GraphicController;
import org.project.controller.NetworkController;
import org.project.graphic.PixelArt;
import org.project.message.MessageWelcome;
import org.project.network.FutureQueue;

import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class Main {

    public static void main(String[] args) throws Exception {

        String uniqueID = UUID.randomUUID().toString();
        String exchangeName = "Coop-Pixel-Art";
        String hostname = "localhost";

        Controller controller = new Controller(uniqueID, exchangeName, hostname);
        controller.startProgram();

    }
}
