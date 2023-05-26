package org.project;

import org.project.graphic.PixelArt;
import org.project.network.Publisher;
import org.project.network.Subscriber;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.TimeoutException;

public class Main {

    public static void main(String[] args) throws Exception {

        String uniqueID = UUID.randomUUID().toString();
        String exchangeName = "Test";
        String hostname = "localhost";

        Subscriber subscriber = new Subscriber(uniqueID, exchangeName, hostname);
        subscriber.attachPositionTopic();

        Publisher publisher = new Publisher(uniqueID, exchangeName, hostname);

        PixelArt pixelArt = new PixelArt();
        pixelArt.attachPublisher(publisher);
        pixelArt.initPixelArt();
        pixelArt.showView();

    }
}
