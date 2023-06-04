package org.project.model.message;


import org.project.shared.grid.PixelGridImpl;
import org.project.shared.brush.Brush;

import java.util.HashMap;
import java.util.Map;

public class MessageWelcome extends Message {

    private final PixelGridImpl currentPixelGrid;
    private final Map<String, Brush> brushMap;

    public MessageWelcome(final PixelGridImpl currentPixelGrid, final Map<String, Brush> brushMap){
        super();
        this.currentPixelGrid = currentPixelGrid;
        this.brushMap = new HashMap<>(brushMap);
    }

    public PixelGridImpl getCurrentPixelGrid() {
        return this.currentPixelGrid;
    }

    public Map<String, Brush> getBrushMap() {
        return this.brushMap;
    }

    @Override
    public String toString() {
        return "MessageBoot{" +
                "idSender='" + super.getIdSender() + '\'' +
                ", currentPixelGrid=" + currentPixelGrid +
                '}';
    }
}
