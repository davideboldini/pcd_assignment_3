package org.rmi.model;

import java.io.Serializable;

public class Cell implements Serializable {

    private final int x;
    private final int y;
    private final int color;

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
