package org.project.network;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.apache.commons.lang3.SerializationUtils;
import org.project.message.MessageBoot;
import org.project.message.MessageWelcome;
import org.project.message.MessageClick;
import org.project.message.MessagePosition;

import java.io.IOException;

public class Publisher {

    private ConnectionFactory factory;
    private final String uniqueID;
    private final String exchangeName;
    private final Channel channel;
    private final Connection connection;

    public Publisher(final String uniqueID, final String exchangeName, final String hostName) throws Exception {

        this.uniqueID = uniqueID;

        this.factory = new ConnectionFactory();
        this.factory.setHost(hostName);
        this.factory.setPort(5672);
        this.factory.setUsername("davide.boldini");
        this.factory.setPassword("Boldini99");
        this.factory.setVirtualHost("/");

        this.exchangeName = exchangeName;

        this.connection = factory.newConnection();
        this.channel = connection.createChannel();


        this.channel.exchangeDeclare(exchangeName, "topic");

    }

    public void publishMessage(final String routingKey, final String message) throws Exception {
        this.channel.basicPublish(exchangeName, routingKey, null, message.getBytes("UTF-8"));
    }

    public void publishNewConnectionMessage(final MessageBoot message) throws IOException {
        message.setIdSender(uniqueID);
        this.channel.basicPublish(exchangeName, Topics.NEW_CONNECTION_TOPIC.name(), null, SerializationUtils.serialize(message));
    }

    public void publishBootMessage(final MessageWelcome message) throws IOException {
        message.setIdSender(uniqueID);
        this.channel.basicPublish(exchangeName, Topics.BOOT_TOPIC.name(), null, SerializationUtils.serialize(message));
    }

    public void publishPositionMessage(final MessagePosition message) throws IOException {
        message.setIdSender(uniqueID);
        this.channel.basicPublish(exchangeName, Topics.MOUSE_POSITION_TOPIC.name(), null, SerializationUtils.serialize(message));
    }

    public void publishClickMessage(final MessageClick message) throws IOException {
        message.setIdSender(uniqueID);
        this.channel.basicPublish(exchangeName, Topics.CELL_CLICK_TOPIC.name(), null, SerializationUtils.serialize(message));

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
