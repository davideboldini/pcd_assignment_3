package org.assignemnt.message;

import akka.actor.typed.ActorRef;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MsgFile extends Msg implements MsgProtocol{

    private final List<File> fileList;

    public MsgFile(final List<File> fileList, final Map<String, ActorRef<MsgProtocol>> actorRefMap){
        super(actorRefMap);
        this.fileList = new ArrayList<>(fileList);
    }

    public List<File> getFileList(){
        return this.fileList;
    }
}
