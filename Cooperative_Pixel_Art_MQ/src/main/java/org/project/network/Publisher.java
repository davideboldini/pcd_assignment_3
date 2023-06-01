package org.project.network;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.apache.commons.lang3.SerializationUtils;
import org.project.controller.NetworkController;
import org.project.message.MessageBoot;
import org.project.message.MessageWelcome;
import org.project.message.MessageClick;
import org.project.message.MessagePosition;

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

        this.channel.exchangeDeclare(exchangeName, "fanout");
    }

    public void publishMessage(final String routingKey, final String message) throws Exception {
        channel.basicPublish(exchangeName, routingKey, null, message.getBytes("UTF-8"));
    }

    public void publishNewConnectionMessage(final MessageBoot message) throws IOException, TimeoutException {
        message.setIdSender(uniqueID);
        channel.basicPublish(exchangeName, "", null, SerializationUtils.serialize(message));
        futureQueue.setFutureWelcome(new CompletableFuture<>());;
    }

    public void publishWelcomeMessage(final MessageWelcome message) throws IOException, TimeoutException {
        message.setIdSender(uniqueID);
        channel.basicPublish(exchangeName, "", null, SerializationUtils.serialize(message));
    }

    public void publishPositionMessage(final MessagePosition message) throws IOException, TimeoutException {
        message.setIdSender(uniqueID);
        channel.basicPublish(exchangeName, "", null, SerializationUtils.serialize(message));
    }

    public void publishClickMessage(final MessageClick message) throws IOException, TimeoutException {
        message.setIdSender(uniqueID);
        channel.basicPublish(exchangeName, "", null, SerializationUtils.serialize(message));
    }

    public void closeConnection(){
        if (connection != null && connection.isOpen()){
            try {
                connection.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
