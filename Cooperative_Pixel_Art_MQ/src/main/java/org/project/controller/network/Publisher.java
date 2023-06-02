package org.project.controller.network;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.apache.commons.lang3.SerializationUtils;
import org.project.model.Topics;
import org.project.model.message.*;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeoutException;

public class Publisher {

    private ConnectionFactory factory;
    private final String uniqueID;
    private final String exchangeName;

    private final Channel channel;
    private final Connection connection;

    private FutureQueue futureQueue;
    private NetworkController networkController;

    public Publisher(final String uniqueID, final String exchangeName, final String hostName, final FutureQueue futureQueue, final NetworkController networkController) throws Exception {

        this.uniqueID = uniqueID;
        this.exchangeName = exchangeName;
        this.futureQueue = futureQueue;
        this.networkController = networkController;

        this.factory = new ConnectionFactory();
        this.factory.setHost(hostName);

        this.connection = factory.newConnection();
        this.channel = connection.createChannel();

        this.channel.exchangeDeclare(exchangeName, "direct");
    }

    public void publishMessage(final String routingKey, final String message) throws Exception {
        channel.basicPublish(exchangeName, routingKey, null, message.getBytes("UTF-8"));
    }

    public void publishNewConnectionMessage(final MessageBoot message) throws IOException, TimeoutException {
        message.setIdSender(uniqueID);
        channel.basicPublish(exchangeName, Topics.NEW_CONNECTION.name(), null, SerializationUtils.serialize(message));
        futureQueue.setFutureWelcome(new CompletableFuture<>());
    }

    public void publishWelcomeMessage(final MessageWelcome message) throws IOException, TimeoutException {
        message.setIdSender(uniqueID);
        channel.basicPublish(exchangeName, Topics.WELCOME.name(), null, SerializationUtils.serialize(message));
    }

    public void publishPositionMessage(final MessagePosition message) throws IOException, TimeoutException {
        message.setIdSender(uniqueID);
        channel.basicPublish(exchangeName, Topics.MOUSE_POSITION.name(), null, SerializationUtils.serialize(message));
    }

    public void publishClickMessage(final MessageClick message) throws IOException, TimeoutException {
        message.setIdSender(uniqueID);
        channel.basicPublish(exchangeName, Topics.CELL_CLICK.name(), null, SerializationUtils.serialize(message));
    }

    public void publishCloseMessage(final MessageClose message) throws IOException, TimeoutException {
        message.setIdSender(uniqueID);
        channel.basicPublish(exchangeName, Topics.CLOSE.name(), null, SerializationUtils.serialize(message));
    }

}
