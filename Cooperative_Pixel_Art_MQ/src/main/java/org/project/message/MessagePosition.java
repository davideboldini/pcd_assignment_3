package org.project.message;

import org.project.utility.Pair;

import java.io.Serializable;

public class MessagePosition implements Serializable {

    private String idSender;
    private final Pair<Integer,Integer> position;
    private final int colorBrush;

    public MessagePosition(final Pair<Integer, Integer> position, final int colorBrush) {
        this.position = position;
        this.colorBrush = colorBrush;
    }

    public String getIdSender() {
        return this.idSender;
    }

    public Pair<Integer, Integer> getPosition() {
        return this.position;
    }

    public int getColorBrush() {
        return this.colorBrush;
    }

    public void setIdSender(final String idSender) {
        this.idSender = idSender;
    }

    @Override
    public String toString() {
        return "MessagePosition{" +
                "idSender='" + idSender + '\'' +
                ", position=" + position +
                ", colorBrush=" + colorBrush +
                '}';
    }
}
