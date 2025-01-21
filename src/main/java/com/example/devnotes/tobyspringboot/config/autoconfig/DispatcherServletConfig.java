package com.example.devnotes.tobyspringboot.config.autoconfig;

import com.example.devnotes.tobyspringboot.config.MyAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.DispatcherServlet;

//@Configuration
@MyAutoConfiguration
public class DispatcherServletConfig {

    @Bean
    public DispatcherServlet dispatcherServlet() {
        return new DispatcherServlet();
    }
}
