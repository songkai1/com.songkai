package com.songkai.configs.rabbitMq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;

import com.rabbitmq.client.Channel;

public class MessageConsumer2 implements ChannelAwareMessageListener{
	private Logger logger = LoggerFactory.getLogger(MessageConsumer2.class);

	@Override
	public void onMessage(Message message, Channel channel) throws Exception {
		try {
			String json = new String(message.getBody(), "utf-8");
			logger.info("=============json======>"+json);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("==============message=====>"+message);
		
	}  

}
