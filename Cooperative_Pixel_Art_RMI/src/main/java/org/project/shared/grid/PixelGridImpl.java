package org.project.shared.grid;

import java.util.Arrays;
import java.util.Random;

public class PixelGridImpl implements PixelGrid{
	private final int nRows;
	private final int nColumns;
	private final int[][] grid;
	
	public PixelGridImpl(final int nRows, final int nColumns) {
		this.nRows = nRows;
		this.nColumns = nColumns;
		grid = new int[nRows][nColumns];
	}

	public void clear() {
		for (int i = 0; i < nRows; i++) {
			Arrays.fill(grid[i], 0);
		}
	}

	public void initGrid() {
		Random rand = new Random();
		for (int i = 0; i < 10; i++) {
			this.set(rand.nextInt(40), rand.nextInt(40), randomColor());
		}
	}

	public void set(final int x, final int y, final int color) {
		grid[y][x] = color;
	}

	public int get(int x, int y) {
		return grid[y][x];
	}

	public int getNumRows() {
		return this.nRows;
	}

	public int getNumColumns() {
		return this.nColumns;
	}

	private static int randomColor() {
		Random rand = new Random();
		return rand.nextInt(256 * 256 * 256);
	}

}
