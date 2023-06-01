package org.project.message;

import org.project.model.Cell;

import java.io.Serializable;

public class MessageClick implements Serializable, MessageProtocol {

    private String idSender;
    private final Cell cellClicked;

    public MessageClick(final Cell cellClicked){
        this.cellClicked = cellClicked;
    }

    public String getIdSender() {
        return this.idSender;
    }

    public Cell getCellClicked() {
        return this.cellClicked;
    }

    public void setIdSender(final String idSender) {
        this.idSender = idSender;
    }

    @Override
    public String toString() {
        return "MessageClick{" +
                "idSender='" + idSender + '\'' +
                ", cellClicked=" + cellClicked +
                '}';
    }
}
