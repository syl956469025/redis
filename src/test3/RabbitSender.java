package test3;

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
		
		String queueName ="order_record";
		
		
		
		
		channel.queueDeclare(
				queueName, //queueName 是列队名字   
				true,		//false 是否将消息持久化到硬盘
				false, 
				false, 
				null
				);
		
		String msg ="你好";
		
		channel.basicPublish(
				"",			//设置ExchangeName  表示将消息发送到exchangeName上
				queueName,  // 设置将消息发送到那个队列里边
				null,		
				msg.getBytes()  //添加发送的消息内容
				);
		
		channel.close();
		conn.close();
		
		
		
		
		
		
		
	}
}
