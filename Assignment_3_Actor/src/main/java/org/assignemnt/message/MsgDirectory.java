package org.assignemnt.message;

import akka.actor.typed.ActorRef;
import org.assignemnt.model.Directory;

import java.util.Map;

public class MsgDirectory implements MsgProtocol{

    private Directory directory;
    private Map<String, ActorRef<MsgProtocol>> actorRefMap;

    public MsgDirectory(final Directory directory, final Map<String, ActorRef<MsgProtocol>> actorRefMap){
        this.directory = directory;
        this.actorRefMap = actorRefMap;
    }

    public Directory getDirectory() {
        return this.directory;
    }

    public Map<String, ActorRef<MsgProtocol>> getActorRefMap() {
        return this.actorRefMap;
    }
}
