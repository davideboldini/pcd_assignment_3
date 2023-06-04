package org.project.shared.log;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface Log extends Remote {

    List<String> getLogList() throws RemoteException;
    void addString(final String string) throws RemoteException;
    void joinedUserMex(final String uniqueID) throws RemoteException;
    void quitUserMex(final String uniqueID) throws RemoteException;
    void changeColorUserMex(final String uniqueID, final int color) throws RemoteException;
}
