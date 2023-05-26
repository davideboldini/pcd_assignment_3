package org.assignemnt.message;

public class MsgInit implements MsgProtocol{

    private final int MAXL;
    private final int NI;

    public MsgInit(final int MAXL, final int NI){
        this.MAXL = MAXL;
        this.NI = NI;
    }

    public int getMAXL() {
        return this.MAXL;
    }

    public int getNI() {
        return this.NI;
    }
}
