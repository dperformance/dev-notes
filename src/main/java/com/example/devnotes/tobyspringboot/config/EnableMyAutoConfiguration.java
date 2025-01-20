package com.example.devnotes.tobyspringboot.config;

import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
//@Import({DispatcherServletConfig.class, TomcatWebServerConfig.class}) 동적인 자동 구성 정보 등록
@Import(MyAutoConfigImportSelector.class)
public @interface EnableMyAutoConfiguration {
}
