package com.songkai;

import org.apache.mina.filter.logging.LoggingFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.alibaba.druid.support.logging.Log;
import com.alibaba.druid.support.logging.LogFactory;

@Configuration
public class SerialServer {

	private static Log log = LogFactory.getLog(SerialServer.class);

    @Bean
    public LoggingFilter loggingFilter() {
        return new LoggingFilter();
    }
    
}
