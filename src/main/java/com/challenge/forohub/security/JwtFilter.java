package com.challenge.forohub.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {

  private final JwtService jwtService;
  private final UserDetailsService userDetailsService;

  public JwtFilter(JwtService jwtService, UserDetailsService userDetailsService) {
    this.jwtService = jwtService;
    this.userDetailsService = userDetailsService;
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {

    String token = extractToken(request);  // get the token from the header.
//    System.out.println("\n---------- token: " + token);

    if (token != null && jwtService.isTokenValid(token)) {
      String username = jwtService.getUsernameFromToken(token);
//      System.out.println("\n---------- username: " + username + " ----------\n");

      UserDetails userDetails = userDetailsService.loadUserByUsername(username);
      System.out.println("\nUsername: " + userDetails.getUsername());
//      System.out.println("Password: " + userDetails.getPassword());
      System.out.println("Role: " + userDetails.getAuthorities() + "\n");

      // Creates an authentication token with user details and roles.
      var authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
      // Sets the authentication in the security context.
      SecurityContextHolder.getContext().setAuthentication(authentication);
    }
    filterChain.doFilter(request, response);
  }

  // get the token from the header.
  private String extractToken(HttpServletRequest request) {
    String bearer = request.getHeader("Authorization");
    if (bearer != null && bearer.startsWith("Bearer ")) {
      return bearer.substring(7);
    }
    return null;
  }

}
