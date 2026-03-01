package com.muhammadali.attendance_management_system.security.config;
import com.muhammadali.attendance_management_system.security.jwt.JwtAuthFilter;
import com.muhammadali.attendance_management_system.security.service.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;
    private final CustomUserDetailsService customUserDetailsService;

    public SecurityConfig(JwtAuthFilter jwtAuthFilter, CustomUserDetailsService customUserDetailsService) {
        this.jwtAuthFilter = jwtAuthFilter;
        this.customUserDetailsService = customUserDetailsService;
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf->csrf.disable())
                .authorizeHttpRequests(auth->auth
                        .requestMatchers("/api/users/generateToken").permitAll()
                        .requestMatchers("/api/users/students").permitAll()
                        .requestMatchers(HttpMethod.POST,"/api/users").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.PUT,"/api/users/**").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.DELETE,"/api/users/**").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.GET,"/api/users").hasAuthority("ADMIN")
                        .requestMatchers("/api/users/faculty").hasAnyAuthority("ADMIN","FACULTY")
                        .requestMatchers("/api/users/**").hasAnyAuthority("ADMIN","FACULTY")
                        .requestMatchers("/api/attendance/**").hasAnyAuthority("ADMIN","FACULTY")
                        .requestMatchers(HttpMethod.GET,"/api/attendance").permitAll()
                        .requestMatchers("/api/attendance/**").hasAuthority("FACULTY")
                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtAuthFilter,
                        UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }


    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception{
        return config.getAuthenticationManager();
    }


    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }



}
