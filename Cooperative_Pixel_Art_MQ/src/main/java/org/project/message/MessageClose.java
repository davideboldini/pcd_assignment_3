package org.project.message;

public class MessageClose extends MessageBoot {

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
