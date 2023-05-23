package org.assignemnt.message;

import org.assignemnt.utility.Pair;

import java.io.File;
import java.util.HashMap;
import java.util.TreeSet;

public class MsgGui implements MsgProtocol{

    private TreeSet<Pair<File,Long>> fileLengthTree;
    private HashMap<Pair<Integer,Integer>, Integer> intervalMap;

    public MsgGui(final TreeSet<Pair<File,Long>> fileLengthTree, final HashMap<Pair<Integer,Integer>, Integer> intervalMap){
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
