package test2;


import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;
import com.rabbitmq.client.QueueingConsumer.Delivery;

/**
 * 接收消息
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
		
		//这里使用没有参数的queueDeclare方法创建queue并获取queueName
		String queueName =channel.queueDeclare().getQueue();
		//将queue绑定到exchange中
		channel.queueBind(queueName, exchangeName,"" );
		
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
