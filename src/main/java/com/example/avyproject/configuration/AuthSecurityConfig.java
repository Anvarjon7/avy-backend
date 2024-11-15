package com.example.avyproject.configuration;

import com.example.avyproject.security.JwtRequestFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class AuthSecurityConfig {

    private final JwtRequestFilter jwtRequestFilter;

    public AuthSecurityConfig(JwtRequestFilter jwtRequestFilter) {
        this.jwtRequestFilter = jwtRequestFilter;
    }

    @Bean
    public org.springframework.security.crypto.password.PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors().and()
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/api/auth/**").permitAll() // пути, доступные без аутентификации
                .antMatchers("/static/**").permitAll() // Разрешить доступ к изображениям
                .antMatchers("/images/**").permitAll() // Разрешить доступ к изображениям
                .antMatchers("/video/**").permitAll() // Разрешить доступ к видео
                .antMatchers( "/v3/api-docs/**","/swagger-ui/**","/swagger-ui.html").permitAll()
                .antMatchers("/stripe-webhook/**").permitAll() // Разрешить доступ к Stripe Webhook без аутентификации
                .anyRequest().authenticated() // все остальные пути требуют аутентификации
                .and()
                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class); // добавление JWT фильтра

        return http.build();
    }

    @Bean
    public FilterRegistrationBean filterRegistrationBean() {
    FilterRegistrationBean registrationBean = new FilterRegistrationBean();
    registrationBean.setFilter(jwtRequestFilter);
    registrationBean.setEnabled(false);
    return registrationBean;
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("*"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration); // Применение ко всем эндпоинтам
        return source;
    }

}
