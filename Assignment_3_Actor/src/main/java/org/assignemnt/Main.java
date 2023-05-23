package org.assignemnt;

import akka.actor.typed.ActorSystem;
import org.assignemnt.GUI.Gui;
import org.assignemnt.actor.BootActor;
import org.assignemnt.actor.GuiActor;
import org.assignemnt.message.MsgBoot;
import org.assignemnt.message.MsgProtocol;
import org.assignemnt.model.Directory;
import org.assignemnt.utility.analyzer.SourceAnalyzer;
import org.assignemnt.utility.analyzer.SourceAnalyzerImpl;

public class Main {
    public static void main(String[] args) throws InterruptedException {

        Gui gui = new Gui();
        gui.setVisible(true);
    }
}