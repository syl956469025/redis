package test;

import java.io.IOException;




import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;


/**
 * http://blog.csdn.net/is_zhoufeng/article/details/10022503
 * http://xpenxpen.iteye.com/blog/1474608
 * http://www.rabbitmq.com/tutorials/tutorial-one-java.html
 * http://www.erlang.org/download.html
 * 
 * amqp://guest@127.0.0.1:5672/
 * AMQChannel(amqp://guest@127.0.0.1:5672/,1)
 * 
 * ��ʹ��rabbitMQ�Զ������Ϣʱ 3.4�汾�Ķ˿ںŸĳ���15672
 * ���ʵ���ַ��:http://localhost:15672/
 * 
 * 
 * ��Ϣ�жӵķ�����producer
 * @author shi
 *
 */
public class RabbitConnectionProducer {
	private final static String QUEUE_NAME = "test";
	private final static String EXCHANGE_NAME = "my-mq-exchange";
	private final static String message = "{\"orderCode\":\"WAP1412190000000058\" ,\"orderTime\":\"2014-12-23 10:00:23\" }";
	private final static String ROUTINGKEY = "test";
	public static void main(String[] args) throws IOException {
		ConnectionFactory factory = new ConnectionFactory();
	    factory.setHost("10.99.187.135");
	    factory.setPort(5672); //Ĭ�϶˿ں�Ϊ5672
	    factory.setUsername("admin");
//	    factory.setPassword("9ol.8ik,");
	    factory.setPassword("admin");
	   // ExecutorService es = Executors.newFixedThreadPool(20);
	    Connection connection = factory.newConnection();
	    
	    Channel channel = connection.createChannel();
	    /**
	     * 1. ������Ϣ��ת����Ϣ���󶨵Ķ��С��������ͣ�direct, topic, headers and fanout
		 *	direct��ת����Ϣ��routigKeyָ���Ķ���
		 *	topic��������ת����Ϣ������
		 *  headers���������û�нӴ�����
		 *  fanout��ת����Ϣ�����а󶨶���
	     */
	    channel.exchangeDeclare(EXCHANGE_NAME, "direct", true);
	    channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, ROUTINGKEY);
	    
	    channel.basicPublish(EXCHANGE_NAME, QUEUE_NAME, MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes());
	    
	    System.out.println(" [x] Sent '" + message + "'");
	    int i = channel.getChannelNumber();//.getNextPublishSeqNo();
	    channel.close();
	    connection.close();
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	}

}
