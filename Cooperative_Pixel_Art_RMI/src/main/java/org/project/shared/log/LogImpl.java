package org.project.shared.log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class LogImpl implements Log{

    private SimpleDateFormat formatter;
    private Date date;
    private final List<String> logList;

    public LogImpl() {
        this.formatter = new SimpleDateFormat("HH:mm:ss");
        this.date = new Date();
        this.logList = new ArrayList<>();
    }

    public List<String> getLogList() {
        return logList;
    }

    public void addString(final String string) {
        this.logList.add(string);
    }

    public synchronized void joinedUserMex(final String uniqueID){
        this.logList.add(formatter.format(date) + ": " + uniqueID + "si e' unito");
    }

    public synchronized void quitUserMex(final String uniqueID) {
        this.logList.add(formatter.format(date) + ": " + uniqueID + "ha abbandonato");
    }

    public synchronized void changeColorUserMex(final String uniqueID, final int color) {
        this.logList.add(formatter.format(date) + ": " + uniqueID + " ha cambiato colore in " + color);
    }
}
