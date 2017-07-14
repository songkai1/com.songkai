package com.songkai.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.facebook.swift.service.ThriftService;
import com.songkai.entity.User;

/**
 * 
 * @author songkai
 *
 */
@ThriftService("usersService")
@Service
@Transactional
public interface UserService {
	
	User getByid(Long id);
}
