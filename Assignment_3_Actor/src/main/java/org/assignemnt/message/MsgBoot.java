package org.assignemnt.message;

import akka.actor.typed.ActorSystem;
import org.assignemnt.model.Directory;

public class MsgBoot implements MsgProtocol{

    private Directory startDirectory;
    private int MAXL;
    private int NI;

    public MsgBoot(final Directory startDirectory, final int MAXL, final int NI){
        this.startDirectory = startDirectory;
        this.MAXL = MAXL;
        this.NI = NI;
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
}
