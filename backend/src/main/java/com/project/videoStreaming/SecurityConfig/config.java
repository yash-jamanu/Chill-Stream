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
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.project.videoStreaming.Users.Service.CustomUserLogin;



@Configuration
@EnableWebSecurity
public class config {

    @Autowired
    CustomUserLogin customUserLogin;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{

        return httpSecurity
                        .csrf(Customizer -> Customizer.disable())        
                        .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                        .authorizeHttpRequests(auth -> auth
                                .requestMatchers("/api/auth/**").permitAll()
                                .requestMatchers("/video/user/*", "/video/random-videos", "/video/category/*").permitAll()
                                .requestMatchers("/user/channel/*").permitAll()
                                .requestMatchers("/comments/Video/*").permitAll()
                                .requestMatchers("/api/auth/status", "/search").permitAll()
                                .anyRequest().authenticated())
                        .sessionManagement(session -> session
                                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED))
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
        configuration.setAllowedMethods(List.of("*"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setExposedHeaders(List.of("Set-Cookie"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
