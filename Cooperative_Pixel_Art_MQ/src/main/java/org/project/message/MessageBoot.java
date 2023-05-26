package org.project.message;

import org.project.utility.Pair;

public class MessageBoot extends MessagePosition{

    private String idSender;

    public MessageBoot(Pair<Integer, Integer> position, int colorBrush) {
        super(position, colorBrush);
    }

    @Override
    public String getIdSender() {
        return this.idSender;
    }

    @Override
    public void setIdSender(final String idSender) {
        this.idSender = idSender;
    }
}
