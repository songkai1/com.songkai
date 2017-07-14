package com.songkai.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.songkai.entity.User;
import com.songkai.service.BaseService;
import com.songkai.service.UserService;

@Service
@Transactional
public class UserServiceImpl extends BaseService implements UserService {
	
	private static final String USER_QUERY_MAPPER = "com.songkai.models.mapper.EmployeeMapper.";
	
	@Override
	public User getByid(Long id) {
		User u = mGeneralRepository.getSqlSession().selectOne(USER_QUERY_MAPPER + "select_employeeDept_list", id);
		return u;
	}
	
}
