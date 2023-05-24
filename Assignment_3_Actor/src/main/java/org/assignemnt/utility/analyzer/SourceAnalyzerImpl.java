package org.assignemnt.utility.analyzer;

import akka.actor.typed.ActorSystem;
import akka.actor.typed.MailboxSelector;
import com.typesafe.config.ConfigFactory;
import org.assignemnt.GUI.Gui;
import org.assignemnt.actor.BootActor;
import org.assignemnt.actor.GuiActor;
import org.assignemnt.message.MsgBoot;
import org.assignemnt.message.MsgProtocol;
import org.assignemnt.model.Directory;

public class SourceAnalyzerImpl implements SourceAnalyzer{

    public static ActorSystem<MsgProtocol> guiSystem;
    private ActorSystem<MsgProtocol> bootActor;
    public static int numMsg = 0;

    public void analyzeSources(final Directory dir, final int MAXL, final int NI, final Gui gui){
        bootActor = ActorSystem.create(BootActor.create(), "boot_actor");
        guiSystem = ActorSystem.create(GuiActor.create(gui), "gui_actor");
        bootActor.tell(new MsgBoot(dir, MAXL, NI));
    }

    public void stopActors(){
        bootActor.terminate();
        guiSystem.terminate();
    }

}
