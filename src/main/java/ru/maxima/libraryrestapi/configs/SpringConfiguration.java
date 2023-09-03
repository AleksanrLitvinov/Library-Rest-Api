package ru.maxima.libraryrestapi.configs;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import ru.maxima.libraryrestapi.jwt.Authorities;
import ru.maxima.libraryrestapi.jwt.JwtFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class SpringConfiguration {

    private final JwtFilter jwtFilter;

//    это специальное Filter предложение, предоставляемое Spring Security,
//    которое позволяет делегировать множество Filter экземпляров через SecurityFilterChain
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(a -> {
                    a.requestMatchers("/auth/get-token").permitAll();
                    a.requestMatchers("/auth/login").permitAll();
                    a.anyRequest().authenticated();
                })
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

//    Выдайте, AuthenticationException если он считает, что входные данные представляют собой недопустимый принципал.
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

//    PasswordEncoder используется для одностороннего преобразования пароля, чтобы обеспечить его безопасное хранение.
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public Authorities authorities() {
        return Authorities.getInstance();
    }
}
