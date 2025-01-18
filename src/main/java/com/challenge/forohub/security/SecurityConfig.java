package com.challenge.forohub.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.access.AccessDeniedHandlerImpl;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableSpringDataWebSupport(pageSerializationMode = EnableSpringDataWebSupport.PageSerializationMode.VIA_DTO)
public class SecurityConfig {

  // dependency injection
  @Autowired
  private JwtService jwtService;
  @Autowired
  private UserDetailsService userDetailsService;

  @Autowired
  private AccessDeniedHandler accessDeniedHandler;

//  @Bean
//  public AccessDeniedHandler accessDeniedHandler() {
//    AccessDeniedHandlerImpl accessDeniedHandler = new AccessDeniedHandlerImpl();
//    // You can customize the behavior of the handler if needed
//    accessDeniedHandler.setErrorPage("/access-denied");  // Redirect to a custom error page, for example
//    return accessDeniedHandler;
//  }


  // Password encoder
  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
        .csrf(csrf -> csrf.disable())
        .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .authorizeHttpRequests(req -> {
          req.requestMatchers(HttpMethod.POST, "/login").permitAll();  // Allow access without authentication
          req.requestMatchers("/swagger-ui.html").permitAll();  // Allow localhost:8080/context-path/swagger-ui.html
          req.requestMatchers("/swagger-ui/**").permitAll();  // Allow localhost:8080/context-path/swagger-ui/index.html
          req.requestMatchers("/v3/api-docs/**").permitAll();  // Allow access without authentication
          req.requestMatchers(HttpMethod.POST, "/**").hasRole("ADMIN");  // POST only for admin
          req.requestMatchers(HttpMethod.PUT, "/**").hasRole("ADMIN");   // PUT only for admin
//          req.requestMatchers(HttpMethod.DELETE, "/medicos").hasRole("ADMIN");   // DELETE only for admin
          req.requestMatchers(HttpMethod.DELETE, "/**").hasRole("ADMIN"); // DELETE only for admin
          req.requestMatchers(HttpMethod.GET, "/**").hasAnyRole("ADMIN", "USER");  // Read access for both admin and user
          req.anyRequest().authenticated();  // Authentication required for all other endpoints
        })
        // Adds the JWT filter to process requests before the UsernamePasswordAuthenticationFilter.
        .addFilterBefore(new JwtFilter(jwtService, userDetailsService), UsernamePasswordAuthenticationFilter.class)
        // Set CustomAccessDeniedHandler
        .exceptionHandling(ex -> ex.accessDeniedHandler(accessDeniedHandler)); // Updated for Spring Security 6.1+
    return http.build();
  }

  // Authenticate the user
  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
    return authenticationConfiguration.getAuthenticationManager();
  }

}
