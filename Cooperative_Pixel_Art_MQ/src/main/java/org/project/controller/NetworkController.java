package org.project.controller;

import org.project.graphic.BrushManager;
import org.project.graphic.PixelGrid;
import org.project.message.*;
import org.project.model.Cell;
import org.project.network.FutureQueue;
import org.project.network.Publisher;
import org.project.network.Subscriber;
import org.project.utility.Pair;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeoutException;

public class NetworkController {

    private final Subscriber subscriber;
    private final Publisher publisher;
    private FutureQueue futureQueue;

    private String uniqueID;
    private Controller controller;

    public NetworkController(final String uniqueID, final String exchangeName, final String hostname, final Controller controller) throws Exception {
        this.futureQueue = new FutureQueue();
        this.uniqueID = uniqueID;
        this.subscriber = new Subscriber(uniqueID, exchangeName, hostname, futureQueue, this);
        this.publisher = new Publisher(uniqueID, exchangeName, hostname, futureQueue, this);
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

    public FutureQueue getFutureQueue() {
        return futureQueue;
    }

    public Controller getController() {
        return controller;
    }
}
