package com.songkai.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.songkai.entity.Sellers;
import com.songkai.entity.User;

@Service
public class UserService extends BaseService{
	
	private final String USER_NAMESPACE = "com.songkai.models.mapper.EmployeeMapper.";
	
	public List<User> findAllUser(){
		return mGeneralRepository.getSqlSession().selectList(USER_NAMESPACE+"findAllUser");
	}
	public List<Sellers> findAllSellers(){
		return mGeneralRepository.getMysqlSqlSession().selectList(USER_NAMESPACE+"findAllSellers");
	}
	
}
