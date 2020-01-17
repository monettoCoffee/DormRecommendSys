package com.config;

import com.aop.HeaderTokenInterceptor;
import com.utils.JsonWebTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Autowired
    private HeaderTokenInterceptor headerTokenInterceptor;

    @Value("#{'${server.interceptor.exclude-path-patterns}'.split(',')}")
    private String[] excludePathPatterns;

    @Value("${application.token.keep-time}")
    private long KEEP_TIME;

    @Value("${application.token.secretKey}")
    private String SECRET_KEY;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(headerTokenInterceptor)
                .excludePathPatterns(excludePathPatterns);

        JsonWebTokenUtil.KEEP_TIME = this.KEEP_TIME;
        JsonWebTokenUtil.SECRET_KEY = this.SECRET_KEY;
    }

}
