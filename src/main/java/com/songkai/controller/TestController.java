package com.songkai.controller;

import java.util.List;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.songkai.entity.Sellers;
import com.songkai.entity.User;
import com.songkai.service.UserService;

/**
 * @author songkai
 */
@Controller
@RequestMapping("/test")
public class TestController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private StringRedisTemplate redisTemplate;
	
	@Autowired
	private RabbitTemplate rabbitTemplate;
	
	@RequestMapping(value={"","/"},method=RequestMethod.GET, produces = "application/json; charset=utf-8")
	@ResponseBody
	public ResponseEntity test() {
		List<User> list = userService.findAllUser();
		List<Sellers> sellers = userService.findAllSellers();
		System.out.println(sellers);
		String code =  redisTemplate.opsForValue().get("pms-01");
		System.out.println(code);
		return ResponseEntity.status(HttpStatus.OK).body(list);
	}
	
}
