package com.nadila.blogapi.config;

import com.nadila.blogapi.config.jwt.AuthEntryPointJwt;
import com.nadila.blogapi.config.jwt.AuthTokenFilter;
import com.nadila.blogapi.config.jwt.JwtUtil;
import com.nadila.blogapi.service.authService.BlogUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class BlogApiConfig {

    private final BlogUserDetailsService  blogUserDetailsService;
    private final AuthEntryPointJwt authEntryPointJwt;


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthTokenFilter authTokenFilter() {
        return new AuthTokenFilter();
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return  authConfig.getAuthenticationManager();

    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(){
        var authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(blogUserDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf(AbstractHttpConfigurer::disable)
                .exceptionHandling(e -> e.authenticationEntryPoint(authEntryPointJwt))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth.requestMatchers("api/v1/auth/signin","/api/v1/auth/signup" ).permitAll()
                        .anyRequest().authenticated());
        httpSecurity.authenticationProvider(daoAuthenticationProvider());
        httpSecurity.addFilterBefore(authTokenFilter(), UsernamePasswordAuthenticationFilter.class);

        return httpSecurity.build();
    }

}
