package org.assignemnt.message;

import akka.actor.typed.ActorRef;
import org.assignemnt.utility.Pair;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeSet;

public class MsgCompleteUpdate implements MsgProtocol{

    private final TreeSet<Pair<File,Long>> fileLengthTree;
    private final HashMap<Pair<Integer,Integer>, Integer> intervalMap;
    private final Map<String, ActorRef<MsgProtocol>> actorRefMap;

    public MsgCompleteUpdate(final TreeSet<Pair<File,Long>> fileLengthTree, final HashMap<Pair<Integer,Integer>, Integer> intervalMap,
                             final Map<String, ActorRef<MsgProtocol>> actorRefMap){
        this.fileLengthTree = new TreeSet<>(fileLengthTree);
        this.intervalMap = new HashMap<>(intervalMap);
        this.actorRefMap = actorRefMap;
    }

    public TreeSet<Pair<File, Long>> getFileLengthTree() {
        return this.fileLengthTree;
    }

    public HashMap<Pair<Integer, Integer>, Integer> getIntervalMap() {
        return this.intervalMap;
    }

    public Map<String, ActorRef<MsgProtocol>> getActorRefMap() {
        return this.actorRefMap;
    }
}
