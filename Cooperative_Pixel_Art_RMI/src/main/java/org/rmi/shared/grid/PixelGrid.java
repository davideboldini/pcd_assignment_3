package org.rmi.shared.grid;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface PixelGrid extends Remote {

    void clear() throws RemoteException;
    void initGrid() throws RemoteException;
    void set(final int x, final int y, final int color) throws RemoteException;
    int get(int x, int y) throws RemoteException;
    int getNumRows() throws RemoteException;
    int getNumColumns() throws RemoteException;
}
