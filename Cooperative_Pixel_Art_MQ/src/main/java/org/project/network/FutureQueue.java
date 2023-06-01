package org.project.network;

import org.project.message.MessageWelcome;

import java.util.concurrent.CompletableFuture;

public class FutureQueue {

    private CompletableFuture<MessageWelcome> futureWelcome;

    public void setFutureWelcome(CompletableFuture<MessageWelcome> futureWelcome) {
        this.futureWelcome = futureWelcome;
    }

    public CompletableFuture<MessageWelcome> getFutureWelcome() {
        return futureWelcome;
    }
}
