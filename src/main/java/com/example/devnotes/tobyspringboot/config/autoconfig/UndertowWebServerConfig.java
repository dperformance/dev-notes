package com.example.devnotes.tobyspringboot.config.autoconfig;

import com.example.devnotes.tobyspringboot.config.ConditionalMyOnClass;
import com.example.devnotes.tobyspringboot.config.MyAutoConfiguration;
import org.springframework.boot.web.embedded.undertow.UndertowServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;

@MyAutoConfiguration
@ConditionalMyOnClass("io.undertow.Undertow")
public class UndertowWebServerConfig {
    @Bean("undertowWebServerFactory")
    public ServletWebServerFactory servletWebServerFactory() {
        return new UndertowServletWebServerFactory();
    }
}
