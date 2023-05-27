package org.project;

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
        String exchangeName = "Test";
        String hostname = "localhost";


        Subscriber subscriber = new Subscriber(uniqueID, exchangeName, hostname);
        Publisher publisher = new Publisher(uniqueID, exchangeName, hostname);

        PixelArt pixelArt = new PixelArt();
        pixelArt.initPixelArt();
        pixelArt.showView();
        pixelArt.attachPublisher(publisher);

        subscriber.attachNewConnectionTopic();
        subscriber.attachPositionTopic();

        publisher.publishNewConnectionMessage(new MessageBoot());
    }
}
