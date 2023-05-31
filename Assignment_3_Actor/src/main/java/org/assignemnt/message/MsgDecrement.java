package org.assignemnt.message;

import akka.actor.typed.ActorRef;

import java.util.Map;

public class MsgDecrement extends Msg implements MsgProtocol{

    public MsgDecrement(final Map<String, ActorRef<MsgProtocol>> actorRefMap){
        super(actorRefMap);
    }
}
