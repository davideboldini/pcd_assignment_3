package org.assignemnt.message;

import akka.actor.typed.ActorRef;
import org.assignemnt.utility.Pair;

import java.io.File;
import java.util.List;
import java.util.Map;

public class MsgFileLength extends Msg implements MsgProtocol{

    private final List<Pair<File, Long>> fileList;

    public MsgFileLength(final List<Pair<File,Long>> fileList, final Map<String, ActorRef<MsgProtocol>> actorRefMap){
        super(actorRefMap);
        this.fileList = fileList;
    }

    public List<Pair<File,Long>> getFile() {
        return this.fileList;
    }
}
