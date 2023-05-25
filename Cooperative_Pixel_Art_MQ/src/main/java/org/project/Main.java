package org.project;

import org.project.graphic.PixelArt;

import java.util.UUID;

public class Main {

    public static void main(String[] args) {

        String uniqueID = UUID.randomUUID().toString();

        PixelArt pixelArt = new PixelArt();
        pixelArt.initPixelArt();
        pixelArt.showView();

    }
}
