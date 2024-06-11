package com.openframe.api.preownercars;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.openframe.api.preownercars.interceptor.RateLimitingInterceptor;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private RateLimitingInterceptor rateLimitingInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(rateLimitingInterceptor).excludePathPatterns("/css/**", "/js/**", "/images/**", "/webjars/**", "/**/favicon.ico");
    }
}
