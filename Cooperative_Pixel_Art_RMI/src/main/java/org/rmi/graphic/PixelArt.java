package org.rmi.graphic;

import org.rmi.shared.grid.PixelGrid;

import java.rmi.RemoteException;

public interface PixelArt {

    void initPixelArt(final PixelGrid grid) throws RemoteException;
    void keepRefresh();


}
