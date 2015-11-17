package test4;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;
import com.rabbitmq.client.QueueingConsumer.Delivery;

public class RabbitRec {
	
	public static void main(String [] args) throws Exception{
		
		ConnectionFactory cf = new ConnectionFactory();
		cf.setHost("10.99.187.135");
		cf.setPort(5672); //默认端口号为5672
		cf.setUsername("admin");
		cf.setPassword("admin");
		Connection conn = cf.newConnection();
		Channel channel = conn.createChannel();
		
		String queueName = channel.queueDeclare().getQueue();
		
		String ExchangeName ="my-mq-exchange";
		channel.exchangeDeclare(ExchangeName, "direct");
		
		channel.queueBind(queueName, ExchangeName, "");
		
		//设置消息接收方式
		QueueingConsumer consumer = new QueueingConsumer(channel);
		channel.basicConsume(queueName, true, consumer);
		
		while(true){
			Delivery delivery =consumer.nextDelivery();
			String msg = new String(delivery.getBody());
			
			System.out.println(msg);
		}
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
	}
}
