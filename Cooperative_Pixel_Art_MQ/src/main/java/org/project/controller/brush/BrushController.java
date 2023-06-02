package org.project.controller.brush;

import org.project.controller.Controller;
import org.project.graphic.BrushManager;
import org.project.utility.Pair;

import java.util.Map;

public class BrushController implements Runnable{

    private final BrushManager brushManager;
    private final Controller controller;

    public BrushController(final Controller controller) {
        this.brushManager = new BrushManager();
        this.controller = controller;
    }

    public void addBrush(final String uniqueID, final BrushManager.Brush brush) {
        this.brushManager.addBrush(uniqueID, brush);
        this.controller.getGraphicController().getPixelArt().getView().addUserToTable(uniqueID, brush.getColor());
    }

    public void addBrushMap(final Map<String, BrushManager.Brush> brushMap){
        for (Map.Entry<String, BrushManager.Brush> entry : brushMap.entrySet()) {
            this.brushManager.addBrush(entry.getKey(), entry.getValue());
            this.controller.getGraphicController().getPixelArt().getView().addUserToTable(entry.getKey(), entry.getValue().getColor());
        }
    }

    public void removeBrush(final String uniqueID) {
        this.brushManager.removeBrush(uniqueID);
        this.controller.getGraphicController().getPixelArt().getView().removeUserToTable(uniqueID);
    }

    public void updateBrushPosition(final String uniqueID, final BrushManager.Brush brush) {
        if (!this.brushManager.getBrushMap().containsKey(uniqueID)) {
            this.addBrush(uniqueID, brush);
        }
        this.brushManager.getBrushMap().get(uniqueID).updatePosition(brush.getX(), brush.getY());
        if (this.brushManager.getBrushMap().get(uniqueID).getColor() != brush.getColor()) {
            this.updateBrushColor(uniqueID, brush.getColor());
        }

        this.controller.getGraphicController().updateGui();
    }

    public void updateBrushColor(final String uniqueID, final int color) {
        this.brushManager.getBrushMap().get(uniqueID).setColor(color);
        this.controller.getGraphicController().getPixelArt().getView().updateColorUser(uniqueID, color);
    }

    public BrushManager getBrushManager() {
        return this.brushManager;
    }

    @Override
    public void run() {
        while (true){}
    }
}
