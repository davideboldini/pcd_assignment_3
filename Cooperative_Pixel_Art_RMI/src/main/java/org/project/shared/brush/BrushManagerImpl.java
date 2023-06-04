package org.project.shared.brush;

import org.project.utility.Pair;

import java.awt.*;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class BrushManagerImpl implements BrushManager, Serializable {

    private final Map<String, Brush> brushMap = new HashMap<>();

    public synchronized void addBrush(final String uniqueID, final Brush brush) {
        System.out.println(uniqueID + " si è' unito");
        brushMap.put(uniqueID, brush);
    }

    public synchronized void removeBrush(final String uniqueID) {
        System.out.println(uniqueID + " ha abbandonato");
        brushMap.remove(uniqueID);
    }

    public synchronized void updateBrushPosition(final String uniqueID, final Pair<Integer, Integer> position) {
        brushMap.get(uniqueID).updatePosition(position.getX(), position.getY());
    }

    public synchronized void updateBrushColor(final String uniqueID, final int color) {
        System.out.println(uniqueID + " ha cambiato colore in: " + color);
        brushMap.get(uniqueID).setColor(color);
    }

    public synchronized Map<String, Brush> getBrushMap() {
        return brushMap;
    }
}
