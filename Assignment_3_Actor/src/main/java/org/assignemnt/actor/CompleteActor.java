package org.assignemnt.actor;

import akka.actor.typed.ActorRef;
import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;
import org.assignemnt.message.MsgComplete;
import org.assignemnt.message.MsgCompleteUpdate;
import org.assignemnt.message.MsgProtocol;
import org.assignemnt.utility.Pair;

import java.io.File;
import java.util.Map;
import java.util.TreeSet;
import java.util.concurrent.CompletableFuture;

public class CompleteActor extends AbstractBehavior<MsgProtocol> {

    private CompletableFuture<Pair<TreeSet<Pair<File,Long>>, Map<Pair<Integer,Integer>, Integer>>> finalResult;

    public CompleteActor(final ActorContext<MsgProtocol> context, final CompletableFuture<Pair<TreeSet<Pair<File,Long>>, Map<Pair<Integer,Integer>, Integer>>> finalResult) {
        super(context);
        this.finalResult = finalResult;
    }

    public CompleteActor(final ActorContext<MsgProtocol> context){
        super(context);
    }

    @Override
    public Receive<MsgProtocol> createReceive() {
        return newReceiveBuilder()
                .onMessage(MsgCompleteUpdate.class, this::onCompleteMsg)
                .build();
    }

    private Behavior<MsgProtocol> onCompleteMsg(MsgCompleteUpdate msg) {
        if (finalResult != null) {
            finalResult.complete(new Pair<>(msg.getFileLengthTree(), msg.getIntervalMap()));
            getContext().getSystem().terminate();
        }
        if (msg.getActorRefMap().containsKey("gui_actor")){
            ActorRef<MsgProtocol> guiActor = msg.getActorRefMap().get("gui_actor");
            guiActor.tell(new MsgComplete());
        }
        return this;
    }

    public static Behavior<MsgProtocol> create(CompletableFuture<Pair<TreeSet<Pair<File,Long>>, Map<Pair<Integer,Integer>, Integer>>> finalResult) {
        return Behaviors.setup(context -> new CompleteActor(context, finalResult));
    }

    public static Behavior<MsgProtocol> create(){
        return Behaviors.setup(CompleteActor::new);
    }
}
