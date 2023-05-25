package org.project.message;

import org.project.model.Cell;

public class MessageClick {

    private String idSender;
    private Cell cellClicked;

    public MessageClick(final String idSender, final Cell cellClicked){
        this.idSender = idSender;
        this.cellClicked = cellClicked;
    }

    public String getIdSender() {
        return this.idSender;
    }

    public Cell getCellClicked() {
        return this.cellClicked;
    }
}
