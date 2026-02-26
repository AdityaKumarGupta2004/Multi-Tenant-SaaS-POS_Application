package com.trisys.Pos.System;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;
import java.sql.Connection;

@SpringBootApplication
public class PosSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(PosSystemApplication.class, args);
    }
    @Bean
    public ApplicationRunner logDatabaseUrl(DataSource dataSource) {
        return args -> {
            try (Connection connection = dataSource.getConnection()) {
                System.out.println("✅ Connected to DB: " + connection.getMetaData().getURL());
                System.out.println("✅ DB Name: " + connection.getCatalog());
            }
        };
    }

}