package com.challenge.forohub.persistence.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
@Table(name = "usuarios")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Usuario implements UserDetails {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column()
  private String name;

  @Column
  private String email;

  @Column
  private String username;

  @Column()
  private String password;

  @ElementCollection(fetch = FetchType.EAGER)
  private Set<String> roles; // Example roles: ["ROLE_ADMIN", "ROLE_USER"]

  @Column()
  private LocalDateTime createdAt;

  @Column()
  private LocalDateTime updatedAt;

//  @Override
//  public String getUsername() {
//    return username;
//  }
//
//  @Override
//  public String getPassword() {
//    return password;
//  }

//  ----------------------------------------------------------------

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return roles.stream()
        .map(role -> new SimpleGrantedAuthority(role))
        .collect(Collectors.toList());
  }

  @Override
  public boolean isAccountNonExpired() {
    return true; // Cambia según tu lógica de negocio
  }

  @Override
  public boolean isAccountNonLocked() {
    return true; // Cambia según tu lógica de negocio
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true; // Cambia según tu lógica de negocio
  }

  @Override
  public boolean isEnabled() {
    return true; // Cambia según tu lógica de negocio
  }
}
