package com.songkai.configs;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import com.songkai.filter.ActionFilter;

//@Configuration
@Component
public class FilterConfig {
	
	@Bean
    public FilterRegistrationBean someFilterRegistration() {

        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new ActionFilter());
        
        List<String> urlPatterns = new ArrayList<String>();
        urlPatterns.add("/**");
        
        registration.setUrlPatterns(urlPatterns);
        return registration;
    }
}
