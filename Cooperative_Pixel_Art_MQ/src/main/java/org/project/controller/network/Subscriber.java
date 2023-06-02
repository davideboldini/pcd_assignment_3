package org.project.controller.network;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import org.apache.commons.lang3.SerializationUtils;
import org.project.model.Cell;
import org.project.model.Topics;
import org.project.model.message.*;
import org.project.utility.Pair;

import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.TimeoutException;

public class Subscriber{

    private final String uniqueID;
    private final String exchangeName;
    private String queue;

    private final FutureQueue futureQueue;
    private final NetworkController networkController;

    private final Channel channel;

    public Subscriber(final String uniqueID, final String exchangeName, final String hostName, final FutureQueue futureQueue,
                      final NetworkController networkController) throws IOException, TimeoutException {
        this.uniqueID = uniqueID;
        this.futureQueue = futureQueue;
        this.networkController = networkController;
        this.exchangeName = exchangeName;

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(hostName);

        Connection connection = factory.newConnection();
        this.channel = connection.createChannel();

    }

    public void declareExchange() throws IOException, TimeoutException {
        channel.exchangeDeclare(exchangeName, "direct");
    }

    public void declareQueues() throws IOException, TimeoutException {
        queue = channel.queueDeclare(uniqueID, false, true, false, null).getQueue();
    }

    public void declareBindings() throws IOException, TimeoutException {
        channel.queueBind(queue, exchangeName, Topics.NEW_CONNECTION.name());
        channel.queueBind(queue, exchangeName, Topics.WELCOME.name());
        channel.queueBind(queue, exchangeName, Topics.MOUSE_POSITION.name());
        channel.queueBind(queue, exchangeName, Topics.CELL_CLICK.name());
        channel.queueBind(queue, exchangeName, Topics.CLOSE.name());
    }

    public void attachCallback() throws IOException {
        DeliverCallback callback = ((consumerTag, delivery) -> {

            String routingKey = delivery.getEnvelope().getRoutingKey();

            switch (Topics.valueOf(routingKey)) {
                case NEW_CONNECTION -> {
                    try {
                        MessageBoot message = SerializationUtils.deserialize(delivery.getBody());
                        if (!Objects.equals(message.getIdSender(), uniqueID)) {
                            this.networkController.newWelcome(this.networkController.getController().getGraphicController().getPixelArt().getGrid(),
                                    this.networkController.getController().getBrushController().getBrushManager().getBrushMap());
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                case WELCOME -> {
                    try {
                        MessageWelcome message = SerializationUtils.deserialize(delivery.getBody());
                        if (!futureQueue.getFutureWelcome().isDone()) {
                            futureQueue.getFutureWelcome().complete(new Pair<>(message.getCurrentPixelGrid(), message.getBrushMap()));
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                case MOUSE_POSITION -> {
                    try {
                        MessagePosition message = SerializationUtils.deserialize(delivery.getBody());
                        if (!Objects.equals(message.getIdSender(), uniqueID)) {
                            this.networkController.getController().getBrushController().updateBrushPosition(message.getIdSender(), message.getBrush());
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                case CELL_CLICK -> {
                    try {
                        MessageClick message = SerializationUtils.deserialize(delivery.getBody());
                        if (!Objects.equals(message.getIdSender(), uniqueID)) {
                            Cell cell = message.getCellClicked();
                            this.networkController.getController().getGraphicController().getPixelArt().getGrid().set(cell.getX(), cell.getY(), cell.getColor());
                            this.networkController.getController().getGraphicController().updateGui();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                case CLOSE -> {
                    try {
                        MessageClose message = SerializationUtils.deserialize(delivery.getBody());
                        if (!Objects.equals(message.getIdSender(), uniqueID)) {
                            this.networkController.getController().getBrushController().removeBrush(message.getIdSender());
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                default -> throw new IllegalStateException("Unexpected value: " + Topics.valueOf(routingKey));
            }

        });

        channel.basicConsume(queue, true, callback, t -> {});
    }

}
