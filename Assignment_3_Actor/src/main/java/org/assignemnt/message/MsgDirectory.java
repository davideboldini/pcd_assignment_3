package org.assignemnt.message;

import akka.actor.typed.ActorRef;
import org.assignemnt.model.Directory;

import java.util.Map;

public class MsgDirectory implements MsgProtocol{

    private final Directory directory;
    private final Map<String, ActorRef<MsgProtocol>> actorRefMap;

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
