package org.rmi.model.message.object;

import org.rmi.shared.brush.Brush;

public class MessageConnection extends Message{

    private final Brush brush;

    public MessageConnection(final Brush brush) {
        super();
        this.brush = brush;
    }

    public Brush getBrush() {
        return brush;
    }

    @Override
    public String toString() {
        return "MessageConnection{" +
                "brush=" + brush +
                "} " + super.toString();
    }
}
