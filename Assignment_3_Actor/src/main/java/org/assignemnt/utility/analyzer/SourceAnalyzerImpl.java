package org.assignemnt.utility.analyzer;

import akka.actor.typed.ActorSystem;
import org.assignemnt.GUI.Gui;
import org.assignemnt.actor.BootActor;
import org.assignemnt.actor.GuiActor;
import org.assignemnt.message.MsgBoot;
import org.assignemnt.message.MsgProtocol;
import org.assignemnt.message.MsgStop;
import org.assignemnt.model.Directory;

public class SourceAnalyzerImpl implements SourceAnalyzer{

    public static ActorSystem<MsgProtocol> guiActor;
    private ActorSystem<MsgProtocol> bootActor;

    public void analyzeSources(final Directory dir, final int MAXL, final int NI, final Gui gui){
        bootActor = ActorSystem.create(BootActor.create(), "boot_actor");
        guiActor = ActorSystem.create(GuiActor.create(gui), "gui_actor");
        bootActor.tell(new MsgBoot(dir, MAXL, NI));
    }

    public void stopActors(){
        bootActor.terminate();
        guiActor.terminate();
    }
}
