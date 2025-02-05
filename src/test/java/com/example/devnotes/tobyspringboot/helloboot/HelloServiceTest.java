package com.example.devnotes.tobyspringboot.helloboot;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class HelloServiceTest {

    @Test
    void simpleHelloService() {
        SimpleHelloService helloService = new SimpleHelloService(helloRepositoryStub);
        String result = helloService.sayHello("Test");

        Assertions.assertThat(result).isEqualTo("Hello Test");
    }

    @Test
    void HelloDecorator() {
        HelloDecorator decorator = new HelloDecorator(helloServiceStub);
        String result = decorator.sayHello("Test");
        Assertions.assertThat(result).isEqualTo("*Test*");
    }

    private static HelloRepository helloRepositoryStub = new HelloRepository() {
        @Override
        public Hello findHello(String name) {
            return null;
        }

        @Override
        public void increaseCount(String name) {

        }
    };

    private static HelloService helloServiceStub = new HelloService() {
        @Override
        public String sayHello(String name) {
            return name;
        }

        @Override
        public int countOf(String name) {
            return 0;
        }
    };
}
