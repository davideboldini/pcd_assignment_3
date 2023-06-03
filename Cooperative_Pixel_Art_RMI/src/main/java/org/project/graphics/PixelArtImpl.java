package org.project.graphics;

import org.project.shared.PixelGrid;
import org.project.shared.brush.Brush;
import org.project.shared.brush.BrushImpl;
import org.project.shared.brush.BrushManager;
import org.project.utility.Pair;

import java.rmi.RemoteException;

public class PixelArtImpl implements PixelArt{

	private String uniqueID;
	private PixelGrid grid;
	private PixelGridView view;
	private BrushManager brushManager;

	public PixelArtImpl(final String uniqueID, final BrushManager brushManager) {
		this.uniqueID = uniqueID;
		this.brushManager = brushManager;
	}

	public void initPixelArt(final PixelGrid grid) throws RemoteException {

		this.grid = grid;
		this.view = new PixelGridView(grid, brushManager, 800, 800);

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

		view.display();
	}

	public void keepRefresh(){
		new Thread(() -> {
			while (true) {
				view.refresh();
			}
		}).start();
	}

}
