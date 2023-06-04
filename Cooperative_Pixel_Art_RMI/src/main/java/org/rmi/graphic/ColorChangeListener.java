package org.rmi.graphic;

import java.rmi.RemoteException;

public interface ColorChangeListener {
    void colorChanged(int color) throws RemoteException;
}
