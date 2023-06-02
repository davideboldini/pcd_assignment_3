package org.project.controller;

import org.project.graphic.PixelArt;
import org.project.graphic.PixelGrid;

import java.io.IOException;

public class GraphicController {

    private final Controller controller;
    private PixelArt pixelArt;

    public GraphicController(final Controller controller){
        this.controller = controller;
    }

    public void deployNewPixelArt() throws IOException {
        pixelArt = new PixelArt(controller);
        pixelArt.initPixelArt();
        pixelArt.showView();
    }

    public void deployExistingPixelArt(final PixelGrid pixelGrid) throws IOException {
        pixelArt = new PixelArt(controller);
        pixelArt.initPixelArt(pixelGrid);
        pixelArt.showView();
    }

    public void updateGui() {
        this.pixelArt.getView().refresh();
    }

    public PixelArt getPixelArt() {
        return this.pixelArt;
    }

}
