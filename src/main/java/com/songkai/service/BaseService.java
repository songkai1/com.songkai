package com.songkai.service;

import org.springframework.beans.factory.annotation.Autowired;
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

	public MyBatisGeneralRepository getmGeneralRepository() {
		return mGeneralRepository;
	}

	public void setmGeneralRepository(MyBatisGeneralRepository mGeneralRepository) {
		this.mGeneralRepository = mGeneralRepository;
	}
	
	
}
