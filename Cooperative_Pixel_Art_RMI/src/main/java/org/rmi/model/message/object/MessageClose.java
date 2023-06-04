package org.rmi.model.message.object;

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
