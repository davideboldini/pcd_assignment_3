package org.project.message;

import org.project.graphic.PixelGrid;

public class MessageBoot {

    private String idSender;
    private PixelGrid currentPixelGrid;

    public MessageBoot(final String idSender, final PixelGrid currentPixelGrid){
        this.idSender = idSender;
        this.currentPixelGrid = currentPixelGrid;
    }

    public PixelGrid getCurrentPixelGrid() {
        return this.currentPixelGrid;
    }

    public String getIdSender() {
        return this.idSender;
    }
}
