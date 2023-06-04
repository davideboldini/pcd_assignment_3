package org.rmi.shared.brush;

import org.rmi.model.message.object.MessageClose;
import org.rmi.model.message.object.MessageColorChanged;
import org.rmi.model.message.object.MessageConnection;
import org.rmi.model.message.object.MessagePosition;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Map;

public interface BrushManager extends Remote {

    void addBrush(final MessageConnection message) throws RemoteException;

    void removeBrush(final MessageClose message) throws RemoteException;

    Map<String, Brush> getBrushMap() throws RemoteException;

    void updateBrushPosition(final MessagePosition message) throws RemoteException;

    void updateBrushColor(final MessageColorChanged message) throws RemoteException;

}
