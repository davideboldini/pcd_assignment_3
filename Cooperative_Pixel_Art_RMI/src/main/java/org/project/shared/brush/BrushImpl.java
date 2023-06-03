package org.project.shared.brush;

import java.io.Serializable;
import java.util.Random;

public class BrushImpl implements Brush, Serializable {

    private int x, y;
    private int color;

    public BrushImpl(final int x, final int y, final int color) {
        this.x = x;
        this.y = y;
        this.color = color;
    }

    public BrushImpl(final int x, final int y){
        this.x = x;
        this.y = y;
        this.setRandomColor();
    }

    public void updatePosition(final int x, final int y) {
        this.x = x;
        this.y = y;
    }
    // write after this getter and setters
    public int getX(){
        return this.x;
    }
    public int getY(){
        return this.y;
    }
    public int getColor(){
        return this.color;
    }
    public void setColor(int color){
        this.color = color;
    }

    public void setRandomColor() {
        Random rand = new Random();
        this.color = rand.nextInt(256 * 256 * 256);
    }

}
