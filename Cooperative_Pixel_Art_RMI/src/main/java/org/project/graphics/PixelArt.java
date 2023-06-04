package org.project.graphics;

import org.project.shared.grid.PixelGrid;
import org.project.shared.log.Log;

import java.rmi.RemoteException;

public interface PixelArt {

    void initPixelArt(final PixelGrid grid) throws RemoteException;
    void keepRefresh();


}
