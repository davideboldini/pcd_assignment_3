package org.project.model;

import java.io.Serializable;

public class Cell implements Serializable {

    private int x;
    private int y;
    private int color;

    public Cell(final int x, final int y, final int color){
        this.x = x;
        this.y = y;
        this.color = color;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public int getColor() {
        return this.color;
    }
}
