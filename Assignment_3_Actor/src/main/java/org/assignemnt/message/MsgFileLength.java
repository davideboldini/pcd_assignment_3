package org.assignemnt.message;

import akka.actor.typed.ActorRef;
import org.assignemnt.utility.Pair;

import java.io.File;
import java.util.List;
import java.util.Map;

public class MsgFileLength implements MsgProtocol{

    private final List<Pair<File, Long>> fileList;
    private final Map<String, ActorRef<MsgProtocol>> actorRefMap;

    public MsgFileLength(final List<Pair<File,Long>> fileList, final Map<String, ActorRef<MsgProtocol>> actorRefMap){
        this.fileList = fileList;
        this.actorRefMap = actorRefMap;
    }

    public List<Pair<File,Long>> getFile() {
        return this.fileList;
    }

    public Map<String, ActorRef<MsgProtocol>> getActorRefMap() {
        return this.actorRefMap;
    }
}
