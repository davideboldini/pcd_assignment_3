package org.project.model.message;

public class MessageClose extends Message {

    public MessageClose(){
        super();
    }

    @Override
    public String toString() {
        return "MessageClose{" +
                "idSender='" + super.getIdSender() + '\'' +
                '}';
    }
}
