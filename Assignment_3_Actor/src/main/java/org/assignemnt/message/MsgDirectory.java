package org.assignemnt.message;

import akka.actor.typed.ActorRef;
import org.assignemnt.model.Directory;

import java.util.Map;

public class MsgDirectory extends Msg implements MsgProtocol{

    private final Directory directory;

    public MsgDirectory(final Directory directory, final Map<String, ActorRef<MsgProtocol>> actorRefMap){
        super(actorRefMap);
        this.directory = directory;
    }

    public Directory getDirectory() {
        return this.directory;
    }
}
