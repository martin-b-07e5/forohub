package com.challenge.forohub.security;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class AuthController {

  @Autowired
  private JwtService jwtService;

  @Autowired
  private AuthenticationManager authenticationManager;


  @PostMapping
  public ResponseEntity<?> autenticarUsuario(@RequestBody @Valid AuthUsuarioDTO authUsuarioDTO) {

    // Create an authentication token based on the user's credentials
    Authentication authToken = new UsernamePasswordAuthenticationToken(
        authUsuarioDTO.username(),
        authUsuarioDTO.password()
    );

    // Authenticate the user
    Authentication authenticatedUser = authenticationManager.authenticate(authToken);

    if (authenticatedUser.getPrincipal() instanceof Usuario) {
      Usuario usuario = (Usuario) authenticatedUser.getPrincipal();
      String jwtToken = jwtService.generateToken(usuario);  // Generate JWT token using JwtService

//      return ResponseEntity.ok(jwtToken);  // Return only the token in the response.
      return ResponseEntity.ok(new JwtDTO(jwtToken));  // Return token in the response in json format.
    } else {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
    }

  }

}
