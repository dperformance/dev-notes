package com.example.devnotes.tobyspringboot.config;

import org.springframework.context.annotation.Configuration;

/**
 * 인프라 빈 구성 정보의 분리로 인한 미사용 클래스
 */
@Configuration
public class Config {

    /** TomcatWebServerConfig 이동 */
//    @Bean
//    public ServletWebServerFactory servletWebServerFactory() {
//        return new TomcatServletWebServerFactory();
//    }

    /** DispatcherServletConfig 이동 */
//    @Bean
//    public DispatcherServlet dispatcherServlet() {
//        return new DispatcherServlet();
//    }
}
