package test4;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * 消息发送者
 * @author shi
 *
 */
public class RabbitSender {
	
	public static void main(String [] args) throws Exception{
		
		ConnectionFactory cf = new ConnectionFactory();
		cf.setHost("10.99.187.135");
		cf.setPort(5672); //默认端口号为5672
		cf.setUsername("admin");
		cf.setPassword("admin");
		Connection conn = cf.newConnection();
		Channel channel = conn.createChannel();
		
		String ExchangeName ="dnn";
		channel.exchangeDeclare(ExchangeName, "fanout");
		
		String msg ="你们好";
		channel.basicPublish(
				ExchangeName,//设置发送消息到exchangeName
				"",//不适用queue
				null,
				msg.getBytes() // 发送的消息内容
				);
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		channel.close();
		conn.close();
		
		
		
		
		
		
		
	}
}
