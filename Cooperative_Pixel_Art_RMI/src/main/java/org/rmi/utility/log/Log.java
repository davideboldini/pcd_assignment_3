package org.rmi.utility.log;

public interface Log {

    void joinedUserMex(final String uniqueID);
    void quitUserMex(final String uniqueID);
    void changeColorUserMex(final String uniqueID, final int color);
}
