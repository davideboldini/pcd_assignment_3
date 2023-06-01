package org.project.graphic;

import org.project.controller.Controller;
import org.project.controller.NetworkController;
import org.project.utility.Pair;

import java.io.IOException;
import java.util.Map;
import java.util.Random;

public class PixelArt {

	private PixelGridView view;
	private PixelGrid grid;
	private BrushManager brushManager;
	private Controller controller;

	public PixelArt(final Controller controller){
		this.controller = controller;
	}

	public void initNewPixelArt() throws IOException {
		brushManager = new BrushManager();
		//var localBrush = new BrushManager.Brush(0, 0, randomColor());
		var fooBrush = new BrushManager.Brush(0, 0, randomColor());
		//brushManager.addBrush(controller.getUniqueID(), localBrush);
		brushManager.addBrush(controller.getUniqueID(), fooBrush);
		grid = new PixelGrid(40,40);

		Random rand = new Random();
		for (int i = 0; i < 10; i++) {
			grid.set(rand.nextInt(40), rand.nextInt(40), randomColor());
		}

		view = new PixelGridView(grid, brushManager, 800, 800);

		view.addMouseMovedListener((x, y) -> {
			//localBrush.updatePosition(x, y);
			fooBrush.updatePosition(x,y);
			controller.getNetworkController().newPosition(new Pair<>(x,y), fooBrush.getColor());
			view.refresh();
		});

		view.addPixelGridEventListener((x, y) -> {
			grid.set(x, y, fooBrush.getColor());
			view.refresh();
		});

		view.addColorChangedListener(fooBrush::setColor);

	}

	public void initExistingPixelArt(final PixelGrid pixelGrid, final Map<String, BrushManager.Brush> brushMap){
		brushManager = new BrushManager();

		for (Map.Entry<String, BrushManager.Brush> entry : brushMap.entrySet()) {
			brushManager.addBrush(entry.getKey(), entry.getValue());
		}
		//var localBrush = new BrushManager.Brush(0, 0, randomColor());
		var fooBrush = new BrushManager.Brush(0, 0, randomColor());
		//brushManager.addBrush(controller.getUniqueID(), localBrush);
		brushManager.addBrush(controller.getUniqueID(), fooBrush);
		grid = pixelGrid;

		view = new PixelGridView(grid, brushManager, 800, 800);

		view.addMouseMovedListener((x, y) -> {
			fooBrush.updatePosition(x, y);
			//controller.getNetworkController().newPosition(new Pair<>(x,y), fooBrush.getColor());
			view.refresh();
		});

		view.addPixelGridEventListener((x, y) -> {
			grid.set(x, y, fooBrush.getColor());
			view.refresh();
		});

		view.addColorChangedListener(fooBrush::setColor);
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

	public BrushManager getBrushManager() {
		return brushManager;
	}
}
