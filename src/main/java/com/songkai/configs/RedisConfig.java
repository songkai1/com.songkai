package com.songkai.configs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;

import redis.clients.jedis.JedisPoolConfig;

/**
 * 配置redis
 * @author songkai
 *
 */
@Configuration
@ComponentScan
public class RedisConfig {

	@Value("${spring.redis.host}")
	private String redisHost;
	@Value("${spring.redis.port}")
	private String redisPort;
	
	
	@Bean
	public JedisPoolConfig poolConfig(){
		JedisPoolConfig j = new JedisPoolConfig();
		j.setMaxTotal(200); //最大连接数, 默认8个
		j.setMaxIdle(30);//最大空闲数：空闲链接数大于maxIdle时，将进行回收  
		j.setMinIdle(5);//最小空闲数：低于minIdle时，将创建新的链接  
		j.setMaxWaitMillis(3000);//最大等待时间：单位ms  
		j.setTestOnBorrow(true);//使用连接时，检测连接是否成功  
		j.setTestOnReturn(true);//返回连接时，检测连接是否成功  
		return j;
	}
	
	@Bean 
	public JedisConnectionFactory connectionFactory(){
		JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory();
		jedisConnectionFactory.setHostName(redisHost);
		jedisConnectionFactory.setPort(Integer.parseInt(redisPort));
		jedisConnectionFactory.setTimeout(10000);//超时时间：单位ms  
		jedisConnectionFactory.setPoolConfig(poolConfig());
		return jedisConnectionFactory;
	}
	
	@Bean
	public StringRedisTemplate redisTemplate(){
		StringRedisTemplate stringRedisTemplate = new StringRedisTemplate();
		stringRedisTemplate.setConnectionFactory(connectionFactory());
		return stringRedisTemplate;
	}
	
}
