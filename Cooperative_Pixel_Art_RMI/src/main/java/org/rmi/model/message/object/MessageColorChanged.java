package org.rmi.model.message.object;

public class MessageColorChanged extends Message{

    private final int color;

    public MessageColorChanged(final int color) {
        super();
        this.color = color;
    }

    public int getColor() {
        return color;
    }

    @Override
    public String toString() {
        return "MessageColorChanged{" +
                "color=" + color +
                "} " + super.toString();
    }
}
