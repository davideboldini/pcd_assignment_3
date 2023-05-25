package org.project.message;

import org.project.utility.Pair;

public class MessagePosition {

    private String idSender;
    private Pair<Integer,Integer> position;
    private int colorBrush;

    public MessagePosition(final String idSender, final Pair<Integer, Integer> position, final int colorBrush) {
        this.idSender = idSender;
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
}
