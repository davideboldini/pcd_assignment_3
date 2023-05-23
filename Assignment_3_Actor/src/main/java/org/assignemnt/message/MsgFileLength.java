package org.assignemnt.message;

import org.assignemnt.utility.Pair;

import java.io.File;
import java.util.List;

public class MsgFileLength implements MsgProtocol{

    private List<Pair<File, Long>> fileList;

    public MsgFileLength(final List<Pair<File,Long>> fileList){
        this.fileList = fileList;
    }

    public List<Pair<File,Long>> getFile() {
        return this.fileList;
    }


}
