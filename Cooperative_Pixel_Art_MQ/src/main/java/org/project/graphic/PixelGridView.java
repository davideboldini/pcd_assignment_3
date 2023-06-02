package org.project.graphic;

import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.FormSpecs;
import com.jgoodies.forms.layout.RowSpec;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class PixelGridView extends JFrame {
    private final VisualiserPanel panel;
	private final DefaultTableModel dtm;
	private final JTextArea textAreaChat;
    private final PixelGrid grid;
    private final int w, h;
    private final List<PixelGridEventListener> pixelListeners;
	private final List<MouseMovedListener> movedListener;

	private final List<ColorChangeListener> colorChangeListeners;

	SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
	Date date = new Date();
    
    public PixelGridView(PixelGrid grid, BrushManager brushManager, int w, int h){
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
				colorChangeListeners.forEach(l -> l.colorChanged(color.getRGB()));
			}
		});
		// add panel and a button to the button to change color
		getContentPane().add(panel, BorderLayout.CENTER);
		getContentPane().add(colorChangeButton, BorderLayout.SOUTH);
        getContentPane().add(panel);
        
        JPanel panelUserChat = new JPanel();
        getContentPane().add(panelUserChat, BorderLayout.EAST);
        panelUserChat.setLayout(new FormLayout(new ColumnSpec[] {
        		ColumnSpec.decode("220px:grow"),},
        	new RowSpec[] {
        		RowSpec.decode("31px"),
        		RowSpec.decode("max(51dlu;default):grow"),
        		FormSpecs.RELATED_GAP_ROWSPEC,
        		FormSpecs.DEFAULT_ROWSPEC,
        		FormSpecs.RELATED_GAP_ROWSPEC,
        		RowSpec.decode("max(63dlu;default):grow"),}));
        
        JLabel lblUsers = new JLabel("Utenti");
        lblUsers.setFont(new Font("Tahoma", Font.PLAIN, 13));
        lblUsers.setVerticalAlignment(SwingConstants.TOP);
        lblUsers.setHorizontalAlignment(SwingConstants.CENTER);
        panelUserChat.add(lblUsers, "1, 1, center, center");

		dtm = new DefaultTableModel(0,0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		JTable usersTable = new JTable(dtm) {
			@Override
			public Component prepareRenderer(TableCellRenderer renderer, int row, int col) {
				Component comp = super.prepareRenderer(renderer, row, col);
				Object value = getModel().getValueAt(row, col);
				if (value instanceof Integer) {
					comp.setBackground(new Color((Integer) value));
				} else {
					comp.setBackground(Color.white);
				}

				return comp;
			}
		};

		String[] header = new String[] {
			"Utente", "Colore"
		};

		// add header in table model
		dtm.setColumnIdentifiers(header);
		usersTable.setModel(dtm);

        panelUserChat.add(usersTable, "1, 2, fill, fill");
        
        JLabel lblChat = new JLabel("Chat");
        lblChat.setFont(new Font("Tahoma", Font.PLAIN, 13));
        panelUserChat.add(lblChat, "1, 4, center, default");

        textAreaChat = new JTextArea();
		textAreaChat.setEditable(false);

		JScrollPane scrollPane = new JScrollPane(textAreaChat);

		panelUserChat.add(scrollPane, "1, 6, fill, fill");
		hideCursor();
		panelUserChat.setCursor(Cursor.getDefaultCursor());
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

	public void addUserToTable(final String uniqueID, final int color) {
		dtm.addRow(new Object[] {uniqueID, color});
		textAreaChat.append(formatter.format(date) + ": " + uniqueID + " si e' unito\n");
	}

	public void removeUserToTable(final String uniqueID) {
		for (int i = 0; i < dtm.getRowCount(); i++) {
			if (((String)dtm.getValueAt(i, 0)).equals(uniqueID)) {
				dtm.removeRow(i);
				break;
			}
		}
		textAreaChat.append(formatter.format(date) + ": " + uniqueID + " ha abbandonato\n");
	}

	public void updateColorUser(final String uniqueID, final int color) {
		for (int i = 0; i < dtm.getRowCount(); i++) {
			if (((String)dtm.getValueAt(i, 0)).equals(uniqueID)) {
				dtm.setValueAt(color, i,1);
				break;
			}
		}
		textAreaChat.append(formatter.format(date) + ": " + uniqueID + " ha cambiato colore in " + color + "\n");
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
				int dx = w / grid.getNumColumns();
				int dy = h / grid.getNumRows();
				int col = e.getX() / dx;
				int row = e.getY() / dy;
				pixelListeners.forEach(l -> l.selectedCell(col, row));
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
				movedListener.forEach(l -> l.mouseMoved(e.getX(), e.getY()));
			}
		};
	}

}
