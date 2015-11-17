package test1;

import java.io.IOException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class RabbitServer {

	public static void main(String[] args) throws IOException {
		ConnectionFactory cf = new ConnectionFactory();
		cf.setHost("10.99.187.135");
		cf.setPort(5672); //默认端口号为5672
		cf.setUsername("admin");
		cf.setPassword("admin");

		Connection conn = cf.newConnection(); //创建一个连接
		Channel channel = conn.createChannel();//创建一个渠道
		
		//String queueName = "queue1";  //定义queue名称
		
		//为channel定义queue的属性   queueName为Queue名称
		//channel.queueDeclareNoWait(queueName, false, false, false, null);//第一个false设置的是：是否将消息持久化到硬盘
		//设置exchangeName fanout 是exchangeName的类型，fanout表示发送给多个消费者
		String ExchangeName ="my-mq-exchange";
		channel.exchangeDeclare(ExchangeName, "direct",true);
		
		String msg ="你好！";
		
		//发送消息
		//发送消息
		channel.basicPublish(ExchangeName, "",null,msg.getBytes());
		
		System.out.println("send message   "+msg);
		
		channel.close();
		conn.close();
		
		
		
		
		
	}

}
