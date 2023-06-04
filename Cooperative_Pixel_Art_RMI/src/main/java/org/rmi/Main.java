package org.rmi;

import org.rmi.model.message.sender.Sender;
import org.rmi.shared.grid.PixelGrid;
import org.rmi.graphic.PixelArt;
import org.rmi.graphic.PixelArtImpl;
import org.rmi.shared.brush.BrushManager;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.UUID;

public class Main {
    public static void main(String[] args) {

        try {
            String uniqueID = UUID.randomUUID().toString();
            Registry registry = LocateRegistry.getRegistry(null);

            PixelGrid pixelGrid = (PixelGrid) registry.lookup("pixelGridObj");
            BrushManager brushManager = (BrushManager) registry.lookup("brushManagerObj");

            Sender sender = new Sender(uniqueID, brushManager);
            Thread senderThread = new Thread(sender);
            senderThread.start();
            PixelArt pixelArt = new PixelArtImpl(brushManager, sender);
            pixelArt.initPixelArt(pixelGrid);
            pixelArt.keepRefresh();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}