package com.example.devnotes.tobyspringboot.helloboot;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

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
public class DataSourceTest {
    @Autowired
    DataSource dataSource;

    @Test
    void connect() throws SQLException {
        Connection connection = dataSource.getConnection();
        connection.close();

    }
}
