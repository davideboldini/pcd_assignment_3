package org.rmi.graphic;

import org.rmi.model.message.object.MessageClose;
import org.rmi.model.message.object.MessageColorChanged;
import org.rmi.model.message.object.MessageConnection;
import org.rmi.model.message.object.MessagePosition;
import org.rmi.model.message.sender.Sender;
import org.rmi.shared.grid.PixelGrid;
import org.rmi.shared.brush.Brush;
import org.rmi.shared.brush.BrushImpl;
import org.rmi.shared.brush.BrushManager;
import org.rmi.utility.Pair;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.rmi.RemoteException;

public class PixelArtImpl implements PixelArt{

	private PixelGridView view;
	private final BrushManager brushManager;
	private final Sender sender;

	public PixelArtImpl(final BrushManager brushManager, final Sender sender) {
		this.brushManager = brushManager;
		this.sender = sender;
	}

	public void initPixelArt(final PixelGrid grid) throws RemoteException {

		this.view = new PixelGridView(grid, brushManager, 800, 800);

		Brush brush = new BrushImpl(0,0);
		sender.sendEventNewConnection(new MessageConnection(brush));

		view.addMouseMovedListener((x, y) -> {
			brush.updatePosition(x, y);
			sender.sendEventPosition(new MessagePosition(new Pair<>(x,y)));
			view.refresh();
		});

		view.addPixelGridEventListener((x, y) -> {
			grid.set(x, y, brush.getColor());
			view.refresh();
		});

		view.addColorChangedListener(color -> {
			brush.setColor(color);
			sender.sendEventColorChanged(new MessageColorChanged(color));
		});

		view.addWindowListener((new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				try {
					sender.sendCloseEvent(new MessageClose());
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
