package com.example.ms_usuarios.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "usuarios")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Entidad que representa un usuario del sistema")
public class Usuario implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID único del usuario", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @Column(unique = true, nullable = false)
    @Schema(description = "Nombre de usuario único", example = "juan.perez", required = true)
    private String username;

    @Column(nullable = false)
    @Schema(description = "Contraseña del usuario (encriptada)", example = "$2a$10$...", accessMode = Schema.AccessMode.WRITE_ONLY)
    private String password;

    @Column(unique = true)
    @Schema(description = "Correo electrónico del usuario", example = "juan.perez@example.com")
    private String email;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "usuario_roles", joinColumns = @JoinColumn(name = "usuario_id"))
    @Column(name = "rol")
    @Schema(description = "Roles del usuario en el sistema", example = "[\"USER\", \"ADMIN\"]")
    private List<String> roles = new ArrayList<>();

    @Column(name = "nombre")
    @Schema(description = "Nombre del usuario", example = "Juan")
    private String nombre;

    @Column(name = "apellido")
    @Schema(description = "Apellido del usuario", example = "Pérez")
    private String apellido;

    @Column(name = "activo")
    @Schema(description = "Indica si la cuenta está activa", example = "true")
    private boolean activo = true;

    // Métodos requeridos por UserDetails
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream()
                .map(rol -> new SimpleGrantedAuthority("ROLE_" + rol))
                .collect(Collectors.toList());
    }

    @Override
    public boolean isAccountNonExpired() {
        return activo;
    }

    @Override
    public boolean isAccountNonLocked() {
        return activo;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return activo;
    }

    @Override
    public boolean isEnabled() {
        return activo;
    }
}