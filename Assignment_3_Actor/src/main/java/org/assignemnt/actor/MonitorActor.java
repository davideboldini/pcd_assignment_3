package org.assignemnt.actor;

import akka.actor.typed.ActorRef;
import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;
import org.assignemnt.message.*;
import org.assignemnt.utility.Pair;

import java.io.File;
import java.util.*;

public class MonitorActor extends AbstractBehavior<MsgProtocol> {

    private TreeSet<Pair<File,Long>> fileLengthTree;
    private HashMap<Pair<Integer,Integer>, Integer> intervalMap;

    public MonitorActor(ActorContext<MsgProtocol> context) {
        super(context);
    }

    @Override
    public Receive<MsgProtocol> createReceive() {
        return newReceiveBuilder()
                .onMessage(MsgInit.class, this::onMsgInit)
                .onMessage(MsgFileLength.class, this::onMsgFile)
                .onMessage(MsgComplete.class, this::onMsgComplete)
                .build();
    }



    private Behavior<MsgProtocol> onMsgInit(final MsgInit msg){
        log("MAXL: " + msg.getMAXL() + " NI: " + msg.getNI());
        int MAXL = msg.getMAXL();
        int NI = msg.getNI();

        this.initMap(MAXL, NI);
        this.initTreeSet();

        return this;
    }

    private Behavior<MsgProtocol> onMsgFile(final MsgFileLength msg){

        List<Pair<File, Long>> fileList = msg.getFile();
        Map<String, ActorRef<MsgProtocol>> actorRefMap = msg.getActorRefMap();

        for (Pair<File, Long> pair: fileList) {
            this.fileLengthTree.add(new Pair<>(pair.getX(), pair.getY()));
            this.addMap(pair.getY());
        }

        actorRefMap.get("count_actor").tell(new MsgDecrement(actorRefMap));

        if (actorRefMap.get("gui_actor") != null) {
            actorRefMap.get("gui_actor").tell(new MsgCompleteUpdate(this.fileLengthTree, this.intervalMap, msg.getActorRefMap()));
        }

        return this;
    }

    private Behavior<MsgProtocol> onMsgComplete(final MsgComplete msg) {
        Map<String, ActorRef<MsgProtocol>> actorRefMap = msg.getActorRefMap();
        ActorRef<MsgProtocol> completeActor = actorRefMap.get("complete_actor");
        completeActor.tell(new MsgCompleteUpdate(this.fileLengthTree, this.intervalMap, msg.getActorRefMap()));
        return this;
    }


    public static Behavior<MsgProtocol> create() {
        return Behaviors.setup(MonitorActor::new);
    }

    private void log(String msg) {
        System.out.println("[Monitor Actor] " + msg);
    }



    private void initMap(final int MAXL, final int NI){
        this.intervalMap = new LinkedHashMap<>();

        int intervalSize = MAXL;

        if (NI > 1) {
            intervalSize = MAXL / (NI - 1);
        } else {
            this.intervalMap.put(new Pair<>(0, MAXL), 0);
            return;
        }

        for (int i = 0; i < (NI - 1); i++){
            if ( ((i + 1) * intervalSize)-1 != (MAXL - 1) && i == (NI - 1)-1){
                this.intervalMap.put(new Pair<>(i * intervalSize, (MAXL - 1)), 0);
            }else {
                this.intervalMap.put(new Pair<>(i * intervalSize, ((i + 1) * intervalSize)-1), 0);
            }
        }

        this.intervalMap.put(new Pair<>(MAXL, -1), 0);
    }

    private void initTreeSet(){
        this.fileLengthTree = new TreeSet<>((o1, o2) -> {
            int countCompare = o2.getY().compareTo(o1.getY());
            if (countCompare == 0){
                return o2.getX().compareTo(o1.getX());
            }
            return countCompare;
        });
    }

    private void addMap(final Long numRows){
        this.intervalMap.keySet().stream().filter(interval -> numRows < interval.getY() || (numRows >= interval.getX() && interval.getY().equals(-1))).findFirst().ifPresent(interval -> intervalMap.put(interval, intervalMap.get(interval) + 1));
    }


}
