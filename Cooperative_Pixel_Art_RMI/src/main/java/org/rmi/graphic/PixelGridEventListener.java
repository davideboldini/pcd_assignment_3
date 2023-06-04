package org.rmi.graphic;

import java.rmi.RemoteException;

public interface PixelGridEventListener {
	void selectedCell(int x, int y) throws RemoteException;
}
