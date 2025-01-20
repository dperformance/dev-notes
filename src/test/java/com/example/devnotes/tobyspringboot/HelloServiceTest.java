package com.example.devnotes.tobyspringboot;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class HelloServiceTest {

    @Test
    void simpleHelloService() {
        SimpleHelloService helloService = new SimpleHelloService();
        String result = helloService.sayHello("Test");

        Assertions.assertThat(result).isEqualTo("Hello Test");
    }

    @Test
    void HelloDecorator() {
        HelloDecorator decorator = new HelloDecorator(name -> name);
        String result = decorator.sayHello("Test");
        Assertions.assertThat(result).isEqualTo("*Test*");
    }
}
