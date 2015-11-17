package test3;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;
import com.rabbitmq.client.QueueingConsumer.Delivery;

public class RabbitRec {
	
	public static void main(String [] args) throws Exception{
		
		ConnectionFactory cf = new ConnectionFactory();
		cf.setHost("10.99.187.135");
		cf.setPort(5672); //Ĭ�϶˿ں�Ϊ5672
		cf.setUsername("admin");
		cf.setPassword("admin");
		Connection conn = cf.newConnection();
		Channel channel = conn.createChannel();
		
		String queueName ="order_record";
		
		channel.queueDeclare(
				queueName, //�����ж�����
				true, 		//�Ƿ���Ϣ�־û�
				false,		
				false,
				null
				);
		
		//���úû�ȡ��Ϣ�ķ�ʽ
		QueueingConsumer consumer = new QueueingConsumer(channel);
		channel.basicConsume(queueName, true, consumer);
		
		
		//ѭ����ȡ��Ϣ
		while(true){
			Delivery delivery = consumer.nextDelivery();
			String msg = new String(delivery.getBody());
			System.out.println(msg);
		}
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
	}
}
