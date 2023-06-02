package org.project.controller;

import org.project.graphic.BrushManager;
import org.project.graphic.PixelGrid;
import org.project.message.MessageWelcome;
import org.project.network.FutureQueue;

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
        networkController.newConnection();
        try {
            System.out.println("Controllo della presenza di altri utenti...");
            MessageWelcome mexWelcome = futureQueue.getFutureWelcome().get(3, TimeUnit.SECONDS);
            System.out.println("Utente trovato!");

            PixelGrid pixelGrid = mexWelcome.getCurrentPixelGrid();

            Map<String, BrushManager.Brush> brushMap = mexWelcome.getBrushMap();
            graphicController.deployExistingPixelArt(pixelGrid);
            brushController.addBrushMap(brushMap);

        } catch (TimeoutException | InterruptedException | ExecutionException e) {
            System.out.println("Nessun utente trovato");
            this.graphicController.deployNewPixelArt();
        }
    }

    public String getUniqueID() {
        return this.uniqueID;
    }
}
