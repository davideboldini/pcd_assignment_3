package org.assignemnt.message;

import java.io.File;

public class MsgFileLength implements MsgProtocol{

    private File file;
    private Long numRows;

    public MsgFileLength(final File file, final Long numRows){
        this.file = file;
        this.numRows = numRows;
    }

    public File getFile() {
        return this.file;
    }

    public Long getNumRows() {
        return this.numRows;
    }
}
