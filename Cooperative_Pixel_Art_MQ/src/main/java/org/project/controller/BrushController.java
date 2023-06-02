package org.project.controller;

import org.project.graphic.BrushManager;
import org.project.utility.Pair;

import java.util.Map;

public class BrushController implements Runnable{


    private BrushManager brushManager;
    private Controller controller;

    public BrushController(final Controller controller) {
        this.brushManager = new BrushManager();
        this.controller = controller;
    }

    public void addBrush(final String uniqueID, final BrushManager.Brush brush) {
        this.brushManager.addBrush(uniqueID, brush);
        controller.getGraphicController().getPixelArt().getView().addUserToTable(uniqueID, brush.getColor());
    }

    public void addBrushMap(final Map<String, BrushManager.Brush> brushMap){
        for (Map.Entry<String, BrushManager.Brush> entry : brushMap.entrySet()) {
            brushManager.addBrush(entry.getKey(), entry.getValue());
            controller.getGraphicController().getPixelArt().getView().addUserToTable(entry.getKey(), entry.getValue().getColor());
        }
    }

    public void removeBrush(final String uniqueID) {
        this.brushManager.removeBrush(uniqueID);
        controller.getGraphicController().getPixelArt().getView().removeUserToTable(uniqueID);
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
