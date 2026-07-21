package com.gustavo.u3tournaments.config;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;

import java.nio.charset.StandardCharsets;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public JwtAuthenticationConverter
    jwtAuthenticationConverter() {

        JwtGrantedAuthoritiesConverter authoritiesConverter =
                new JwtGrantedAuthoritiesConverter();

        authoritiesConverter.setAuthoritiesClaimName(
                "roles"
        );

        authoritiesConverter.setAuthorityPrefix("");

        JwtAuthenticationConverter authenticationConverter =
                new JwtAuthenticationConverter();

        authenticationConverter
                .setJwtGrantedAuthoritiesConverter(
                        authoritiesConverter
                );

        return authenticationConverter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http,
            JwtAuthenticationConverter jwtAuthenticationConverter
    ) throws Exception {

        AuthenticationEntryPoint authenticationEntryPoint =
                (request, response, exception) -> {

                    response.setStatus(
                            HttpServletResponse.SC_UNAUTHORIZED
                    );

                    response.setContentType(
                            "application/json"
                    );

                    response.setCharacterEncoding(
                            StandardCharsets.UTF_8.name()
                    );

                    response.getWriter().write(
                            """
                            {
                              "status": 401,
                              "title": "Authentication required",
                              "detail": "A valid access token is required"
                            }
                            """
                    );
                };

        AccessDeniedHandler accessDeniedHandler =
                (request, response, exception) -> {

                    response.setStatus(
                            HttpServletResponse.SC_FORBIDDEN
                    );

                    response.setContentType(
                            "application/json"
                    );

                    response.setCharacterEncoding(
                            StandardCharsets.UTF_8.name()
                    );

                    response.getWriter().write(
                            """
                            {
                              "status": 403,
                              "title": "Access denied",
                              "detail": "You do not have permission to access this resource"
                            }
                            """
                    );
                };

        http
                .csrf(AbstractHttpConfigurer::disable)

                .formLogin(AbstractHttpConfigurer::disable)

                .httpBasic(AbstractHttpConfigurer::disable)

                .sessionManagement(session ->
                        session.sessionCreationPolicy(
                                SessionCreationPolicy.STATELESS
                        )
                )

                .exceptionHandling(exceptions -> exceptions
                        .authenticationEntryPoint(
                                authenticationEntryPoint
                        )
                        .accessDeniedHandler(
                                accessDeniedHandler
                        )
                )

                .authorizeHttpRequests(authorize -> authorize

                        .requestMatchers(
                                HttpMethod.GET,
                                "/health"
                        ).permitAll()

                        .requestMatchers(
                                HttpMethod.POST,
                                "/api/players"
                        ).permitAll()

                        .requestMatchers(
                                HttpMethod.POST,
                                "/api/auth/login"
                        ).permitAll()

                        .requestMatchers(
                                "/error"
                        ).permitAll()

                        .requestMatchers(
                                HttpMethod.GET,
                                "/api/players"
                        ).hasAnyRole(
                                "ADMIN",
                                "MASTER_ADMIN"
                        )

                        .anyRequest().authenticated()
                )

                .oauth2ResourceServer(oauth2 -> oauth2

                        .jwt(jwt -> jwt
                                .jwtAuthenticationConverter(
                                        jwtAuthenticationConverter
                                )
                        )

                        .authenticationEntryPoint(
                                authenticationEntryPoint
                        )

                        .accessDeniedHandler(
                                accessDeniedHandler
                        )
                );

        return http.build();
    }
}