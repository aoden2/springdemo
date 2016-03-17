package com.tdt.springdemo.config;

import com.tdt.springdemo.utils.MailUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

@Configuration
@PropertySource(value = "classpath:application.properties")
public class RootConfig {

    @Autowired
    Environment environment;

    @Bean
    public MailUtil mailUtil() {
        MailUtil.setEmail(environment.getProperty("mail"));
        MailUtil.setPassword(environment.getProperty("mail.password"));
        return new MailUtil();
    }
}
