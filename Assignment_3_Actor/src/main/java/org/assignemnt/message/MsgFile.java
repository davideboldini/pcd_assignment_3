package org.assignemnt.message;

import akka.actor.typed.ActorRef;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MsgFile implements MsgProtocol{

    private final List<File> fileList;
    private final Map<String, ActorRef<MsgProtocol>> actorRefMap;

    public MsgFile(final List<File> fileList, final Map<String, ActorRef<MsgProtocol>> actorRefMap){
        this.fileList = new ArrayList<>(fileList);
        this.actorRefMap = actorRefMap;
    }

    public List<File> getFileList(){
        return this.fileList;
    }

    public Map<String, ActorRef<MsgProtocol>> getActorRefMap() {
        return this.actorRefMap;
    }
}
