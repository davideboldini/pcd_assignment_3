package org.assignemnt.actor;

import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;
import org.assignemnt.GUI.Gui;
import org.assignemnt.message.MsgGui;
import org.assignemnt.message.MsgProtocol;
import org.assignemnt.utility.Pair;

public class GuiActor extends AbstractBehavior<MsgProtocol> {

    private final Gui gui;

    public GuiActor(ActorContext<MsgProtocol> context, final Gui gui) {
        super(context);
        this.gui = gui;
    }

    @Override
    public Receive<MsgProtocol> createReceive() {
        return newReceiveBuilder()
                .onMessage(MsgGui.class, this::onGuiMsg)
                .build();
    }

    private Behavior<MsgProtocol> onGuiMsg(MsgGui msg) {
        this.gui.updateGui(new Pair<>(msg.getFileLengthTree(), msg.getIntervalMap()));
        return this;
    }

    public static Behavior<MsgProtocol> create(final Gui gui){
        return Behaviors.setup(context -> new GuiActor(context, gui));
    }


}
