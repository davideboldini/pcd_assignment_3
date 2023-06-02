package org.project.controller.network;

import org.project.controller.Controller;
import org.project.graphic.BrushManager;
import org.project.graphic.PixelGrid;
import org.project.model.Cell;
import org.project.model.message.*;
import org.project.utility.Pair;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeoutException;

public class NetworkController {

    private final Subscriber subscriber;
    private final Publisher publisher;
    private final FutureWelcome futureWelcome;
    private final Controller controller;

    public NetworkController(final String uniqueID, final String exchangeName, final String hostname, final Controller controller) throws Exception {
        this.futureWelcome = new FutureWelcome();
        this.subscriber = new Subscriber(uniqueID, exchangeName, hostname, futureWelcome, this);
        this.publisher = new Publisher(uniqueID, exchangeName, hostname, futureWelcome);
        this.controller = controller;
    }

    public void initSubscriber(){
        try {
            this.subscriber.declareExchange();
            this.subscriber.declareQueues();
            this.subscriber.declareBindings();
            this.subscriber.attachCallback();
        } catch (IOException | TimeoutException e) {
            throw new RuntimeException(e);
        }
    }

    public void newConnection(){
        try {
            this.publisher.publishNewConnectionMessage(new MessageBoot());
        } catch (IOException | TimeoutException e) {
            throw new RuntimeException(e);
        }
    }

    public void newWelcome(final PixelGrid pixelArt, final Map<String, BrushManager.Brush> brushMap){
        try {
            this.publisher.publishWelcomeMessage(new MessageWelcome(pixelArt, brushMap));
        } catch (IOException | TimeoutException e) {
            throw new RuntimeException(e);
        }
    }

    public void newPosition(final BrushManager.Brush brush){
        try {
            this.publisher.publishPositionMessage(new MessagePosition(brush));
        } catch (IOException | TimeoutException e) {
            throw new RuntimeException(e);
        }
    }

    public void newClick(final Pair<Integer, Integer> position, final int colorBrush){
        try {
            this.publisher.publishClickMessage(new MessageClick(new Cell(position.getX(), position.getY(), colorBrush)));
        } catch (IOException | TimeoutException e) {
            throw new RuntimeException(e);
        }
    }

    public void newClose(){
        try {
            this.publisher.publishCloseMessage(new MessageClose());
        } catch (IOException | TimeoutException e) {
            throw new RuntimeException(e);
        }
    }

    public FutureWelcome getFutureQueue() {
        return this.futureWelcome;
    }

    public Controller getController() {
        return this.controller;
    }
}
