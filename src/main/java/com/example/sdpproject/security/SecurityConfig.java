package com.example.sdpproject.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final AuthenticationProvider authenticationProvider;
    private static final String[] AUTH_WHITELIST = {
            "/v3/api-docs/**",
            "/swagger-ui/**",
            "/swagger-ui.html",
    };

    @Bean
    @SuppressWarnings("all")
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults())
                .authorizeHttpRequests(
                        //AUTH
                        request -> request
                                .requestMatchers(AUTH_WHITELIST)
                                .permitAll()
                                .requestMatchers("/api/auth/login", "/api/auth/register", "/api/auth/verification/verify")
                                .permitAll()
                )
                .authorizeHttpRequests(
                        request -> request
                                .requestMatchers("/api/compiler/send")
                                .hasAnyRole("ADMIN", "USER")
                )
                .authorizeHttpRequests(
                        request -> request
                                .requestMatchers("/api/algorithms/create")
                                .hasAuthority("ADMIN")
                                .requestMatchers("/api/algorithms/{id}/addTestCase")
                                .hasAuthority("ADMIN")
                                .requestMatchers(HttpMethod.DELETE, "/api/algorithms/{id}")
                                .hasAuthority("ADMIN")
                                .requestMatchers(HttpMethod.PUT, "/api/algorithms/{id}")
                                .hasAuthority("ADMIN")
                                .requestMatchers(HttpMethod.GET, "/api/algorithms/{id}")
                                .permitAll()
                                .requestMatchers("/api/algorithms/{id}/submit")
                                .hasAnyAuthority("ADMIN", "USER")
                                .requestMatchers("/api/algorithms")
                                .permitAll()
                                .requestMatchers("/api/algorithms/algorithmtag")
                                .permitAll()
                )
                .authorizeHttpRequests(
                        request -> request
                                .requestMatchers("/api/algorithmtag/**", "/api/algorithmtag")
                                .hasAuthority("ADMIN")
                )
                .authorizeHttpRequests(
                        request -> request
                                .requestMatchers("/api/difficulty")
                                .hasAuthority("ADMIN")
                )
                .authorizeHttpRequests(
                        request -> request
                                .requestMatchers("/api/submissions")
                                .hasAnyAuthority("ADMIN", "USER")
                )
                .authorizeHttpRequests(
                        request -> request
                                .requestMatchers("/api/conversationtopic", "/api/conversationtopic/**")
                                .hasAuthority("ADMIN")
                )
                .authorizeHttpRequests(
                        request -> request
                                .requestMatchers("/api/message", "/api/message/**")
                                .hasAnyAuthority("ADMIN", "USER")
                )
                .authorizeHttpRequests(
                        request -> request
                                .requestMatchers("/api/conversation", "/api/conversation/**")
                                .hasAnyAuthority("ADMIN", "USER")
                )
                .authorizeHttpRequests(
                        request -> request
                                .requestMatchers("/api/admin/**")
                                .hasAuthority("ADMIN")
                )
                .authorizeHttpRequests(
                        request -> request
                                .requestMatchers("/api/user")
                                .hasAnyAuthority("USER", "ADMIN")
                )
                .logout(
                        request -> request.
                                logoutUrl("/api/auth/logout")
                                .logoutSuccessHandler(
                                        new HttpStatusReturningLogoutSuccessHandler()
                                ).logoutSuccessUrl("/")
                ).authenticationProvider(authenticationProvider);

        return httpSecurity.build();
    }

}
