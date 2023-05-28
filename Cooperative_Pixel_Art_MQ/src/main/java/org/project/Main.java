package org.project;

import org.project.controller.NetworkController;
import org.project.graphic.PixelArt;
import org.project.message.MessageBoot;
import org.project.network.Publisher;
import org.project.network.Subscriber;
import org.project.utility.Pair;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.TimeoutException;

public class Main {

    public static void main(String[] args) throws Exception {

        String uniqueID = UUID.randomUUID().toString();
        String exchangeName = "Coop-Pixel-Art";
        String hostname = "localhost";

        NetworkController networkController = new NetworkController(uniqueID, exchangeName);
        networkController.initSubscriber();

        PixelArt pixelArt = new PixelArt();
        pixelArt.attachController(networkController);
        pixelArt.initPixelArt();
        pixelArt.showView();
    }
}
