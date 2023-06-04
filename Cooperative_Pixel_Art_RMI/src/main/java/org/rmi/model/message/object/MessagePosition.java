package org.rmi.model.message.object;

import org.rmi.utility.Pair;

public class MessagePosition extends Message {

    private final Pair<Integer, Integer> position;

    public MessagePosition(final Pair<Integer, Integer> position) {
        super();
        this.position = position;
    }

    public Pair<Integer, Integer> getPosition() {
        return position;
    }

    @Override
    public String toString() {
        return "MessagePosition{" +
                "idSender='" + super.getIdSender() + '\'' +
                ", position=" + position +
                '}';
    }
}
