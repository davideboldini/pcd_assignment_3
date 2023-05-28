package org.project.network;

import com.rabbitmq.client.*;
import org.apache.commons.lang3.SerializationUtils;
import org.project.message.MessageBoot;
import org.project.message.MessageClick;
import org.project.message.MessagePosition;
import org.project.message.MessageWelcome;

import java.io.IOException;
import java.net.InetAddress;
import java.util.concurrent.TimeoutException;

public class Subscriber {

    private String uniqueID;
    private ConnectionFactory factory;
    private final String exchangeName;

    private final Channel channelNewConnection;
    private final Channel channelPosition;
    private final Channel channelClick;
    private final Channel channelBoot;

    private final Connection connection;

    private final String queueNewConnection;
    private final String queuePosition;
    private final String queueClick;
    private final String queueBoot;

    public Subscriber(final String uniqueID, final String exchangeName, final String hostName) throws IOException, TimeoutException {
        this.uniqueID = uniqueID;

        this.factory = new ConnectionFactory();
        this.factory.setHost(hostName);

        this.factory.setPort(5672);
        this.factory.setUsername("davide.boldini");
        this.factory.setPassword("Boldini99");
        this.factory.setVirtualHost("/");

        this.exchangeName = exchangeName;

        this.connection = factory.newConnection();

        this.channelNewConnection = connection.createChannel();
        this.channelPosition = connection.createChannel();
        this.channelClick = connection.createChannel();
        this.channelBoot = connection.createChannel();

        this.channelNewConnection.exchangeDeclare(exchangeName, "topic");
        this.channelPosition.exchangeDeclare(exchangeName, "topic");
        this.channelClick.exchangeDeclare(exchangeName, "topic");
        this.channelBoot.exchangeDeclare(exchangeName, "topic");

        this.queueNewConnection = this.channelNewConnection.queueDeclare().getQueue();
        this.queuePosition = this.channelPosition.queueDeclare().getQueue();
        this.queueClick = this.channelClick.queueDeclare().getQueue();
        this.queueBoot = this.channelBoot.queueDeclare().getQueue();

    }

    public void attachNewConnectionTopic() throws IOException {
        this.channelNewConnection.queueBind(queueNewConnection, exchangeName, Topics.NEW_CONNECTION_TOPIC.name());

        DeliverCallback newConnectionMexCallback = ((consumerTag, delivery) -> {
            MessageBoot message = SerializationUtils.deserialize(delivery.getBody());
            System.out.println(message.toString());
        });

        channelNewConnection.basicConsume(queueNewConnection, true, newConnectionMexCallback, t -> {});
    }


    public void attachBootTopic() throws IOException {
        this.channelBoot.queueBind(queueBoot, exchangeName, Topics.BOOT_TOPIC.name());

        DeliverCallback bootMexCallback = ((consumerTag, delivery) -> {
            MessageWelcome message = SerializationUtils.deserialize(delivery.getBody());
            System.out.println(message.toString());
        });

        channelBoot.basicConsume(queueBoot, true, bootMexCallback, t -> {});
    }


    public void attachPositionTopic() throws IOException {
        this.channelPosition.queueBind(queuePosition, exchangeName, Topics.MOUSE_POSITION_TOPIC.name());

        DeliverCallback positionMexCallback = ((consumerTag, delivery) -> {
            MessagePosition message = SerializationUtils.deserialize(delivery.getBody());
            System.out.println(message.toString());
        });

        channelPosition.basicConsume(queuePosition, true, positionMexCallback, consumerTag -> {});
    }


    public void attachClickTopic() throws IOException {
        this.channelClick.queueBind(queueClick, exchangeName, Topics.CELL_CLICK_TOPIC.name());

        DeliverCallback clickMexCallback = ((consumerTag, delivery) -> {
            MessageClick message = SerializationUtils.deserialize(delivery.getBody());
            System.out.println(message.toString());
        });

        channelClick.basicConsume(queueClick, true, clickMexCallback, t -> {});
    }

}
