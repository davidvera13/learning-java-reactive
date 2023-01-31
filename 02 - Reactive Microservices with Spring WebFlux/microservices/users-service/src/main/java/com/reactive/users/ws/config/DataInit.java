package com.reactive.users.ws.config;

import com.reactive.users.ws.service.UserService;
import org.springframework.core.io.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;

import java.nio.charset.StandardCharsets;


@Component
public class DataInit implements CommandLineRunner {

    // getting files from classpath
    @Value("classpath:h2/h2Init.sql")
    private Resource initSql;

    private final R2dbcEntityTemplate r2dbcEntityTemplate;

    private final UserService userService;

    @Autowired
    public DataInit(R2dbcEntityTemplate r2dbcEntityTemplate, UserService userService) {
        this.r2dbcEntityTemplate = r2dbcEntityTemplate;
        this.userService = userService;
    }

    @Override
    public void run(String... args) throws Exception {
        String query = StreamUtils.copyToString(initSql.getInputStream(), StandardCharsets.UTF_8);
        System.out.println(query);
        this.r2dbcEntityTemplate
                .getDatabaseClient()
                .sql(query)
                .then()
                .subscribe();

    }

}
