package com.in28minutes.springboot.myfirstwebapp.security;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import java.util.function.Function;

import static org.springframework.boot.autoconfigure.security.servlet.PathRequest.toH2Console;

@Configuration
//@EnableWebSecurity
public class springSecurityConfiguration {


    private static void customize(AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry auth) {
        auth
                .requestMatchers(toH2Console()).permitAll();
    }

    //    InMemoryUserDetailsManager
//    InMemoryUserDeatilsManager(UserDetails... users)
    @Bean
    public InMemoryUserDetailsManager createUserDetailsManager() {
        Function<String, String> passwordEncoder = input -> passwordEncoder().encode(input);
        UserDetails userDetails = createNewUser(passwordEncoder, "in28minutes", "dummy");
        UserDetails userDetails1 = createNewUser(passwordEncoder, "Jayesh", "dummy");

        return new InMemoryUserDetailsManager(userDetails, userDetails1);
    }

    private UserDetails createNewUser(Function<String, String> passwordEncoder, String username, String password) {
        return User.builder()
                .passwordEncoder(passwordEncoder)
                .username(username)
                .password(password)
                .roles("USER", "ADMIN")
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http.authorizeHttpRequests(springSecurityConfiguration::customize);
//        http.formLogin(withDefaults());
//        http.csrf(AbstractHttpConfigurer::disable);
//        http.headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin));
//        return http.build();
//    }
}