package org.assignemnt.message;

import akka.actor.typed.ActorRef;

import java.util.Map;

public class MsgComplete extends Msg implements MsgProtocol{

    public MsgComplete(Map<String, ActorRef<MsgProtocol>> actorRefMap) {
        super(actorRefMap);
    }
}
