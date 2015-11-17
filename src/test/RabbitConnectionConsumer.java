package test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.ConsumerCancelledException;
import com.rabbitmq.client.MessageProperties;
import com.rabbitmq.client.QueueingConsumer;
import com.rabbitmq.client.ShutdownSignalException;

/**
 * ��Ϣ�жӵĽ�����consumer
 * @author shi
 *
 */
public class RabbitConnectionConsumer {
	private final static String QUEUE_NAME = "b2c_MQBTCP";
	private final static String EXCHANGE_NAME = "my-mq-exchange";
	public static void main(String[] args) throws Exception  {
		RabbitConnectionConsumer p = new RabbitConnectionConsumer();
		p.received();
	}
	
	
	public void received() throws IOException, Exception{
		ConnectionFactory factory = new ConnectionFactory();
	    factory.setHost("10.99.201.109");
	    factory.setPort(5672); 
	    factory.setUsername("admin");
	    factory.setPassword("9ol.8ik,");
	    Connection connection = factory.newConnection();
	    Channel channel = connection.createChannel();

	    channel.queueDeclare(QUEUE_NAME, true, false, false, null);
	    System.out.println(" [*] Waiting for messages. To exit press CTRL+C");
	    
	    QueueingConsumer consumer = new QueueingConsumer(channel);
	    channel.basicConsume(QUEUE_NAME, false, consumer);
		
	    while (true) {
	      QueueingConsumer.Delivery delivery = consumer.nextDelivery();
	      
	      String message = new String(delivery.getBody());
	      
	      channel.basicNack(delivery.getEnvelope().getDeliveryTag(), false, false);
	      Map<String, Object> readValue = JsonUtil.OM.readValue(message,
					HashMap.class);
			Object orderCode = (Object) readValue.get("ordercode");
			if(orderCode !="GM1505160000103610"){
				sendSGOrderRecordMessage(message);
			}else {
				System.out.println();
				System.out.println(message);
				System.out.println();
			}
			
	    }
	}
	
	public void sendSGOrderRecordMessage(String message) throws Exception{
		ConnectionFactory factory = new ConnectionFactory();
	    factory.setHost("10.99.201.109");
	    factory.setPort(5672); 
	    factory.setUsername("admin");
	    factory.setPassword("9ol.8ik,");
	    Connection connection = factory.newConnection();
	    
	    Channel channel = connection.createChannel();
	    
	    channel.exchangeDeclare(EXCHANGE_NAME, "direct", true);
	    channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, QUEUE_NAME);
	    
	    channel.basicPublish(EXCHANGE_NAME, QUEUE_NAME, MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes());
	    
	    System.out.println(" [x] Sent '" + message + "'");
	    channel.close();
	    connection.close();
	}
	
	
	

}
