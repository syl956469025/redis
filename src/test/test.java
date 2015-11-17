package test;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;
import com.rabbitmq.client.QueueingConsumer.Delivery;

/**
 * @description 
 * @author ʷ����
 * @version 1.0
 *  2014��12��1��
 */
public class test {
	public static void main(String[] args) throws  Exception {
		ConnectionFactory cf = new ConnectionFactory();
		cf.setHost("10.99.187.135");
		cf.setPort(5672); //Ĭ�϶˿ں�Ϊ5672
		cf.setUsername("admin");
		cf.setPassword("admin");
		Connection conn = cf.newConnection();
		Channel channel = conn.createChannel();
		
		String exchangeName ="my-mq-exchange";
		channel.exchangeDeclare(exchangeName, "direct",true);
		//����ʹ��û�в�����queueDeclare��������queue����ȡqueueName
//		String queueName =channel.queueDeclare().getQueue();
		String queueName ="b2c_MQBTCP";
		//��queue�󶨵�exchange��
		channel.queueBind(queueName, exchangeName,"");
		//channel.queueDeclare(queueName,false,false,false,null);
		//����Ĳ�����Sender��һ����
		//���úû�ȡ��Ϣ�ķ�ʽ
		QueueingConsumer consumer = new QueueingConsumer(channel);
		//���ó�false��ͱ�ʾ�ر����Զ�������Ϣ
		channel.basicConsume(queueName, true, consumer);  // �м��true ��ʾ���յ���Ϣ����Զ������������һ�����������߷�������Ϣ�Ѿ�����
		
		
		
		//ѭ����ȡ��Ϣ
		while(true){
			//��ȡ��Ϣ�����û����Ϣ����һ������һֱ����
			Delivery delivery = consumer.nextDelivery();
			
			String msg = new String(delivery.getBody());
			
			//ȷ����Ϣ���Ѿ��յ�  
           // channel.basicAck(delivery.getEnvelope().getDeliveryTag() , false);  
			
			System.out.println("received msg      "+msg);
			
		}
	}
}
