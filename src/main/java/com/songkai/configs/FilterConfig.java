package com.songkai.configs;

import javax.servlet.Filter;

import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.songkai.filter.ActionFilter;

/**
 * 
 * @author songkai
 *
 */
//@Configuration
@Component
public class FilterConfig {
	
	@Bean
    public FilterRegistrationBean someFilterRegistration() {

        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(actionFilter());
        registration.addUrlPatterns("/*");
        registration.addInitParameter("excludedURLs", "/build/;/images;/js/;/css/;/img/;/fonts/");
        registration.setName("actionFilter");
        return registration;
    }
	
	@Bean(name = "actionFilter")
    public Filter actionFilter() {
        return new ActionFilter();
    }
}
