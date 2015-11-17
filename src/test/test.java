package test;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;
import com.rabbitmq.client.QueueingConsumer.Delivery;

/**
 * @description 
 * @author 史彦磊
 * @version 1.0
 *  2014年12月1日
 */
public class test {
	public static void main(String[] args) throws  Exception {
		ConnectionFactory cf = new ConnectionFactory();
		cf.setHost("10.99.187.135");
		cf.setPort(5672); //默认端口号为5672
		cf.setUsername("admin");
		cf.setPassword("admin");
		Connection conn = cf.newConnection();
		Channel channel = conn.createChannel();
		
		String exchangeName ="my-mq-exchange";
		channel.exchangeDeclare(exchangeName, "direct",true);
		//这里使用没有参数的queueDeclare方法创建queue并获取queueName
//		String queueName =channel.queueDeclare().getQueue();
		String queueName ="b2c_MQBTCP";
		//将queue绑定到exchange中
		channel.queueBind(queueName, exchangeName,"");
		//channel.queueDeclare(queueName,false,false,false,null);
		//上面的部分与Sender是一样的
		//配置好获取消息的方式
		QueueingConsumer consumer = new QueueingConsumer(channel);
		//设置成false后就表示关闭了自动回馈消息
		channel.basicConsume(queueName, true, consumer);  // 中间的true 表示接收到消息后会自动反馈给服务端一个回馈，告诉发送者消息已经处理
		
		
		
		//循环获取消息
		while(true){
			//获取消息，如果没有消息，这一步将会一直阻塞
			Delivery delivery = consumer.nextDelivery();
			
			String msg = new String(delivery.getBody());
			
			//确认消息，已经收到  
           // channel.basicAck(delivery.getEnvelope().getDeliveryTag() , false);  
			
			System.out.println("received msg      "+msg);
			
		}
	}
}
