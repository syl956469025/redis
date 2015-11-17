package test2;


import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;
import com.rabbitmq.client.QueueingConsumer.Delivery;

/**
 * ������Ϣ
 * @author shi
 *
 */
public class RabbitClient1 {

	public static void main(String[] args) throws  Exception {
		ConnectionFactory cf = new ConnectionFactory();
		cf.setHost("127.0.0.1");
		Connection conn = cf.newConnection();
		Channel channel = conn.createChannel();
		
		String exchangeName ="news";
		channel.exchangeDeclare(exchangeName, "fanout");
		
		//����ʹ��û�в�����queueDeclare��������queue����ȡqueueName
		String queueName =channel.queueDeclare().getQueue();
		//��queue�󶨵�exchange��
		channel.queueBind(queueName, exchangeName,"" );
		
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
