package com.songkai.configs.rabbitMq;

import java.util.HashMap;
import java.util.Map;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;


@Configuration
@ComponentScan
public class RabbitMqConfig {
	
	@Bean
	public ConnectionFactory getConnectionFactory(){
		CachingConnectionFactory factory = new CachingConnectionFactory();
		factory.setHost("10.3.22.57");
		factory.setPort(5672);
		factory.setUsername("admin");
		factory.setPassword("admin123");
		factory.setVirtualHost("VH_PMS");
		factory.setChannelCacheSize(10);
		factory.setRequestedHeartBeat(10);//表示心跳延迟(单位为秒), Default: 60
		return factory;
	}
	
	@Bean
	public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
		RabbitAdmin rabbitAdmin = new RabbitAdmin(connectionFactory);
		rabbitAdmin.setAutoStartup(true);
		return rabbitAdmin;
	}
	
	//===========================以下为AMQP配置队列绑定等，spring容器加载时候就能够注入===================================   
	
	/**
	 * 队列 MQ_TEST_PMS
	 * @return
	 */
	@Bean(name="MQ_TEST_PMS")
	public Queue queue001() {
		return new Queue("MQ_TEST_PMS",true);
	}
	/**
	 * 队列 MQ_TEST_PMS1
	 * @return
	 */
	@Bean(name="MQ_TEST_PMS1")
	public Queue queue002() {
		Map<String, Object> args = new HashMap<String, Object>();
		args.put("x-message-ttl", "300000");
		return new Queue("MQ_TEST_PMS1",true);
	}
	
	/**
	 * 交换机 PMS_MQ_EXCHANGE
	 * @return
	 */
	@Bean(name="PMS_MQ_EXCHANGE")
	public DirectExchange directExchange() {
	  return new DirectExchange("PMS_MQ_EXCHANGE",true,false);
	}
	
	@Bean
	public Binding bind001() {
	  return BindingBuilder.bind(queue001()).to(directExchange()).with("MQ_TEST_PMS");
	}
	@Bean
	public Binding bind002() {
		return BindingBuilder.bind(queue002()).to(directExchange()).with("MQ_TEST_PMS1");
	}
	
	/**
	 * 消息对象json转换类
	 * @return
	 */
	@Bean
	public MessageConverter jsonMessageConverter() {
		return new Jackson2JsonMessageConverter();
	}
	
	/**
	 * 声明 Template
	 * @return
	 */
	@Bean 
	public RabbitTemplate rabbitTemplate() {
		RabbitTemplate rabbitTemplate = new RabbitTemplate(getConnectionFactory());
		rabbitTemplate.setMessageConverter(jsonMessageConverter());
		rabbitTemplate.setRoutingKey("MQ_TEST_PMS");
		rabbitTemplate.setQueue("MQ_TEST_PMS");  
	 return rabbitTemplate;
	}
	
	/**********************************************************
	 *************************** 消费监听************************
	 ***********************************************************/
	
	/**
	 * MQ_TEST_PMS 消费监听
	 * @return
	 */
	@Bean
	public SimpleMessageListenerContainer messageListenerContainer(){
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(getConnectionFactory());
        container.setConcurrentConsumers(2);
        container.setQueueNames("MQ_TEST_PMS");
        container.setupMessageListener(new MessageListenerAdapter(new MessageConsumer()));
        container.setPrefetchCount(1000);
        return container;
    }
	
	/**
	 * MQ_TEST_PMS1 消费监听2
	 * @return
	 */
	@Bean
	public SimpleMessageListenerContainer messageListenerContainer2(){
		SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(getConnectionFactory());
		container.setConcurrentConsumers(2);
		container.setQueueNames("MQ_TEST_PMS1");
		container.setupMessageListener(new MessageListenerAdapter(new MessageConsumer2()));
		container.setPrefetchCount(1000);
		return container;
	}
	
}
