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

    private final String uniqueID;
    private final String exchangeName;

    private final Channel channel;

    private final FutureWelcome futureWelcome;

    public Publisher(final String uniqueID, final String exchangeName, final String hostName, final FutureWelcome futureWelcome) throws Exception {

        this.uniqueID = uniqueID;
        this.exchangeName = exchangeName;
        this.futureWelcome = futureWelcome;

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(hostName);

        Connection connection = factory.newConnection();
        this.channel = connection.createChannel();

        this.channel.exchangeDeclare(exchangeName, "direct");
    }

    public void publishNewConnectionMessage(final MessageBoot message) throws IOException, TimeoutException {
        message.setIdSender(uniqueID);
        channel.basicPublish(exchangeName, Topics.NEW_CONNECTION.name(), null, SerializationUtils.serialize(message));
        futureWelcome.setFutureWelcome(new CompletableFuture<>());
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
