package org.project.controller;

import org.project.graphic.BrushManager;
import org.project.graphic.PixelArt;
import org.project.graphic.PixelGrid;

import java.io.IOException;
import java.util.Map;

public class GraphicController {

    private Controller controller;
    private PixelArt pixelArt;

    public GraphicController(final Controller controller){
        this.controller = controller;
    }

    public void deployNewPixelArt() throws IOException {
        pixelArt = new PixelArt(controller);
        pixelArt.initNewPixelArt();
        pixelArt.showView();
    }

    public void deployExistingPixelArt(final PixelGrid pixelGrid, final Map<String, BrushManager.Brush> brushMap){
        pixelArt = new PixelArt(controller);
        pixelArt.initExistingPixelArt(pixelGrid, brushMap);
        pixelArt.showView();
    }

    public PixelArt getPixelArt() {
        return this.pixelArt;
    }

}
