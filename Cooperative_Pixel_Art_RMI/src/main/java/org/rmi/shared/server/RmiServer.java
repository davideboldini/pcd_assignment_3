package org.rmi.shared.server;

import org.rmi.shared.grid.PixelGrid;
import org.rmi.shared.grid.PixelGridImpl;
import org.rmi.shared.brush.BrushManager;
import org.rmi.shared.brush.BrushManagerImpl;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class RmiServer {

    public static void main(String[] args) {
        try {
            String ipAddress = "127.0.0.1"; //Local IP address
            System.setProperty("java.rmi.server.hostname", ipAddress);

            BrushManager brushManagerObj = new BrushManagerImpl();
            BrushManager brushManagerObjStub = (BrushManager) UnicastRemoteObject.exportObject(brushManagerObj, 0);

            PixelGrid pixelGridObj = new PixelGridImpl(40, 40);
            pixelGridObj.initGrid();
            PixelGrid pixelGridObjStub = (PixelGrid) UnicastRemoteObject.exportObject(pixelGridObj, 0);

            Registry registry = LocateRegistry.getRegistry();
            registry.rebind("pixelGridObj", pixelGridObjStub);
            registry.rebind("brushManagerObj", brushManagerObjStub);

            System.out.println("Objects registered");

        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
