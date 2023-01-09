package com.flesher.ytdl.Configuration;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import java.security.Security;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfiguration {

    @Configuration
    public static class HttpSecurityConfiguration {

        @Bean
        @Order(10)
        public SecurityFilterChain httpSecurityFilterChain(HttpSecurity http) throws Exception {
            http.cors();
            http
                    .csrf().disable()
                    .authorizeRequests()
                    .antMatchers("/**").permitAll()
                    .anyRequest().permitAll();

            return http.build();

        }

        @Bean
        public InMemoryUserDetailsManager inMemoryUserDetailsManager(
                SecurityProperties properties, ObjectProvider<PasswordEncoder> passwordEncoder) {
            return new UserDetailsServiceAutoConfiguration().inMemoryUserDetailsManager(properties, passwordEncoder);
        }
    }
}
