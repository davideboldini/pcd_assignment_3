package org.project.network;

import com.rabbitmq.client.*;
import org.apache.commons.lang3.SerializationUtils;
import org.project.message.MessageWelcome;
import org.project.message.MessageClick;
import org.project.message.MessagePosition;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Subscriber {

    private String uniqueID;
    private ConnectionFactory factory;
    private final String exchangeName;
    private final Channel channel;
    private final Connection connection;
    private final String queueName;

    public Subscriber(final String uniqueID, final String exchangeName, final String hostName) throws IOException, TimeoutException {
        this.uniqueID = uniqueID;

        this.factory = new ConnectionFactory();
        this.factory.setHost(hostName);

        this.exchangeName = exchangeName;

        this.connection = factory.newConnection();
        this.channel = connection.createChannel();

        this.channel.exchangeDeclare(exchangeName, "topic");

        this.queueName = this.channel.queueDeclare().getQueue();

    }


    public void startSubscriber(final String routingKey) throws IOException {
        String queueName = this.channel.queueDeclare().getQueue();
        this.channel.queueBind(queueName, exchangeName, routingKey);
        System.out.println(connection.getId());

        DeliverCallback deliverCallback = ((consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            System.out.println(" [x] Received '" + delivery.getEnvelope().getRoutingKey() + "':'" + message + "'");
        });

        channel.basicConsume(queueName, true, deliverCallback, t -> {});

        this.channel.queueBind(queueName, exchangeName, "bella");
        DeliverCallback deliverCallback2 = ((consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            System.out.println(" [x] Received 1'" + delivery.getEnvelope().getRoutingKey() + "':'" + message + "'");
        });

        channel.basicConsume(queueName, true, deliverCallback, t -> {});


    }

    public void attachBootTopic() throws IOException {
        this.channel.queueBind(queueName, exchangeName, Topics.BOOT_TOPIC.name());

        DeliverCallback bootMexCallback = ((consumerTag, delivery) -> {
            MessageWelcome message = SerializationUtils.deserialize(delivery.getBody());
            System.out.println(message.toString());
        });

        channel.basicConsume(queueName, true, bootMexCallback, t -> {});

    }

    public void attachPositionTopic() throws IOException {
        this.channel.queueBind(queueName, exchangeName, Topics.MOUSE_POSITION_TOPIC.name());

        DeliverCallback positionMexCallback = ((consumerTag, delivery) -> {
            MessagePosition message = SerializationUtils.deserialize(delivery.getBody());
            System.out.println(message.toString());
        });

        channel.basicConsume(queueName, true, positionMexCallback, t -> {});
    }

    public void attachClickTopic() throws IOException {
        this.channel.queueBind(queueName, exchangeName, Topics.CELL_CLICK_TOPIC.name());

        DeliverCallback clickMexCallback = ((consumerTag, delivery) -> {
            MessageClick message = SerializationUtils.deserialize(delivery.getBody());
            System.out.println(message.toString());
        });

        channel.basicConsume(queueName, true, clickMexCallback, t -> {});
    }


}
