Pre-Requisites for the Code to Run on Windows:
1. Java
2. Maven
3. IDE (Any)
4. Apache ActiveMQ

 -------- Installing and starting ActiveMQ on Windows: --------

1. Download the Apache ActiveMQ and Extract the downloaded archive in your system.
2. In the extracted root directory, we see a sub-folder named “bin” which contains a file named “activemq”
3. Double-click on this file and wait for few seconds, until the command prompt pauses at something like we see in below snapshot.
4. If you are using 64 bit operating system then go to win-64 folder and then execute below command or double click “activemq.bat” file.


-------- Using ActiveMQ in a Java project --------
Create a Java Project
Using IDE create a simple Java-Maven Project.

Maven Setup
To start with, we need to add appropriate Maven dependencies to our project. We will add the following dependency to our pom.xml file:

<dependencies>
        <dependency>
            <groupId>org.apache.activemq</groupId>
            <artifactId>activemq-all</artifactId>
            <version>5.15.15</version>
        </dependency> 
    </dependencies>

Ceating java class and Making a connection
Create new Java class and start adding ActiveMQ connection. We will make a method to establish the connection with the ActiveMQ.
Here is the code to make a connection:

-------- First create simple Java class for message sending using ActiveMQ --------

public class ActiveMQMessageSender {
      
    //URL of the JMS server. 
    private static String url = ActiveMQConnection.DEFAULT_BROKER_URL;
      
    // default broker URL is : tcp://localhost:61616"
    private static String jmsQueue = "example_QUEUE";
      
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
        TextMessage message = session
                .createTextMessage("{\"SampleJson\": {\"userId\": \"simplifyingTech\"   } }");
          
        // Here we are sending our message!
        producer.send(message);
          
        System.out.println("JMS Message Sent successfuly:: " + message.getText());
        connection.close();
    }
}


-------- Message Receiver / Consumer --------
This class is used to receive the text message from the queue.


public class ActiveMQMessageReceiver {
  
    // URL of the JMS server
    private static String url = ActiveMQConnection.DEFAULT_BROKER_URL;
    // default broker URL is : tcp://localhost:61616"
  
    //The Queue receive messages from
    private static String jmsQueue = "example_QUEUE";
  
    public static void main(String[] args) throws JMSException {
        // Getting JMS connection from the server
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);
        Connection connection = connectionFactory.createConnection();
        connection.start();
  
        // Creating session for sending messages
        Session session = connection.createSession(false,
                Session.AUTO_ACKNOWLEDGE);
  
        // Getting the queue 
        Destination destination = session.createQueue(jmsQueue);
  
        // MessageConsumer is used for receiving (consuming) messages
        MessageConsumer consumer = session.createConsumer(destination);
  
        // Here we receive the message.
        Message message = consumer.receive();
  
        //We will be using TestMessage in our example. MessageProducer sent us a TextMessage
        if (message instanceof TextMessage) {
            TextMessage textMessage = (TextMessage) message;
            System.out.println("JMS Message Received successfully: '" + textMessage.getText() + "'");
        }
        connection.close();
    }
}



Reference Tutorial:
https://www.youtube.com/watch?v=HGZO9G_erIg