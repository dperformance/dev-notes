package com.example.devnotes.tobyspringboot.helloboot;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class HelloControllerTest {
    @Test
    void helloController() {
        HelloController helloController = new HelloController(helloServiceStub());

        String result = helloController.hello("Test");

        Assertions.assertThat(result).isEqualTo("Test");
    }

    @Test
    void failsHelloController() {
        HelloController helloController = new HelloController(helloServiceStub());
        Assertions.assertThatThrownBy(() -> {
            String result = helloController.hello(null);
        }).isInstanceOf(IllegalArgumentException.class);

        Assertions.assertThatThrownBy(() -> {
            String result = helloController.hello(" ");
        }).isInstanceOf(IllegalArgumentException.class);
    }

    private static HelloService helloServiceStub() {
        return new HelloService() {
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
}
