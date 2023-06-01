package org.project.message;

import org.project.graphic.BrushManager;
import org.project.graphic.PixelGrid;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class MessageWelcome implements Serializable, MessageProtocol {

    private String idSender;
    private PixelGrid currentPixelGrid;
    private Map<String, BrushManager.Brush> brushMap;

    public MessageWelcome(final PixelGrid currentPixelGrid, final Map<String, BrushManager.Brush> brushMap){
        this.currentPixelGrid = currentPixelGrid;
        this.brushMap = new HashMap<>(brushMap);
    }

    public PixelGrid getCurrentPixelGrid() {
        return this.currentPixelGrid;
    }

    public Map<String, BrushManager.Brush> getBrushMap() {
        return this.brushMap;
    }

    public String getIdSender() {
        return this.idSender;
    }

    public void setIdSender(final String idSender) {
        this.idSender = idSender;
    }

    @Override
    public String toString() {
        return "MessageBoot{" +
                "idSender='" + idSender + '\'' +
                ", currentPixelGrid=" + currentPixelGrid +
                '}';
    }
}
