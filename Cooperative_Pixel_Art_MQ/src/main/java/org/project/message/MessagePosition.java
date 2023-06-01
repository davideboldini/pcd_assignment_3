package org.project.message;

import org.project.graphic.BrushManager;
import org.project.utility.Pair;

import java.io.Serializable;

public class MessagePosition implements Serializable, MessageProtocol {

    private String idSender;
    private BrushManager.Brush brush;

    public MessagePosition(final BrushManager.Brush brush) {
        this.brush = brush;
    }

    public String getIdSender() {
        return this.idSender;
    }

    public void setIdSender(final String idSender) {
        this.idSender = idSender;
    }

    public BrushManager.Brush getBrush() {
        return brush;
    }

    @Override
    public String toString() {
        return "MessagePosition{" +
                "idSender='" + idSender + '\'' +
                ", brush=" + brush +
                '}';
    }
}
