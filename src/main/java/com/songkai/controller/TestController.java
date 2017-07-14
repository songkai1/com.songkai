package com.songkai.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.songkai.entity.User;
import com.songkai.service.UserService;
import com.songkai.service.impl.UserServiceImpl;

/**
 * @author songkai
 */
@Controller
@RequestMapping("/test")
public class TestController {
	
	@Autowired
    UserService userService;
	
	@RequestMapping(value={"","/"},method=RequestMethod.GET)
	public String test(){
		User u = userService.getByid(55560700L);
		System.out.println(u);
		return "index";
	}
	
}
