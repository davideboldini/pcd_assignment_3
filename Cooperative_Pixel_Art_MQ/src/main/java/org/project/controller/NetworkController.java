package org.project.controller;

import org.project.graphic.BrushManager;
import org.project.graphic.PixelArt;
import org.project.graphic.PixelGrid;
import org.project.message.MessageBoot;
import org.project.message.MessageClick;
import org.project.message.MessagePosition;
import org.project.message.MessageWelcome;
import org.project.model.Cell;
import org.project.network.Publisher;
import org.project.network.Subscriber;
import org.project.utility.Pair;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeoutException;

public class NetworkController {

    private Subscriber subscriber;
    private Publisher publisher;

    public NetworkController(final String uniqueID, final String exchangeName) throws Exception {
        this.subscriber = new Subscriber(uniqueID, exchangeName, "localhost");
        this.publisher = new Publisher(uniqueID, exchangeName, "localhost");
    }

    public void initSubscriber(){
        try {
            this.subscriber.attachNewConnectionTopic();
            this.subscriber.attachBootTopic();
            this.subscriber.attachPositionTopic();
            this.subscriber.attachClickTopic();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void newConnection(){
        try {
            this.publisher.publishNewConnectionMessage(new MessageBoot());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void newWelcome(final PixelGrid pixelArt, final Map<String, BrushManager.Brush> brushMap){
        try {
            this.publisher.publishBootMessage(new MessageWelcome(pixelArt, brushMap));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void newPosition(final Pair<Integer, Integer> position, final int colorBrush){
        try {
            this.publisher.publishPositionMessage(new MessagePosition(position, colorBrush));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void newClick(final Pair<Integer, Integer> position, final int colorBrush){
        try {
            this.publisher.publishClickMessage(new MessageClick(new Cell(position.getX(), position.getY(), colorBrush)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
