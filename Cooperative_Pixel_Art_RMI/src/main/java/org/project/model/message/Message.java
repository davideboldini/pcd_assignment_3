package org.project.model.message;

import java.io.Serializable;

public class Message implements MessageProtocol, Serializable {

    private String idSender;

    @Override
    public String getIdSender() {
        return idSender;
    }

    @Override
    public void setIdSender(final String idSender) {
        this.idSender = idSender;
    }
}
