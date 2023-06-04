package org.project;

import org.project.shared.grid.PixelGrid;
import org.project.graphics.PixelArt;
import org.project.graphics.PixelArtImpl;
import org.project.shared.brush.BrushManager;
import org.project.shared.log.Log;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.UUID;

public class Main {
    public static void main(String[] args) {

        try {
            String uniqueID = UUID.randomUUID().toString();
            Registry registry = LocateRegistry.getRegistry(null);

            Log logger = (Log) registry.lookup("loggerObj");
            PixelGrid pixelGrid = (PixelGrid) registry.lookup("pixelGridObj");
            BrushManager brushManager = (BrushManager) registry.lookup("brushManagerObj");

            PixelArt pixelArt = new PixelArtImpl(uniqueID, brushManager);
            pixelArt.initPixelArt(pixelGrid);
            pixelArt.keepRefresh();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}