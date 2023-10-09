package com.example.shop.config;

import com.example.shop.entity.Role;
import com.example.shop.service.UserService;
import jakarta.persistence.Basic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfig  {

    private UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public SecurityConfig(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    private DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
        auth.setUserDetailsService(userService);
        auth.setPasswordEncoder(passwordEncoder);
        return auth;
    }
    @Bean
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeRequests(authorizeRequests ->
                        authorizeRequests
                                .antMatchers("/users/new").hasAuthority("ADMIN") // Используем "ADMIN" вместо Role.ADMIN.name()
                                .anyRequest().permitAll()
                )
                .formLogin(withDefaults()) // Используем настройки по умолчанию для формы входа
                .logout(logout -> logout
                        .logoutSuccessUrl("/")
                        .permitAll()
                );

        http.authenticationProvider(authenticationProvider());

        return http.build();
    }

//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.authorizeRequests()
//                .antMatchers("/users/new").hasAuthority(Role.ADMIN.name())
//                .anyRequest().permitAll()
//                .and()
//                .formLogin()
//                .loginPage("/login")
//                .loginProcessingUrl("/auth")
//                .permitAll()
//                .and()
//                .logout()
//                .logoutSuccessUrl("/")
//                .permitAll();
//    }
//}


//@Configuration
//@EnableWebSecurity
//@EnableGlobalMethodSecurity
//public class SecurityConfig {
//    private UserService userService;
//    private final PasswordEncoder passwordEncoder;
//
//    @Autowired
//    public SecurityConfig(UserService userService, PasswordEncoder passwordEncoder) {
//        this.userService = userService;
//        this.passwordEncoder = passwordEncoder;
//    }
//
//
// @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
//auth.authenticationProvider(authenticationProvider());
// }
//
// @Basic
//    private AuthenticationProvider authenticationProvider() {
//     DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
//     auth.setUserDetailsService(userService);
//     auth.setPasswordEncoder(passwordEncoder());
//     return auth;
//    }
//
//    @Bean
//    public PasswordEncoder passwordEncoder(){
//     return  new BCryptPasswordEncoder();
//    }
//
//    @Override
//    protected vconfigure(HttpSecurity http) throws Exception{
// http.authorizeRequests()
//         .antMatchers("/urers/new")
//         .hasAuthotity(Role.ADMIN.name())
//         .anyRequest().permistAll()
//         .and()
//             .formLogin
//             .loginPage("/login")
//             .loginProcessingUrl("/auth")
//             .permistAll
//         .and()
//         .logout().logoutRequestMatcher().;
// }
//
//
//
//}
