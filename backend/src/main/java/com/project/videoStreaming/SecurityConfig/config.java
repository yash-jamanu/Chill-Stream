package com.project.videoStreaming.SecurityConfig;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import com.project.videoStreaming.Filter.JwtFilter;
import com.project.videoStreaming.Users.Service.CustomUserLogin;


@Configuration
@EnableWebSecurity
public class config {


    @Autowired
    CustomUserLogin customUserLogin;

    @Autowired
    JwtFilter jwtFilter;


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{

        return httpSecurity
                        .csrf(csrf->csrf.disable())
                        .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                        .authorizeHttpRequests(auth -> auth
                                .requestMatchers("/api/auth/**").permitAll()
                                .requestMatchers("/video/user/*", "/video/random-videos", "/video/category/*").permitAll()
                                .requestMatchers("/user/channel/*").permitAll()
                                .requestMatchers("/comments/Video/*").permitAll()
                                .requestMatchers("/api/auth/status", "/search").permitAll()
                                .requestMatchers("/csrf/token").permitAll()
                                .anyRequest().authenticated())
                        .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                        .sessionManagement(session -> session
                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                        .logout(logout -> logout.logoutSuccessUrl("/login").permitAll())
                        .build();
    } 

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(customUserLogin);
        provider.setPasswordEncoder(passwordEncoder());
        return new ProviderManager(provider);
    }
    
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:5173"));
        configuration.setAllowedMethods(List.of("GET","POST", "OPTION", "PUT", "PATCH", "DELETE"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
