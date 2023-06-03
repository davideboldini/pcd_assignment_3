package org.project.model.message;


import org.project.shared.brush.Brush;
import org.project.shared.brush.BrushManager;

public class MessagePosition extends Message {

    private final Brush brush;

    public MessagePosition(final Brush brush) {
        super();
        this.brush = brush;
    }

    public Brush getBrush() {
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
