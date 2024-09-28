//package com.nav.rentread.config;
//
//
//import com.nav.rentread.entities.User;
//import com.nav.rentread.log.SecurityFilterLog;
//import com.nav.rentread.repository.UserRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.HttpMethod;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.config.Customizer;
//import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
//
//import java.util.Optional;
//
//@Configuration
//@EnableWebSecurity
//@RequiredArgsConstructor
//public class SecurityConfig {
//
//
//    private final UserRepository userRepository;
//
//    @Bean
//    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
//        return http
//                .csrf(AbstractHttpConfigurer::disable)
//                .cors(AbstractHttpConfigurer::disable)
//                .sessionManagement(session -> session.sessionCreationPolicy(
//                        SessionCreationPolicy.STATELESS
//                ))
//                .authorizeHttpRequests(
//                        authorized-> authorized
//                                .requestMatchers("/register").permitAll()
//                                .requestMatchers(HttpMethod.PUT,"/books/{id}").hasRole("ADMIN")
//                                .requestMatchers(HttpMethod.POST, "/books").hasRole("ADMIN")
//                                .requestMatchers(HttpMethod.DELETE, "/books/{id}").hasRole("ADMIN")
//                                .anyRequest().authenticated()
//                ).httpBasic(Customizer.withDefaults())
//                .addFilterBefore(new SecurityFilterLog("Security Before"), BasicAuthenticationFilter.class)
//        .build();
//    }
//
//    @Bean
//    AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
//        return authenticationConfiguration.getAuthenticationManager();
//    }
//
//    @Bean
//    PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//
//
//    @Bean
//    public UserDetailsService userDetailsService() {
//        return username -> {
//            Optional<User> user =  userRepository.findByEmail(username);
//            if(user.isPresent()) return user.get();
//            throw new UsernameNotFoundException("User not found");
//        };
//    }
//}
