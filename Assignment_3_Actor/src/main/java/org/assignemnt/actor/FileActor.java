package org.assignemnt.actor;

import akka.actor.typed.ActorRef;
import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;
import org.assignemnt.message.MsgFile;
import org.assignemnt.message.MsgFileLength;
import org.assignemnt.message.MsgProtocol;
import org.assignemnt.message.MsgStop;
import org.assignemnt.utility.Pair;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class FileActor extends AbstractBehavior<MsgProtocol> {


    public FileActor(ActorContext<MsgProtocol> context) {
        super(context);
    }

    @Override
    public Receive<MsgProtocol> createReceive() {
        return newReceiveBuilder()
                .onMessage(MsgFile.class, this::onMsgFile)
                .build();
    }

    private Behavior<MsgProtocol> onMsgFile(final MsgFile msg){
        //log(msg.getFileList().toString());

        ActorRef<MsgProtocol> monitorActor = msg.getMonitorActor();
        List<File> fileList = msg.getFileList();

        List<Pair<File, Long>> filePairList = new ArrayList<>();

        for (File f: fileList) {
            Long numRows = this.countNumRows(f);
            filePairList.add(new Pair<>(f, numRows));
        }

        monitorActor.tell(new MsgFileLength(filePairList));
        return this;
    }


    public static Behavior<MsgProtocol> create() {
        return Behaviors.setup(FileActor::new);
    }



    private Long countNumRows(final File file) {
        try {
            return Files.lines(Path.of(file.getPath())).count();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void log(String msg) {
        System.out.println("[File Actor] " + msg);
    }
}
