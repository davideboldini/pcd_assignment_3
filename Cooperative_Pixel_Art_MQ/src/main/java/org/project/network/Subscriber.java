package org.project.network;

import com.rabbitmq.client.*;
import org.apache.commons.lang3.SerializationUtils;
import org.project.controller.NetworkController;
import org.project.message.MessageBoot;
import org.project.message.MessageClick;
import org.project.message.MessagePosition;
import org.project.message.MessageWelcome;

import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.TimeoutException;

public class Subscriber{

    private String uniqueID;
    private String exchangeName;
    private String queue;

    private FutureQueue futureQueue;
    private NetworkController networkController;

    private ConnectionFactory factory;
    private Channel channel;
    private Connection connection;

    public Subscriber(final String uniqueID, final String exchangeName, final String hostName, final FutureQueue futureQueue,
                      final NetworkController networkController) throws IOException, TimeoutException {
        this.uniqueID = uniqueID;
        this.futureQueue = futureQueue;
        this.networkController = networkController;
        this.exchangeName = exchangeName;

        this.factory = new ConnectionFactory();
        this.factory.setHost(hostName);

        this.connection = factory.newConnection();
        this.channel = connection.createChannel();

    }

    public void declareExchange() throws IOException, TimeoutException {
        channel.exchangeDeclare(exchangeName, "fanout");
    }

    public void declareQueues() throws IOException, TimeoutException {
        queue = this.channel.queueDeclare(uniqueID, false, true, false, null).getQueue();
    }

    public void declareBindings() throws IOException, TimeoutException {
        channel.queueBind(queue, exchangeName, "");
        //channel.queueBind(queue, exchangeName, "welcome");
        //channel.queueBind(queue, exchangeName, "position");
        //channel.queueBind(queue, exchangeName, "click");
    }

    public void subscribeMessage() throws IOException {
        this.attachNewConnection();
        //this.attachWelcomeTopic();
        //this.attachPositionTopic();
        //this.attachClickTopic();
    }


    private void attachNewConnection() throws IOException {
        DeliverCallback newConnectionCallback = ((consumerTag, delivery) -> {
            MessageBoot message = SerializationUtils.deserialize(delivery.getBody());
            /*
            if (!Objects.equals(message.getIdSender(), uniqueID)){
                System.out.println("Nuova connessione da: " + message.getIdSender());
                this.networkController.getController().sendWelcomeMessage();
            }
             */
            System.out.println("Nuova connessione da: " + message.getIdSender());
        });

        channel.basicConsume(queue, true, newConnectionCallback, t -> {});
    }


    private void attachWelcomeTopic() throws IOException {
        DeliverCallback welcomeCallback = ((consumerTag, delivery) -> {
            MessageWelcome message = SerializationUtils.deserialize(delivery.getBody());
            System.out.println(message.toString());
        });

        channel.basicConsume(queue, true, welcomeCallback, t -> {});
    }


    private void attachPositionTopic() throws IOException {
        DeliverCallback positionMexCallback = ((consumerTag, delivery) -> {
            MessagePosition message = SerializationUtils.deserialize(delivery.getBody());
            System.out.println(message.toString());
        });

        channel.basicConsume(Topics.MOUSE_POSITION.name(), true, positionMexCallback, consumerTag -> {});
    }


    private void attachClickTopic() throws IOException {
        DeliverCallback clickMexCallback = ((consumerTag, delivery) -> {
            MessageClick message = SerializationUtils.deserialize(delivery.getBody());
            System.out.println(message.toString());
        });

        channel.basicConsume(Topics.CELL_CLICK.name(), true, clickMexCallback, t -> {});
    }

}
