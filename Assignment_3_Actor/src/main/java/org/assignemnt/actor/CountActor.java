package org.assignemnt.actor;

import akka.actor.typed.ActorRef;
import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;
import org.assignemnt.message.MsgComplete;
import org.assignemnt.message.MsgDecrement;
import org.assignemnt.message.MsgIncrement;
import org.assignemnt.message.MsgProtocol;

import java.util.Map;

public class CountActor extends AbstractBehavior<MsgProtocol> {

    private int count = 0;

    public CountActor(ActorContext<MsgProtocol> context) {
        super(context);
    }

    @Override
    public Receive<MsgProtocol> createReceive() {
        return newReceiveBuilder()
                .onMessage(MsgIncrement.class, this::onMsgIncrement)
                .onMessage(MsgDecrement.class, this::onMsgDecrement)
                .build();
    }

    private Behavior<MsgProtocol> onMsgDecrement(final MsgDecrement msg) {
        this.count--;
        if (count == 0){
            Map<String, ActorRef<MsgProtocol>> actorRefMap = msg.getActorRefMap();
            ActorRef<MsgProtocol> monitorActor = actorRefMap.get("monitor_actor");
            monitorActor.tell(new MsgComplete(actorRefMap));
        }
        return this;
    }

    private Behavior<MsgProtocol> onMsgIncrement(final MsgIncrement msg) {
        this.count++;
        return this;
    }

    public static Behavior<MsgProtocol> create() {
        return Behaviors.setup(CountActor::new);
    }


}
