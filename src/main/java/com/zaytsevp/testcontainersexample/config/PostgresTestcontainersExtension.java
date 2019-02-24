package com.zaytsevp.testcontainersexample.config;

import org.hibernate.dialect.PostgreSQL9Dialect;
import org.junit.jupiter.api.extension.Extension;
import org.testcontainers.containers.PostgreSQLContainer;

/**
 * Created on 19.02.2019.
 * <p>
 * Расширение для установки свойств подключения к Postgres в Docker-контейнере
 *
 * @author Pavel Zaytsev
 */
public class PostgresTestcontainersExtension implements Extension {

    static {
        System.out.println("Postgres testcontainers extension starting...\n");

        PostgreSQLContainer postgres = new PostgreSQLContainer();
        postgres.start();

        System.setProperty("spring.datasource.driver-class-name", postgres.getDriverClassName());
        System.setProperty("spring.datasource.url", postgres.getJdbcUrl());
        System.setProperty("spring.datasource.username", postgres.getUsername());
        System.setProperty("spring.datasource.password", postgres.getPassword());
        System.setProperty("spring.jpa.properties.hibernate.dialect", PostgreSQL9Dialect.class.getCanonicalName());
    }
}