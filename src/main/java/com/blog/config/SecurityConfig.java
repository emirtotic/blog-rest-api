package com.blog.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true) //Use this to enable method level security
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * InMemoryUserDetailsManager object expect plain text as password so we need to encode it using passwordEncoder()
     *
     * @return new BCryptPasswordEncoder()
     */
    PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    /**
     * Method for configuring Spring security
     *
     * @param http object of HttpSecurity
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, "/api/**").permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic();
    }

    /**
     * Method for creating some inmemory users
     * Password must be encoded. Otherwise, it will throw an exception for bad credentials
     *
     * @return new InMemoryUserDetailsManager() InMemory objects (users)
     */
    @Override
    @Bean
    protected UserDetailsService userDetailsService() {

        UserDetails emir = User.builder().username("emir")
                .password(passwordEncoder().encode("admin")).roles("ADMIN").build();
        UserDetails user = User.builder().username("user")
                .password(passwordEncoder().encode("user")).roles("USER").build();
        return new InMemoryUserDetailsManager(emir, user);
    }
}