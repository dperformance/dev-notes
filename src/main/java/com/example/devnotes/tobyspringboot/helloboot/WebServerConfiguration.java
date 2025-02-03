package com.example.devnotes.tobyspringboot.helloboot;

import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// 빈 사이의 의존관계가 특별히 없다면 요즘 관례에 따라서 false로 지정한다.
@Configuration(proxyBeanMethods = false)
public class WebServerConfiguration {
    @Bean
    public ServletWebServerFactory customWebServerFactory() {
        TomcatServletWebServerFactory serverFactory = new TomcatServletWebServerFactory();
        serverFactory.setPort(9090);
        return serverFactory;
    }

}
