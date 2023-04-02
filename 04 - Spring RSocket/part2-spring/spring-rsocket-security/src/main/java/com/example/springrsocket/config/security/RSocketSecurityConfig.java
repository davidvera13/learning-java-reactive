package com.example.springrsocket.config.security;

import org.springframework.boot.rsocket.messaging.RSocketStrategiesCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.rsocket.RSocketStrategies;
import org.springframework.messaging.rsocket.annotation.support.RSocketMessageHandler;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.rsocket.EnableRSocketSecurity;
import org.springframework.security.config.annotation.rsocket.RSocketSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.messaging.handler.invocation.reactive.AuthenticationPrincipalArgumentResolver;
import org.springframework.security.rsocket.core.PayloadSocketAcceptorInterceptor;
import org.springframework.security.rsocket.metadata.SimpleAuthenticationEncoder;

@Configuration
@EnableRSocketSecurity
@EnableReactiveMethodSecurity
public class RSocketSecurityConfig {
    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public RSocketStrategiesCustomizer strategiesCustomizer() {
        return c -> c.encoder(new SimpleAuthenticationEncoder());
    }

    // Allow Injecting Authentication Principal
    @Bean
    public RSocketMessageHandler messageHandler(RSocketStrategies rSocketStrategies) {
        RSocketMessageHandler handler = new RSocketMessageHandler();
        handler.setRSocketStrategies(rSocketStrategies);
        handler.getArgumentResolverConfigurer().addCustomResolver(
                new AuthenticationPrincipalArgumentResolver());
        return handler;

    }


    @Bean
    public PayloadSocketAcceptorInterceptor interceptor(RSocketSecurity security) {
        return security.simpleAuthentication(Customizer.withDefaults())
                .authorizePayload(authorizePayloadsSpec -> authorizePayloadsSpec
                        .setup().hasRole("TRUSTED_CLIENT")
                        // using @PreAuthorize at method level
                        // .route("math.service.secured.table").hasRole("ADMIN")
                        .route("math.service.secured.square").hasAnyRole("USER", "ADMIN", "TRUSTED_CLIENT")
                        //.anyRequest().permitAll()).build();
                        // .anyRequest().denyAll())
                        .anyRequest().authenticated())
                        //.hasAnyRole("USER", "ADMIN", "TRUSTED_CLIENT"))
                .build();
                        // we can pass denyAll()
    }

}
