package org.project.network;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;

public class Publisher {

    private String uniqueID;
    private String exchangeName;
    private ConnectionFactory factory;
    private Channel channel;
    private Connection connection;

    public Publisher(final String uniqueID, final String exchangeName, final String hostName) throws Exception {

        this.uniqueID = uniqueID;

        this.factory = new ConnectionFactory();
        this.factory.setHost(hostName);

        this.exchangeName = exchangeName;

        this.connection = factory.newConnection();
        this.channel = connection.createChannel();


        this.channel.exchangeDeclare(exchangeName, "topic");

    }

    public void publishMessage(final String routingKey, final String message) throws Exception {
        this.channel.basicPublish(exchangeName, routingKey, null, message.getBytes("UTF-8"));
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

    /*
    public static void main(String[] args) throws Exception {
        Publisher publisher = new Publisher("topic_logs", "localhost");

        publisher.publishMessage("anonymous.info", "Hello");

        publisher.closeConnection();
    }

     */
}
