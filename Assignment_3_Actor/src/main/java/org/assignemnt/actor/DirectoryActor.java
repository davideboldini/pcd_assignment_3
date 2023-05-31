package org.assignemnt.actor;

import akka.actor.typed.ActorRef;
import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;
import org.assignemnt.message.MsgDirectory;
import org.assignemnt.message.MsgFile;
import org.assignemnt.message.MsgIncrement;
import org.assignemnt.message.MsgProtocol;
import org.assignemnt.model.Directory;

import java.io.File;
import java.util.List;
import java.util.Map;

public class DirectoryActor extends AbstractBehavior<MsgProtocol> {

    public DirectoryActor(ActorContext<MsgProtocol> context) {
        super(context);
    }

    @Override
    public Receive<MsgProtocol> createReceive() {
        return newReceiveBuilder()
                .onMessage(MsgDirectory.class, this::onDirectoryMsg)
                .build();
    }

    private Behavior<MsgProtocol> onDirectoryMsg(MsgDirectory msg){

        Directory directory = msg.getDirectory();

        Map<String, ActorRef<MsgProtocol>> actorRefMap = msg.getActorRefMap();
        ActorRef<MsgProtocol> fileActor = actorRefMap.get("file_actor");
        ActorRef<MsgProtocol> countActor = actorRefMap.get("count_actor");

        countActor.tell(new MsgIncrement());

        List<File> fileList = directory.getJavaFileList();

        fileActor.tell(new MsgFile(fileList, actorRefMap));

        for (Directory d: directory.getDirectoryList()) {
            this.getContext().getSelf().tell(new MsgDirectory(d, actorRefMap));
        }

        return this;
    }


    public static Behavior<MsgProtocol> create() {
        return Behaviors.setup(DirectoryActor::new);
    }

    private void log(String msg) {
        System.out.println("[Directory Actor] " + msg);
    }


}
