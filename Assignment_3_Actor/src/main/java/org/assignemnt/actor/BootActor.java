package org.assignemnt.actor;

import akka.actor.typed.ActorRef;
import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;
import org.assignemnt.message.MsgBoot;
import org.assignemnt.message.MsgDirectory;
import org.assignemnt.message.MsgInit;
import org.assignemnt.message.MsgProtocol;

public class BootActor extends AbstractBehavior<MsgBoot> {


    public BootActor(ActorContext<MsgBoot> context) {
        super(context);
    }

    @Override
    public Receive<MsgBoot> createReceive() {
        return newReceiveBuilder()
                .onMessage(MsgBoot.class, this::onBootMsg)
                .build();
    }

    private Behavior<MsgBoot> onBootMsg(MsgBoot msg){
        ActorRef<MsgProtocol> monitorActor = this.getContext().spawn(MonitorActor.create(), "monitor_actor");
        ActorRef<MsgProtocol> directoryActor = this.getContext().spawn(DirectoryActor.create(), "directory_actor");
        ActorRef<MsgProtocol> fileActor = this.getContext().spawn(FileActor.create(), "file_actor");

        monitorActor.tell(new MsgInit(msg.getMAXL(), msg.getNI()));
        directoryActor.tell(new MsgDirectory(msg.getStartDirectory(), fileActor, monitorActor));

        return this;
    }

    public static Behavior<MsgBoot> create() {
        return Behaviors.setup(BootActor::new);
    }
}
