package org.project.server;

import org.project.shared.grid.PixelGrid;
import org.project.shared.grid.PixelGridImpl;
import org.project.shared.brush.BrushManager;
import org.project.shared.brush.BrushManagerImpl;
import org.project.shared.log.Log;
import org.project.shared.log.LogImpl;

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
