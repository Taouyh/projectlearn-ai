package com.projectlearn.app;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class InfrastructureConnectionTests {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Test
    void mysqlAndRedisAreReachable() throws Exception {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT 1");
             ResultSet resultSet = statement.executeQuery()) {
            assertThat(resultSet.next()).isTrue();
            assertThat(resultSet.getInt(1)).isEqualTo(1);
        }

        assertThat(redisTemplate.getConnectionFactory()).isNotNull();
        assertThat(redisTemplate.getConnectionFactory().getConnection().ping()).isEqualTo("PONG");
    }
}
