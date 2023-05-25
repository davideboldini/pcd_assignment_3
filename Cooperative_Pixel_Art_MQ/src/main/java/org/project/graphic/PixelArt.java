package org.project.graphic;

import org.project.graphic.BrushManager;
import org.project.graphic.PixelGrid;
import org.project.graphic.PixelGridView;

import java.util.Random;

public class PixelArt {

	PixelGridView view;

	public void initPixelArt(){
		var brushManager = new BrushManager();
		var localBrush = new BrushManager.Brush(0, 0, randomColor());
		var fooBrush = new BrushManager.Brush(0, 0, randomColor());
		brushManager.addBrush(localBrush);
		brushManager.addBrush(fooBrush);
		PixelGrid grid = new PixelGrid(40,40);

		Random rand = new Random();
		for (int i = 0; i < 10; i++) {
			grid.set(rand.nextInt(40), rand.nextInt(40), randomColor());
		}

		view = new PixelGridView(grid, brushManager, 800, 800);

		view.addMouseMovedListener((x, y) -> {
			localBrush.updatePosition(x, y);
			view.refresh();
		});

		view.addPixelGridEventListener((x, y) -> {
			grid.set(x, y, localBrush.getColor());
			view.refresh();
		});

		view.addColorChangedListener(localBrush::setColor);
	}

	public void showView(){
		view.display();
	}

	public static int randomColor() {
		Random rand = new Random();
		return rand.nextInt(256 * 256 * 256);
	}

}