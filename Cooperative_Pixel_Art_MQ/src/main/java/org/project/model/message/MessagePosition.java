package org.project.model.message;

import org.project.graphic.BrushManager;

public class MessagePosition extends Message {

    private final BrushManager.Brush brush;

    public MessagePosition(final BrushManager.Brush brush) {
        super();
        this.brush = brush;
    }

    public BrushManager.Brush getBrush() {
        return brush;
    }

    @Override
    public String toString() {
        return "MessagePosition{" +
                "idSender='" + super.getIdSender() + '\'' +
                ", brush=" + brush +
                '}';
    }
}
