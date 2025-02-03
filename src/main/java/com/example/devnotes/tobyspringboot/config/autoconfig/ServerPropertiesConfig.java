package com.example.devnotes.tobyspringboot.config.autoconfig;

import com.example.devnotes.tobyspringboot.config.MyAutoConfiguration;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;

@MyAutoConfiguration
public class ServerPropertiesConfig {

    @Bean
    ServerProperties serverProperties(Environment env) {
        return Binder.get(env).bind("",ServerProperties.class).get();

        // 수동적인 방법.
//        ServerProperties properties = new ServerProperties();
//
//        properties.setContextPath(env.getProperty("contextPath"));
//        properties.setPort(Integer.parseInt(env.getProperty("port")));
//
//        return properties;
    }
}
