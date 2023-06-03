package org.project.shared.brush;

import org.project.utility.Pair;

import java.awt.*;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class BrushManagerImpl implements BrushManager, Serializable {

    private final Map<String, Brush> brushMap = new HashMap<>();

    public synchronized void addBrush(final String uniqueID, final Brush brush) {
        brushMap.put(uniqueID, brush);
    }

    public synchronized void removeBrush(final String uniqueID) {
        brushMap.remove(uniqueID);
    }

    public synchronized void updateBrushPosition(final String uniqueID, final Pair<Integer, Integer> position) {
        brushMap.get(uniqueID).updatePosition(position.getX(), position.getY());
    }

    public synchronized void updateBrushColor(final String uniqueID, final int color) {
        brushMap.get(uniqueID).setColor(color);
    }

    public synchronized Map<String, Brush> getBrushMap() {
        return brushMap;
    }
}
