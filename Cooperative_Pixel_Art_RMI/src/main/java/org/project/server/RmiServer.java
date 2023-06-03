package org.project.server;

import org.project.shared.PixelGrid;
import org.project.shared.PixelGridImpl;
import org.project.shared.brush.BrushManager;
import org.project.shared.brush.BrushManagerImpl;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class RmiServer {

    public static void main(String[] args) {
        try {
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
