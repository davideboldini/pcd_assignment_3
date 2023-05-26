package org.assignemnt.utility.analyzer;

import akka.actor.typed.ActorRef;
import akka.actor.typed.ActorSystem;
import org.assignemnt.GUI.Gui;
import org.assignemnt.actor.BootActor;
import org.assignemnt.actor.CompleteActor;
import org.assignemnt.actor.GuiActor;
import org.assignemnt.message.MsgBoot;
import org.assignemnt.message.MsgProtocol;
import org.assignemnt.model.Directory;
import org.assignemnt.utility.Pair;

import java.io.File;
import java.util.Map;
import java.util.TreeSet;
import java.util.concurrent.CompletableFuture;

public class SourceAnalyzerImpl implements SourceAnalyzer{

    private ActorSystem<MsgProtocol> bootSystem;
    public static int numMsg = 0;


    public CompletableFuture<Pair<TreeSet<Pair<File,Long>>, Map<Pair<Integer,Integer>, Integer>>> getReport(final Directory dir,
                                                                                                            final int MAXL, final int NI) {

        CompletableFuture<Pair<TreeSet<Pair<File,Long>>, Map<Pair<Integer,Integer>, Integer>>> finalResult = new CompletableFuture<>();
        bootSystem = ActorSystem.create(BootActor.create(), "boot_actor");
        bootSystem.tell(new MsgBoot(dir, MAXL, NI, finalResult));

        return finalResult;

    }

    public void analyzeSources(final Directory dir, final int MAXL, final int NI, final Gui gui){
        bootSystem = ActorSystem.create(BootActor.create(), "boot_actor");
        bootSystem.tell(new MsgBoot(dir, MAXL, NI, gui));

    }

    public void stopActors(){
        bootSystem.terminate();
    }

}
