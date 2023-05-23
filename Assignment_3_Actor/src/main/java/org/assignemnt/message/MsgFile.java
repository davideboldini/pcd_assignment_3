package org.assignemnt.message;

import akka.actor.typed.ActorRef;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MsgFile implements MsgProtocol{

    private List<File> fileList;
    private ActorRef<MsgProtocol> monitorActor;

    public MsgFile(final List<File> fileList, final ActorRef<MsgProtocol> monitorActor){
        this.fileList = new ArrayList<>(fileList);
        this.monitorActor = monitorActor;
    }

    public List<File> getFileList(){
        return this.fileList;
    }

    public ActorRef<MsgProtocol> getMonitorActor() {
        return this.monitorActor;
    }
}
