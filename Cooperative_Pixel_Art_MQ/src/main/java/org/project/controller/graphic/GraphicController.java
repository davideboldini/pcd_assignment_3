package org.project.controller.graphic;

import org.project.controller.Controller;
import org.project.graphic.PixelArt;
import org.project.graphic.PixelGrid;

import java.io.IOException;

public class GraphicController {

    private final Controller controller;
    private PixelArt pixelArt;

    public GraphicController(final Controller controller){
        this.controller = controller;
    }

    public void deployPixelArt(final PixelGrid... pixelGrids) throws IOException {
        pixelArt = new PixelArt(controller);
        pixelArt.initPixelArt(pixelGrids);
        pixelArt.showView();
    }

    public void updateGui() {
        this.pixelArt.getView().refresh();
    }

    public PixelArt getPixelArt() {
        return this.pixelArt;
    }

}
