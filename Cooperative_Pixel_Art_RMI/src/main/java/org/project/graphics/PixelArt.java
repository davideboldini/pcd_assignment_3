package org.project.graphics;

import org.project.shared.PixelGrid;

import java.rmi.RemoteException;

public interface PixelArt {

    void initPixelArt(final PixelGrid grid) throws RemoteException;
    void keepRefresh();


}
