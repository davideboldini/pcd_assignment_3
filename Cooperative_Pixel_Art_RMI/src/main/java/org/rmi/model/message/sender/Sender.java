package org.rmi.model.message.sender;

import org.rmi.model.message.object.MessageClose;
import org.rmi.model.message.object.MessageColorChanged;
import org.rmi.model.message.object.MessageConnection;
import org.rmi.model.message.object.MessagePosition;
import org.rmi.shared.brush.BrushManager;

import java.rmi.RemoteException;

public class Sender implements Runnable{

    private final String uniqueID;
    private final BrushManager brushManager;

    public Sender(final String uniqueID, final BrushManager brushManager) {
        this.uniqueID = uniqueID;
        this.brushManager = brushManager;
    }

    public void sendEventNewConnection(final MessageConnection message) throws RemoteException {
        message.setIdSender(uniqueID);
        brushManager.addBrush(message);
    }

    public void sendEventPosition(final MessagePosition message) throws RemoteException {
        message.setIdSender(uniqueID);
        brushManager.updateBrushPosition(message);
    }

    public void sendEventColorChanged(final MessageColorChanged message) throws RemoteException {
        message.setIdSender(uniqueID);
        brushManager.updateBrushColor(message);
    }

    public void sendCloseEvent(final MessageClose message) throws RemoteException {
        message.setIdSender(uniqueID);
        brushManager.removeBrush(message);
        Thread.currentThread().interrupt();
    }

    @Override
    public void run() {
        while (true) {}
    }
}
