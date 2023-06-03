package org.project;

import org.project.shared.PixelGrid;
import org.project.graphics.PixelArt;
import org.project.graphics.PixelArtImpl;
import org.project.shared.brush.BrushManager;

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

            PixelArt pixelArt = new PixelArtImpl(uniqueID, brushManager);
            pixelArt.initPixelArt(pixelGrid);
            pixelArt.keepRefresh();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}