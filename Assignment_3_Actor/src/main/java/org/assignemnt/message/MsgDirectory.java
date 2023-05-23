package org.assignemnt.message;

import akka.actor.typed.ActorRef;
import org.assignemnt.model.Directory;

public class MsgDirectory implements MsgProtocol{

    private Directory directory;
    private ActorRef<MsgProtocol> fileActor;
    private ActorRef<MsgProtocol> monitorActor;

    public MsgDirectory(final Directory directory, final ActorRef<MsgProtocol> fileActor, final ActorRef<MsgProtocol> monitorActor){
        this.directory = directory;
        this.fileActor = fileActor;
        this.monitorActor = monitorActor;
    }

    public Directory getDirectory() {
        return this.directory;
    }

    public ActorRef<MsgProtocol> getFileActor() {
        return this.fileActor;
    }

    public ActorRef<MsgProtocol> getMonitorActor() {
        return this.monitorActor;
    }
}
