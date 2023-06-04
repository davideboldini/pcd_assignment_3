package org.project.graphics;

import org.project.shared.grid.PixelGrid;
import org.project.shared.brush.Brush;
import org.project.shared.brush.BrushImpl;
import org.project.shared.brush.BrushManager;
import org.project.shared.log.Log;
import org.project.utility.Pair;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.rmi.RemoteException;

public class PixelArtImpl implements PixelArt{

	private String uniqueID;
	private PixelGrid grid;
	private PixelGridView view;
	private BrushManager brushManager;
	private Log logger;

	public PixelArtImpl(final String uniqueID, final BrushManager brushManager) {
		this.uniqueID = uniqueID;
		this.brushManager = brushManager;
	}

	public void initPixelArt(final PixelGrid grid) throws RemoteException {

		this.grid = grid;
		this.view = new PixelGridView(grid, brushManager, logger, 800, 800);

		Brush brush = new BrushImpl(0,0);
		brushManager.addBrush(uniqueID, brush);

		view.addMouseMovedListener((x, y) -> {
			brush.updatePosition(x, y);
			brushManager.updateBrushPosition(uniqueID, new Pair<>(x,y));
			view.refresh();
		});

		view.addPixelGridEventListener((x, y) -> {
			grid.set(x, y, brush.getColor());
			view.refresh();
		});

		view.addColorChangedListener(color -> {
			brush.setColor(color);
			brushManager.updateBrushColor(uniqueID, color);
		});

		view.addWindowListener((new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				try {
					brushManager.removeBrush(uniqueID);
				} catch (RemoteException ex) {
					throw new RuntimeException(ex);
				}
				System.out.println("Chiusura...");
				System.exit(0);
			}
		}));

		view.display();
	}

	public void keepRefresh(){
		new Thread(() -> {
			while (true) {
				view.refresh();
				try {
					Thread.sleep(30);
				} catch (InterruptedException e) {
					throw new RuntimeException(e);
				}
			}
		}).start();
	}

}
