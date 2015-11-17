package test2;

import java.io.IOException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class RabbitServer {

	public static void main(String[] args) throws IOException {
		ConnectionFactory cf = new ConnectionFactory();
		cf.setHost("127.0.0.1");

		Connection conn = cf.newConnection(); //创建一个连接
		Channel channel = conn.createChannel();//创建一个渠道
		
	
		//设置exchangeName fanout 是exchangeName的类型，fanout表示发送给多个消费者
		String ExchangeName ="my-mq-exchange";
		channel.exchangeDeclare(ExchangeName, "direct",true);
		
		String messageType = "s.type";
		
		
		
		
		String msg ="你好";
		
		//发送消息
		channel.basicPublish(ExchangeName, messageType, null,msg.getBytes());
		
		System.out.println("send message   "+msg);
		
		channel.close();
		conn.close();
		
		
		
		
		

	}

}
