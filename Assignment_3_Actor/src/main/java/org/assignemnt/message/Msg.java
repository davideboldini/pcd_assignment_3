package org.assignemnt.message;

import akka.actor.typed.ActorRef;

import java.util.Map;

public class Msg implements MsgProtocol{

    private final Map<String, ActorRef<MsgProtocol>> actorRefMap;

    public Msg(final Map<String, ActorRef<MsgProtocol>> actorRefMap){
        this.actorRefMap = actorRefMap;
    }

    public Map<String, ActorRef<MsgProtocol>> getActorRefMap() {
        return this.actorRefMap;
    }
}
