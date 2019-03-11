package com.songkai.datasources;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import com.alibaba.druid.filter.Filter;
import com.alibaba.druid.filter.logging.Log4jFilter;
import com.alibaba.druid.filter.stat.StatFilter;
import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.songkai.configs.SampleZkClient;

@Configuration
public class MysqlDataSourceConfig {

    @Autowired
    SampleZkClient sampleZkClient;
    
    @Value("${rfd_seller_mysql_rl_zookeeper_filename}")
    private String rfd_oracle_rl_zookeeper_filename;
    
    @Bean
    public StatFilter setStatFilter(){
    	StatFilter statFilter = new StatFilter();
    	statFilter.setSlowSqlMillis(1000);
    	statFilter.setLogSlowSql(true);
    	statFilter.setMergeSql(true);
    	return statFilter;
    }
    
    @Bean
    public Log4jFilter setLog4jFilter(){
    	Log4jFilter log4jFilter = new Log4jFilter();
    	log4jFilter.setResultSetLogEnabled(false);
    	log4jFilter.setDataSourceLogEnabled(true);
    	return log4jFilter;
    }
    
    /**
     * 配置DataSource
     * @return
     * @throws SQLException
     */
    @Bean(initMethod = "init",destroyMethod = "close")
    @Primary
    public DataSource mysqlDataSource() throws SQLException {
    	String nodeData = sampleZkClient.getNodeData(rfd_oracle_rl_zookeeper_filename);
    	JSONObject jsonObject = JSON.parseObject(nodeData);
        DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.setUrl(jsonObject.getString("url"));
        druidDataSource.setUsername(jsonObject.getString("username"));
        druidDataSource.setPassword(jsonObject.getString("password"));
        druidDataSource.setInitialSize(jsonObject.getIntValue("initialSize"));
        druidDataSource.setMaxActive(jsonObject.getIntValue("maxActive"));
        druidDataSource.setMaxWait(jsonObject.getIntValue("maxWait"));
        druidDataSource.setTestWhileIdle(jsonObject.getBooleanValue("testWhileIdle"));
        druidDataSource.setTimeBetweenEvictionRunsMillis(jsonObject.getIntValue("timeBetweenEvictionRunsMillis"));
        druidDataSource.setMinEvictableIdleTimeMillis(jsonObject.getIntValue("minEvictableIdleTimeMillis"));
        druidDataSource.setRemoveAbandoned(jsonObject.getBooleanValue("removeAbandoned"));
        druidDataSource.setRemoveAbandonedTimeout(jsonObject.getIntValue("removeAbandonedTimeout"));
        druidDataSource.setLogAbandoned(jsonObject.getBooleanValue("logAbandoned"));
        druidDataSource.setPoolPreparedStatements(jsonObject.getBooleanValue("poolPreparedStatements"));
        druidDataSource.setConnectionProperties(jsonObject.getString("connectionProperties"));
        druidDataSource.setFilters("config");
        List<Filter> filters = new ArrayList<Filter>();
        filters.add(setLog4jFilter());
        filters.add(setStatFilter());
        druidDataSource.setProxyFilters(filters);
        return druidDataSource;
    }
    
    @Bean(name="sqlSessionFactoryMysqlRW")
    public SqlSessionFactory getMysqlSqlSessionFactoryBean() throws Exception{
    	SqlSessionFactoryBean sessionFactoryBean = new SqlSessionFactoryBean();
    	sessionFactoryBean.setDataSource(mysqlDataSource());
    	sessionFactoryBean.setConfigLocation(new DefaultResourceLoader().getResource("classpath:config/mybatis/mybatis-config.xml"));
    	Resource[] resources = new PathMatchingResourcePatternResolver().getResources("classpath:config/mybatis/mappers/*.xml");
    	sessionFactoryBean.setMapperLocations(resources);
    	return sessionFactoryBean.getObject();
    }
    
    @Bean(name="sqlMysqlRWSession")
    public SqlSessionTemplate getMysqlSqlSessionTemplate() throws Exception{
    	return new SqlSessionTemplate(getMysqlSqlSessionFactoryBean());
    }
}
