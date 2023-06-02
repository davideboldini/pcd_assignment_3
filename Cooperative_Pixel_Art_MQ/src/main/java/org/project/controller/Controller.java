package org.project.controller;

import org.project.controller.brush.BrushController;
import org.project.controller.graphic.GraphicController;
import org.project.controller.network.FutureQueue;
import org.project.controller.network.NetworkController;
import org.project.graphic.BrushManager;
import org.project.graphic.PixelGrid;
import org.project.utility.Pair;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class Controller {

    private final GraphicController graphicController;
    private final NetworkController networkController;
    private final BrushController brushController;
    private final String uniqueID;

    public Controller(final String uniqueID, final String exchangeName, final String hostname) throws Exception {
        this.uniqueID = uniqueID;
        this.brushController = new BrushController(this);
        this.networkController = new NetworkController(uniqueID, exchangeName, hostname, this);
        this.networkController.initSubscriber();
        this.graphicController = new GraphicController(this);
    }

    public NetworkController getNetworkController() {
        return this.networkController;
    }

    public GraphicController getGraphicController() {
        return this.graphicController;
    }

    public BrushController getBrushController() {
        return this.brushController;
    }

    public void startProgram() throws IOException {
        Thread brushControllerThread = new Thread(brushController);
        brushControllerThread.start();

        FutureQueue futureQueue = networkController.getFutureQueue();
        this.networkController.newConnection();
        try {
            System.out.println("Controllo della presenza di altri utenti...");

            Pair<PixelGrid, Map<String, BrushManager.Brush>> welcomePair = futureQueue.getFutureWelcome().get(3, TimeUnit.SECONDS);

            System.out.println("Utente trovato!");

            PixelGrid pixelGrid = welcomePair.getX();
            Map<String, BrushManager.Brush> brushMap = welcomePair.getY();

            graphicController.deployPixelArt(pixelGrid);
            brushController.addBrushMap(brushMap);

        } catch (TimeoutException | InterruptedException | ExecutionException e) {
            System.out.println("Nessun utente trovato");
            this.graphicController.deployPixelArt();
        }
    }

    public String getUniqueID() {
        return this.uniqueID;
    }
}
