package test2;

import java.io.IOException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class RabbitServer {

	public static void main(String[] args) throws IOException {
		ConnectionFactory cf = new ConnectionFactory();
		cf.setHost("127.0.0.1");

		Connection conn = cf.newConnection(); //����һ������
		Channel channel = conn.createChannel();//����һ������
		
	
		//����exchangeName fanout ��exchangeName�����ͣ�fanout��ʾ���͸����������
		String ExchangeName ="my-mq-exchange";
		channel.exchangeDeclare(ExchangeName, "direct",true);
		
		String messageType = "s.type";
		
		
		
		
		String msg ="���";
		
		//������Ϣ
		channel.basicPublish(ExchangeName, messageType, null,msg.getBytes());
		
		System.out.println("send message   "+msg);
		
		channel.close();
		conn.close();
		
		
		
		
		

	}

}
