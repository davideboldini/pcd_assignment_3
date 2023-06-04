package org.rmi.shared.brush;

import org.rmi.model.message.object.MessageClose;
import org.rmi.model.message.object.MessageColorChanged;
import org.rmi.model.message.object.MessageConnection;
import org.rmi.model.message.object.MessagePosition;
import org.rmi.utility.log.Log;
import org.rmi.utility.log.LogImpl;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class BrushManagerImpl implements BrushManager, Serializable {

    private final Log logger = new LogImpl();
    private final Map<String, Brush> brushMap = new HashMap<>();

    public synchronized void addBrush(final MessageConnection message) {
        logger.joinedUserMex(message.getIdSender());
        brushMap.put(message.getIdSender(), message.getBrush());
    }

    public synchronized void removeBrush(final MessageClose message) {
        logger.quitUserMex(message.getIdSender());
        brushMap.remove(message.getIdSender());
    }

    public synchronized void updateBrushPosition(final MessagePosition message) {
        brushMap.get(message.getIdSender()).updatePosition(message.getPosition().getX(), message.getPosition().getY());
    }

    public synchronized void updateBrushColor(final MessageColorChanged message) {
        logger.changeColorUserMex(message.getIdSender(), message.getColor());
        brushMap.get(message.getIdSender()).setColor(message.getColor());
    }

    public synchronized Map<String, Brush> getBrushMap() {
        return brushMap;
    }
}
