package org.project.graphics;

import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.FormSpecs;
import com.jgoodies.forms.layout.RowSpec;
import org.project.shared.grid.PixelGrid;
import org.project.shared.brush.BrushManager;
import org.project.shared.log.Log;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class PixelGridView extends JFrame {
	private final VisualiserPanel panel;
	private final PixelGrid grid;
	private final Log logger;
	private final int w, h;
	private final List<PixelGridEventListener> pixelListeners;
	private final List<MouseMovedListener> movedListener;

	private final List<ColorChangeListener> colorChangeListeners;

	SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
	Date date = new Date();

	public PixelGridView(PixelGrid grid, BrushManager brushManager, final Log logger, int w, int h){
		this.logger = logger;
		this.grid = grid;
		this.w = w;
		this.h = h;
		pixelListeners = new ArrayList<>();
		movedListener = new ArrayList<>();
		colorChangeListeners = new ArrayList<>();
		setTitle(".:: PixelArt ::.");
		setResizable(false);
		panel = new VisualiserPanel(grid, brushManager, w, h);
		panel.addMouseListener(createMouseListener());
		panel.addMouseMotionListener(createMotionListener());
		var colorChangeButton = new JButton("Change color");
		colorChangeButton.addActionListener(e -> {
			var color = JColorChooser.showDialog(this, "Choose a color", Color.BLACK);
			if (color != null) {
				colorChangeListeners.forEach(l -> {
					try {
						l.colorChanged(color.getRGB());
					} catch (RemoteException ex) {
						throw new RuntimeException(ex);
					}
				});
			}
		});
		// add panel and a button to the button to change color
		getContentPane().add(panel, BorderLayout.CENTER);
		getContentPane().add(colorChangeButton, BorderLayout.SOUTH);
		getContentPane().add(panel);

		hideCursor();
	}

	public void refresh(){
		panel.repaint();
	}

	public void display() {
		SwingUtilities.invokeLater(() -> {
			this.pack();
			this.setVisible(true);
		});
	}


	public void addPixelGridEventListener(PixelGridEventListener l) { pixelListeners.add(l); }

	public void addMouseMovedListener(MouseMovedListener l) { movedListener.add(l); }

	public void addColorChangedListener(ColorChangeListener l) { colorChangeListeners.add(l); }

	private void hideCursor() {
		var cursorImage = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
		var blankCursor = Toolkit.getDefaultToolkit()
				.createCustomCursor(cursorImage, new Point(0, 0), "blank cursor");
		// Set the blank cursor to the JFrame.
		this.getContentPane().setCursor(blankCursor);
	}

	private MouseListener createMouseListener () {
		return new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					int dx = w / grid.getNumColumns();
					int dy = h / grid.getNumRows();
					int col = e.getX() / dx;
					int row = e.getY() / dy;
					pixelListeners.forEach(l -> {
						try {
							l.selectedCell(col, row);
						} catch (RemoteException ex) {
							throw new RuntimeException(ex);
						}
					});
				} catch (RemoteException ex) {
					ex.printStackTrace();
				}
			}

			@Override
			public void mousePressed(MouseEvent e) {}

			@Override
			public void mouseReleased(MouseEvent e) {}

			@Override
			public void mouseEntered(MouseEvent e) {}

			@Override
			public void mouseExited(MouseEvent e) {}
		};
	}

	private MouseMotionListener createMotionListener() {
		return new MouseMotionListener() {
			@Override
			public void mouseDragged(MouseEvent e) {}

			@Override
			public void mouseMoved(MouseEvent e) {
				movedListener.forEach(l -> {
					try {
						l.mouseMoved(e.getX(), e.getY());
					} catch (RemoteException ex) {
						throw new RuntimeException(ex);
					}
				});
			}
		};
	}

}
