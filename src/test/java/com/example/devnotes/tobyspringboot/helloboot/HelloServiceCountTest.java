package com.example.devnotes.tobyspringboot.helloboot;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@HellobootTest
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
//@Transactional
public class HelloServiceCountTest {
    @Autowired HelloService helloService;
    @Autowired HelloRepository helloRepository;

    @Test
    void sayHelloIncreaseCount() {
        helloService.sayHello("hello");
        Assertions.assertThat(helloRepository.countOf("hello")).isEqualTo(1);
    }
}
