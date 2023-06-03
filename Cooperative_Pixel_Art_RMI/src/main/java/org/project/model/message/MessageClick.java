package org.project.model.message;

import org.project.model.Cell;


public class MessageClick extends Message {

    private final Cell cellClicked;

    public MessageClick(final Cell cellClicked){
        super();
        this.cellClicked = cellClicked;
    }

    public Cell getCellClicked() {
        return this.cellClicked;
    }

    @Override
    public String toString() {
        return "MessageClick{" +
                "idSender='" + super.getIdSender() + '\'' +
                ", cellClicked=" + cellClicked +
                '}';
    }
}
