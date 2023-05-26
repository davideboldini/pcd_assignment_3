package org.assignemnt.message;

import akka.actor.typed.ActorSystem;
import org.assignemnt.GUI.Gui;
import org.assignemnt.model.Directory;
import org.assignemnt.utility.Pair;

import java.io.File;
import java.util.Map;
import java.util.TreeSet;
import java.util.concurrent.CompletableFuture;

public class MsgBoot implements MsgProtocol{

    private final Directory startDirectory;
    private final int MAXL;
    private final int NI;
    private CompletableFuture<Pair<TreeSet<Pair<File,Long>>, Map<Pair<Integer,Integer>, Integer>>> finalResult;
    private Gui gui;

    public MsgBoot(final Directory startDirectory, final int MAXL, final int NI){
        this.startDirectory = startDirectory;
        this.MAXL = MAXL;
        this.NI = NI;
    }

    public MsgBoot(final Directory startDirectory, final int MAXL, final int NI,
                   final CompletableFuture<Pair<TreeSet<Pair<File,Long>>, Map<Pair<Integer,Integer>, Integer>>> finalResult){
        this.startDirectory = startDirectory;
        this.MAXL = MAXL;
        this.NI = NI;
        this.finalResult = finalResult;
    }

    public MsgBoot(final Directory startDirectory, final int MAXL, final int NI, final Gui gui){
        this.startDirectory = startDirectory;
        this.MAXL = MAXL;
        this.NI = NI;
        this.gui = gui;
    }

    public Directory getStartDirectory() {
        return this.startDirectory;
    }

    public int getMAXL() {
        return this.MAXL;
    }

    public int getNI() {
        return this.NI;
    }

    public CompletableFuture<Pair<TreeSet<Pair<File, Long>>, Map<Pair<Integer, Integer>, Integer>>> getFinalResult() {
        return this.finalResult;
    }

    public Gui getGui() {
        return this.gui;
    }
}
