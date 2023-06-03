package org.project.shared.brush;

import org.project.utility.Pair;

import java.awt.*;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Map;

public interface BrushManager extends Remote {

    void addBrush(final String uniqueID, final Brush brush) throws RemoteException;

    void removeBrush(final String uniqueID) throws RemoteException;

    Map<String, Brush> getBrushMap() throws RemoteException;

    void updateBrushPosition(final String uniqueID, final Pair<Integer, Integer> position) throws RemoteException;

    void updateBrushColor(final String uniqueID, final int color) throws RemoteException;

}
