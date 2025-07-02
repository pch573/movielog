package com.movielog.movielogbackend.config;

import com.movielog.movielogbackend.jwt.JwtAuthenticationFilter;
import com.movielog.movielogbackend.jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService;

    // ✅ 이 부분을 추가해주세요.
    @Bean
    public PasswordEncoder passwordEncoder() {
        // BCrypt는 비밀번호 해싱을 위한 강력하고 안전한 알고리즘입니다.
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // ✅ 1. CSRF 보호 비활성화 (Stateless API에서는 필수)
                .csrf(AbstractHttpConfigurer::disable)

                // ✅ 2. 세션을 사용하지 않도록 설정 (Stateless API)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                .authorizeHttpRequests((authorizeHttpRequests) ->
                        authorizeHttpRequests
                        // "/" 경로와 "/css/**", "/js/**" 등 정적 리소스에 대해서는 모두 접근 허용
                        .requestMatchers("/login","/auth/**").permitAll()
                        .requestMatchers(HttpMethod.GET,  "/movies/**").permitAll() // 영화 조회(GET) 요청은 모두 허용
                        .anyRequest().authenticated() // 그 외 다른 모든 요청은 인증을 요구
                );

        // JWT 인증 필터를 UsernamePasswordAuthenticationFilter 앞에 추가
        http.addFilterBefore(new JwtAuthenticationFilter(jwtUtil, userDetailsService), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
