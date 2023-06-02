package org.project.controller.network;

import org.project.graphic.BrushManager;
import org.project.graphic.PixelGrid;
import org.project.model.message.MessageWelcome;
import org.project.utility.Pair;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class FutureQueue {

    private CompletableFuture<Pair<PixelGrid, Map<String, BrushManager.Brush>>> futureWelcome;

    public void setFutureWelcome(CompletableFuture<Pair<PixelGrid, Map<String, BrushManager.Brush>>> futureWelcome) {
        this.futureWelcome = futureWelcome;
    }

    public CompletableFuture<Pair<PixelGrid, Map<String, BrushManager.Brush>>> getFutureWelcome() {
        return futureWelcome;
    }
}
