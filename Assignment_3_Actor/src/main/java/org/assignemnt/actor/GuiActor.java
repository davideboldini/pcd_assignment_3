package org.assignemnt.actor;

import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;
import org.assignemnt.GUI.Gui;
import org.assignemnt.message.MsgComplete;
import org.assignemnt.message.MsgCompleteUpdate;
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
                .onMessage(MsgCompleteUpdate.class, this::onGuiMsg)
                .onMessage(MsgComplete.class, this::onCompleteMsg)
                .build();
    }

    private Behavior<MsgProtocol> onCompleteMsg(final MsgComplete msg) {
        this.gui.setEndGui();
        getContext().getSystem().terminate();
        return this;
    }

    private Behavior<MsgProtocol> onGuiMsg(final MsgCompleteUpdate msg) {
        this.gui.updateGui(new Pair<>(msg.getFileLengthTree(), msg.getIntervalMap()));
        return this;
    }

    public static Behavior<MsgProtocol> create(final Gui gui){
        return Behaviors.setup(context -> new GuiActor(context, gui));
    }

}
