package org.assignemnt.message;

import akka.actor.typed.ActorRef;
import org.assignemnt.utility.Pair;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeSet;

public class MsgCompleteUpdate extends Msg implements MsgProtocol{

    private final TreeSet<Pair<File,Long>> fileLengthTree;
    private final HashMap<Pair<Integer,Integer>, Integer> intervalMap;

    public MsgCompleteUpdate(final TreeSet<Pair<File,Long>> fileLengthTree, final HashMap<Pair<Integer,Integer>, Integer> intervalMap,
                             final Map<String, ActorRef<MsgProtocol>> actorRefMap){
        super(actorRefMap);
        this.fileLengthTree = new TreeSet<>(fileLengthTree);
        this.intervalMap = new HashMap<>(intervalMap);
    }

    public TreeSet<Pair<File, Long>> getFileLengthTree() {
        return this.fileLengthTree;
    }

    public HashMap<Pair<Integer, Integer>, Integer> getIntervalMap() {
        return this.intervalMap;
    }

}
