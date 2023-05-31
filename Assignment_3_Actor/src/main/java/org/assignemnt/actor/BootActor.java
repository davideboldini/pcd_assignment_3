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

import java.util.HashMap;
import java.util.Map;

public class BootActor extends AbstractBehavior<MsgProtocol> {

    private final Map<String, ActorRef<MsgProtocol>> actorRefMap;

    public BootActor(ActorContext<MsgProtocol> context) {
        super(context);
        this.actorRefMap = new HashMap<>();
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
        ActorRef<MsgProtocol> countActor = this.getContext().spawn(CountActor.create(), "count_actor");

        ActorRef<MsgProtocol> completeActor;
        if (msg.getFinalResult() != null) {
            completeActor = this.getContext().spawn(CompleteActor.create(msg.getFinalResult()), "complete_actor");
        } else {
            completeActor = this.getContext().spawn(CompleteActor.create(), "complete_actor");
        }
        actorRefMap.put("complete_actor", completeActor);

        if (msg.getGui() != null){
            ActorRef<MsgProtocol> guiActor = this.getContext().spawn(GuiActor.create(msg.getGui()), "gui_actor");
            actorRefMap.put("gui_actor", guiActor);
        }

        actorRefMap.put("monitor_actor", monitorActor);
        actorRefMap.put("directory_actor", directoryActor);
        actorRefMap.put("file_actor", fileActor);
        actorRefMap.put("count_actor", countActor);

        monitorActor.tell(new MsgInit(msg.getMAXL(), msg.getNI()));
        directoryActor.tell(new MsgDirectory(msg.getStartDirectory(), actorRefMap));

        return this;
    }


    public static Behavior<MsgProtocol> create() {
        return Behaviors.setup(BootActor::new);
    }
}
