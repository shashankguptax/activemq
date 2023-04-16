package com.apache;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

public class ActiveMQMessageProducer {
      
    //URL of the JMS server. 
    private static String url = ActiveMQConnection.DEFAULT_BROKER_URL;
      
    // default broker URL is : tcp://localhost:61616"
    private static String jmsQueue = "CUSTOMER_ONBOARDING_QUEUE";
      
    public static void main(String[] args) throws JMSException {        
        // Getting JMS connection from the server and starting it
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);
        Connection connection = connectionFactory.createConnection();
        connection.start();
          
        //Creating a session to send/receive JMS message.
        Session session = connection.createSession(false,
                Session.AUTO_ACKNOWLEDGE);  
          
        //The queue will be created automatically on the server.
        Destination destination = session.createQueue(jmsQueue); 
          
        // MessageProducer is used for sending messages to the queue.
        MessageProducer producer = session.createProducer(destination);
        
        // We will send a small text message with small Json' 
        // TextMessage message = session
        //         .createTextMessage("{\"SampleJson\": {\"userId\": \"First Customer Onboarder with ID: 000001\"   } }");
         
        TextMessage message = session
        .createTextMessage("{   \"id\": 1,   \"name\": \"Leanne Graham\",   \"username\": \"Bret\",   \"email\": \"Sincere@april.biz\",   \"address\": {     \"street\": \"Kulas Light\",     \"suite\": \"Apt. 556\",     \"city\": \"Gwenborough\", 	\"country\": \"Halaland\",     \"zipcode\": \"92998-3874\",   },   \"phone\": \"1-770-736-8031 x56442\",   \"company\": \"Romaguera-Crona\" }");

        // Here we are sending our message!
        producer.send(message);
          
        System.out.println("JMS Message Sent successfuly:: " + message.getText());
        connection.close();
    }
}
