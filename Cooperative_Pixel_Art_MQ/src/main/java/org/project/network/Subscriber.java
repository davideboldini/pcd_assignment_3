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
        channel.exchangeDeclare(exchangeName, "direct");
    }

    public void declareQueues() throws IOException, TimeoutException {
        queue = this.channel.queueDeclare(uniqueID, false, true, false, null).getQueue();
    }

    public void declareBindings() throws IOException, TimeoutException {
        channel.queueBind(queue, exchangeName, Topics.NEW_CONNECTION.name());
        channel.queueBind(queue, exchangeName, Topics.WELCOME.name());
        channel.queueBind(queue, exchangeName, Topics.MOUSE_POSITION.name());
        channel.queueBind(queue, exchangeName, Topics.CELL_CLICK.name());
    }

    public void subscribeMessage() throws IOException {
        this.attachCallback();
    }


    private void attachCallback() throws IOException {
        DeliverCallback callback = ((consumerTag, delivery) -> {

            String routingKey = delivery.getEnvelope().getRoutingKey();

            if (routingKey.equals(Topics.NEW_CONNECTION.name())) {

                MessageBoot message = SerializationUtils.deserialize(delivery.getBody());

                if (!Objects.equals(message.getIdSender(), uniqueID)){
                    this.networkController.getController().sendWelcomeMessage();
                }
                System.out.println("Nuova connessione da: " + message.getIdSender());

            } else if (routingKey.equals(Topics.WELCOME.name())) {

                MessageWelcome message = SerializationUtils.deserialize(delivery.getBody());
                if (!futureQueue.getFutureWelcome().isDone()) {
                    futureQueue.getFutureWelcome().complete(message);
                }
            } else if (routingKey.equals(Topics.MOUSE_POSITION.name())) {

                MessagePosition message = SerializationUtils.deserialize(delivery.getBody());
                if (!Objects.equals(message.getIdSender(), uniqueID)){

                    this.networkController.getController().getBrushController().updateBrushPosition(message.getIdSender(), message.getPosition(), message.getColorBrush());
                }
                System.out.println(message.toString());

            } else if (routingKey.equals(Topics.CELL_CLICK.name())) {

                MessageClick message = SerializationUtils.deserialize(delivery.getBody());
            }

        });

        channel.basicConsume(queue, true, callback, t -> {});
    }

}
