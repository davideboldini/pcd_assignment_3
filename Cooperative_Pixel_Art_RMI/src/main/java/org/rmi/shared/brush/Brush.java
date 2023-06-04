package org.rmi.shared.brush;

public interface Brush {

    void updatePosition(final int x, final int y);
    int getX();
    int getY();
    int getColor();
    void setColor(int color);
    void setRandomColor();

}
