package com.example.devnotes.tobyspringboot.helloboot;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TobySpringApplication.class)
//@TestPropertySource("classpath:/application.yml")
@TestPropertySource(properties = {
        "server.port=8888",
        "server.contextPath=/app",
        "data.driver-class-name=org.h2.Driver",
        "data.url=jdbc:h2:mem:~/data/testdb",
        "data.username=sa",
        "data.password="
})
@Transactional
public @interface HellobootTest {
}
