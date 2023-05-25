package org.project.network;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Subscriber {

    private String uniqueID;
    private String exchangeName;
    private ConnectionFactory factory;
    private Channel channel;
    private Connection connection;
    private String queueName;

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
            System.out.println(consumerTag);
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

        });

    }

    /*
    public static void main(String[] args) throws IOException, TimeoutException {
        Subscriber subscriber = new Subscriber("topic_logs", "localhost");
        subscriber.startSubscriber("anonymous.info");

    }

     */
}
