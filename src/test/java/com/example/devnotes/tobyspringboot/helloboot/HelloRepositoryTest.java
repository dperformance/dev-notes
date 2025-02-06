package com.example.devnotes.tobyspringboot.helloboot;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

@HellobootTest
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
//@Transactional
public class HelloRepositoryTest {
    @Autowired
    JdbcTemplate jdbcTemplate;
    @Autowired
    HelloRepository helloRepository;


    @Test
    void findHelloFailed() {
        Assertions.assertThat(helloRepository.findHello("Hello")).isNull();
    }

    @Test
    void increaseCount() {
        Assertions.assertThat(helloRepository.countOf("Hello")).isEqualTo(0);

        helloRepository.increaseCount("Hello");
        Assertions.assertThat(helloRepository.countOf("Hello")).isEqualTo(1);

        helloRepository.increaseCount("Hello");
        Assertions.assertThat(helloRepository.countOf("Hello")).isEqualTo(2);
    }
}
