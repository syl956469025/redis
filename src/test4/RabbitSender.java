package test4;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * ��Ϣ������
 * @author shi
 *
 */
public class RabbitSender {
	
	public static void main(String [] args) throws Exception{
		
		ConnectionFactory cf = new ConnectionFactory();
		cf.setHost("10.99.187.135");
		cf.setPort(5672); //Ĭ�϶˿ں�Ϊ5672
		cf.setUsername("admin");
		cf.setPassword("admin");
		Connection conn = cf.newConnection();
		Channel channel = conn.createChannel();
		
		String ExchangeName ="dnn";
		channel.exchangeDeclare(ExchangeName, "fanout");
		
		String msg ="���Ǻ�";
		channel.basicPublish(
				ExchangeName,//���÷�����Ϣ��exchangeName
				"",//������queue
				null,
				msg.getBytes() // ���͵���Ϣ����
				);
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		channel.close();
		conn.close();
		
		
		
		
		
		
		
	}
}
