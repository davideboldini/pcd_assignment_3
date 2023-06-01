package org.project.controller;

import org.project.graphic.BrushManager;
import org.project.utility.Pair;

import java.util.Map;

public class BrushController implements Runnable{


    private BrushManager brushManager;

    public BrushController() {
        this.brushManager = new BrushManager();
    }

    public void addBrush(final String uniqueID, final BrushManager.Brush brush) {
        this.brushManager.addBrush(uniqueID, brush);
    }

    public void addBrushMap(final Map<String, BrushManager.Brush> brushMap){
        for (Map.Entry<String, BrushManager.Brush> entry : brushMap.entrySet()) {
            brushManager.addBrush(entry.getKey(), entry.getValue());
        }
    }

    public void removeBrush(final String uniqueID) {
        this.brushManager.removeBrush(uniqueID);
    }

    public void updateBrushPosition(final String uniqueID, final Pair<Integer, Integer> position, final int color) {
        this.brushManager.getBrushMap().get(uniqueID).updatePosition(position.getX(), position.getY());
        if (this.brushManager.getBrushMap().get(uniqueID).getColor() != color){
            this.updateBrushColor(uniqueID, color);
        }
    }

    public void updateBrushColor(final String uniqueID, final int color) {
        this.brushManager.getBrushMap().get(uniqueID).setColor(color);
    }

    public BrushManager getBrushManager() {
        return this.brushManager;
    }

    @Override
    public void run() {
        while (true){}
    }
}
