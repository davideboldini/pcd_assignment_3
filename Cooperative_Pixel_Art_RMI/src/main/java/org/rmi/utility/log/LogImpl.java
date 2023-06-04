package org.rmi.utility.log;

import java.text.SimpleDateFormat;
import java.util.Date;

public class LogImpl implements Log{

    private final SimpleDateFormat formatter;
    private final Date date;

    public LogImpl() {
        this.formatter = new SimpleDateFormat("HH:mm:ss");
        this.date = new Date();
    }

    public void joinedUserMex(final String uniqueID){
        System.out.println(formatter.format(date) + ": " + uniqueID + " si e' unito");
    }

    public void quitUserMex(final String uniqueID) {
        System.out.println(formatter.format(date) + ": " + uniqueID + " ha abbandonato");
    }

    public void changeColorUserMex(final String uniqueID, final int color) {
        System.out.println(formatter.format(date) + ": " + uniqueID + " ha cambiato colore in " + color);
    }
}
