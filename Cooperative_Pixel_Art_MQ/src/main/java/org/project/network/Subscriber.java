package org.project.network;

import com.rabbitmq.client.*;
import org.apache.commons.lang3.SerializationUtils;
import org.project.controller.NetworkController;
import org.project.message.*;
import org.project.model.Cell;

import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.TimeoutException;

import static org.project.network.Topics.NEW_CONNECTION;

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
                            futureQueue.getFutureWelcome().complete(message);
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
            }

        });

        channel.basicConsume(queue, true, callback, t -> {});
    }

}
