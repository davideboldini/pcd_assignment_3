package org.project.graphic;

import org.project.controller.Controller;
import org.project.controller.NetworkController;
import org.project.utility.Pair;

import java.io.IOException;
import java.io.Serializable;
import java.util.Map;
import java.util.Random;

public class PixelArt implements Serializable {

	private PixelGridView view;
	private PixelGrid grid;
	private Controller controller;

	public PixelArt(final Controller controller){
		this.controller = controller;
	}

	public void initNewPixelArt() throws IOException {
		var fooBrush = new BrushManager.Brush(0, 0, randomColor());

		controller.getBrushController().addBrush(controller.getUniqueID(), fooBrush);

		grid = new PixelGrid(40,40);

		Random rand = new Random();
		for (int i = 0; i < 10; i++) {
			grid.set(rand.nextInt(40), rand.nextInt(40), randomColor());
		}

		view = new PixelGridView(grid, controller.getBrushController().getBrushManager(), 800, 800);

		view.addMouseMovedListener((x, y) -> {
			fooBrush.updatePosition(x,y);
			controller.getBrushController().updateBrushPosition(controller.getUniqueID(), fooBrush);
			controller.getNetworkController().newPosition(fooBrush);
			view.refresh();
		});

		view.addPixelGridEventListener((x, y) -> {
			grid.set(x, y, fooBrush.getColor());
			view.refresh();
		});

		view.addColorChangedListener(color -> {
			fooBrush.setColor(color);
			controller.getBrushController().updateBrushColor(controller.getUniqueID(), color);
		});

	}

	public void initExistingPixelArt(final PixelGrid pixelGrid){

		var fooBrush = new BrushManager.Brush(0, 0, randomColor());

		controller.getBrushController().addBrush(controller.getUniqueID(), fooBrush);
		controller.getNetworkController().newPosition(fooBrush);

		grid = pixelGrid;
		view = new PixelGridView(grid, controller.getBrushController().getBrushManager(), 800, 800);

		view.addMouseMovedListener((x, y) -> {
			fooBrush.updatePosition(x, y);

			controller.getBrushController().updateBrushPosition(controller.getUniqueID(), fooBrush);
			controller.getNetworkController().newPosition(fooBrush);

			view.refresh();
		});

		view.addPixelGridEventListener((x, y) -> {
			grid.set(x, y, fooBrush.getColor());
			view.refresh();
		});

		view.addColorChangedListener(color -> {
			fooBrush.setColor(color);
			controller.getBrushController().updateBrushColor(controller.getUniqueID(), color);
		});
	}

	public void showView(){
		view.display();
	}

	public static int randomColor() {
		Random rand = new Random();
		return rand.nextInt(256 * 256 * 256);
	}

	public PixelGrid getGrid() {
		return grid;
	}

	public PixelGridView getView() {
		return view;
	}
}
