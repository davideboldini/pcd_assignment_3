package org.assignemnt.actor;

import akka.actor.typed.ActorRef;
import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;
import org.assignemnt.message.*;

import java.util.ArrayList;
import java.util.List;

public class BootActor extends AbstractBehavior<MsgProtocol> {

    List<ActorRef<MsgProtocol>> actors = new ArrayList<>();

    public BootActor(ActorContext<MsgProtocol> context) {
        super(context);
    }

    @Override
    public Receive<MsgProtocol> createReceive() {
        return newReceiveBuilder()
                .onMessage(MsgBoot.class, this::onBootMsg)
                .build();
    }

    private Behavior<MsgProtocol> onBootMsg(final MsgBoot msg){
        ActorRef<MsgProtocol> monitorActor = this.getContext().spawn(MonitorActor.create(), "monitor_actor");
        ActorRef<MsgProtocol> directoryActor = this.getContext().spawn(DirectoryActor.create(), "directory_actor");
        ActorRef<MsgProtocol> fileActor = this.getContext().spawn(FileActor.create(), "file_actor");

        actors.add(monitorActor);
        actors.add(directoryActor);
        actors.add(fileActor);

        monitorActor.tell(new MsgInit(msg.getMAXL(), msg.getNI()));
        directoryActor.tell(new MsgDirectory(msg.getStartDirectory(), fileActor, monitorActor));

        return this;
    }

    public static Behavior<MsgProtocol> create() {
        return Behaviors.setup(BootActor::new);
    }
}
