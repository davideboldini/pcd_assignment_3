package org.project.message;

import org.project.utility.Pair;

import java.io.Serializable;

public class MessageBoot implements Serializable, MessageProtocol {

    private String idSender;

    public String getIdSender() {
        return this.idSender;
    }


    public void setIdSender(final String idSender) {
        this.idSender = idSender;
    }

    @Override
    public String toString() {
        return "MessageBoot{" +
                "idSender='" + idSender + '\'' +
                '}';
    }
}
