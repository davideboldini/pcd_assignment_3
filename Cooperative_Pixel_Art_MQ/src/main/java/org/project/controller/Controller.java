package org.project.controller;

import org.project.graphic.BrushManager;
import org.project.graphic.PixelArt;
import org.project.graphic.PixelGrid;
import org.project.message.MessageWelcome;
import org.project.network.FutureQueue;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class Controller {

    private GraphicController graphicController;
    private NetworkController networkController;
    private BrushController brushController;
    private final String uniqueID;

    public Controller(final String uniqueID){
        this.uniqueID = uniqueID;
        this.brushController = new BrushController();
    }

    public void initNetworkController(final String exchangeName, final String hostname) throws Exception {
        this.networkController = new NetworkController(uniqueID, exchangeName, hostname, this);
        this.networkController.initSubscriber();
    }

    public void initGraphicController(){
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
            MessageWelcome mex = futureQueue.getFutureWelcome().get(5, TimeUnit.SECONDS);

            PixelGrid pixelGrid = mex.getCurrentPixelGrid();
            Map<String, BrushManager.Brush> brushMap = mex.getBrushMap();

            brushController.addBrushMap(brushMap);
            graphicController.deployExistingPixelArt(pixelGrid);

        } catch (TimeoutException | InterruptedException | ExecutionException e) {
            this.graphicController.deployNewPixelArt();
        }
    }

    public void sendWelcomeMessage() {
        PixelGrid pixelGrid = this.graphicController.getPixelArt().getGrid();
        if (pixelGrid == null){
            System.out.println("NULL");
        }
        Map<String, BrushManager.Brush> brushMap = new HashMap<>(brushController.getBrushManager().getBrushMap());

        this.networkController.newWelcome(pixelGrid, brushMap);
    }

    public String getUniqueID() {
        return this.uniqueID;
    }
}
