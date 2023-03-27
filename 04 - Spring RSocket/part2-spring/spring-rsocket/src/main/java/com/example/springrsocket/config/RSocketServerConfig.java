package com.example.springrsocket.config;

import io.rsocket.core.Resume;
import org.springframework.boot.rsocket.server.RSocketServerCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
public class RSocketServerConfig {
    @Bean
    RSocketServerCustomizer customizer() {
        // handling resume strategy
        return c -> c.resume(resumeStrategy());
    }

    // will wait 5 minutes before expire resume strategy
    private Resume resumeStrategy() {
        return new Resume()
                .sessionDuration(Duration.ofMinutes(5));
    }
}
