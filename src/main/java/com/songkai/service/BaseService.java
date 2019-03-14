package com.songkai.service;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.songkai.repositories.MyBatisGeneralRepository;

/**
 * 
 * @author songkai
 *
 */
@Service("baseService")
@Transactional
public class BaseService {
	
	@Autowired
	protected MyBatisGeneralRepository mGeneralRepository;
	
	@Autowired
	protected StringRedisTemplate redisTemplate;
	
	@Autowired
	protected RabbitTemplate rabbitTemplate;
	
}
