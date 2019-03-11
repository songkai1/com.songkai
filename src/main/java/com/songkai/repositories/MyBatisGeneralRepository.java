package com.songkai.repositories;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

@Repository
public class MyBatisGeneralRepository{

	@Resource(name = "sqlRWSession")
    protected SqlSession sqlSession;
	@Resource(name = "sqlMysqlRWSession")
	protected SqlSession mysqlSqlSession;

    public SqlSession getSqlSession(){
        return sqlSession;
    }
    
    public SqlSession getMysqlSqlSession() {
		return mysqlSqlSession;
	}
    
}
