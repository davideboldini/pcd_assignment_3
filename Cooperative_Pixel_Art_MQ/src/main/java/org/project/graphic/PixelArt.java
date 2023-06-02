package org.project.graphic;

import org.project.controller.Controller;
import org.project.utility.Pair;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Random;

public class PixelArt implements Serializable {

	private PixelGridView view;
	private PixelGrid grid;
	private final Controller controller;

	public PixelArt(final Controller controller){
		this.controller = controller;
	}

	public void initPixelArt(PixelGrid... grids) {

		if (grids.length == 0) {
			grid = new PixelGrid(40,40);
			Random rand = new Random();
			for (int i = 0; i < 10; i++) {
				grid.set(rand.nextInt(40), rand.nextInt(40), randomColor());
			}
		} else {
			grid = Arrays.stream(grids).findFirst().get();
		}

		var fooBrush = new BrushManager.Brush(0, 0, randomColor());
		view = new PixelGridView(grid, controller.getBrushController().getBrushManager(), 800, 800);
		controller.getBrushController().addBrush(controller.getUniqueID(), fooBrush);

		view.addMouseMovedListener((x, y) -> {
			fooBrush.updatePosition(x,y);
			controller.getBrushController().updateBrushPosition(controller.getUniqueID(), fooBrush);
			controller.getNetworkController().newPosition(fooBrush);
			view.refresh();
		});

		view.addPixelGridEventListener((x, y) -> {
			grid.set(x, y, fooBrush.getColor());
			controller.getNetworkController().newClick(new Pair<>(x,y), fooBrush.getColor());
			view.refresh();
		});

		view.addColorChangedListener(color -> {
			fooBrush.setColor(color);
			view.updateColorUser(controller.getUniqueID(), color);
			controller.getBrushController().updateBrushColor(controller.getUniqueID(), color);
		});

		view.addWindowListener((new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				controller.getNetworkController().newClose();
				System.out.println("Chiusura...");
				System.exit(0);
			}
		}));
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
