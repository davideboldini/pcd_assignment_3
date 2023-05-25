package org.example.network;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Subscriber {

    private String exchangeName;
    private ConnectionFactory factory;
    private Channel channel;
    private Connection connection;

    public Subscriber(final String exchangeName, final String hostName) throws IOException, TimeoutException {
        this.factory = new ConnectionFactory();
        this.factory.setHost(hostName);

        this.exchangeName = exchangeName;

        this.connection = factory.newConnection();
        this.channel = connection.createChannel();

        this.channel.exchangeDeclare(exchangeName, "topic");

    }

    public void startSubscriber(final String routingKey) throws IOException {
        String queueName = this.channel.queueDeclare().getQueue();
        this.channel.queueBind(queueName, exchangeName, routingKey);

        DeliverCallback deliverCallback = ((consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            System.out.println(" [x] Received '" + delivery.getEnvelope().getRoutingKey() + "':'" + message + "'");
        });

        channel.basicConsume(queueName, true, deliverCallback, t -> {});
    }

    public static void main(String[] args) throws IOException, TimeoutException {
        Subscriber subscriber = new Subscriber("topic_logs", "localhost");
        subscriber.startSubscriber("anonymous.info");

    }
}
