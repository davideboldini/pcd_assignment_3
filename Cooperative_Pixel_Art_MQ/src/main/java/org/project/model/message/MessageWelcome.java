package org.project.model.message;

import org.project.graphic.BrushManager;
import org.project.graphic.PixelGrid;

import java.util.HashMap;
import java.util.Map;

public class MessageWelcome extends Message {

    private final PixelGrid currentPixelGrid;
    private final Map<String, BrushManager.Brush> brushMap;

    public MessageWelcome(final PixelGrid currentPixelGrid, final Map<String, BrushManager.Brush> brushMap){
        super();
        this.currentPixelGrid = currentPixelGrid;
        this.brushMap = new HashMap<>(brushMap);
    }

    public PixelGrid getCurrentPixelGrid() {
        return this.currentPixelGrid;
    }

    public Map<String, BrushManager.Brush> getBrushMap() {
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
