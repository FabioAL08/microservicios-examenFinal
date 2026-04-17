package com.example.ms_usuarios.service;

import com.example.ms_usuarios.model.Usuario;
import com.example.ms_usuarios.model.dto.RegistroRequest;
import com.example.ms_usuarios.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public Usuario registrar(RegistroRequest request) {
        log.info("Registrando nuevo usuario: {}", request.getUsername());

        // Validaciones
        if (usuarioRepository.existsByUsername(request.getUsername())) {
            log.warn("Intento de registro con username ya existente: {}", request.getUsername());
            throw new RuntimeException("El username ya está en uso");
        }

        if (usuarioRepository.existsByEmail(request.getEmail())) {
            log.warn("Intento de registro con email ya existente: {}", request.getEmail());
            throw new RuntimeException("El email ya está en uso");
        }

        Usuario usuario = new Usuario();
        usuario.setUsername(request.getUsername());
        usuario.setPassword(passwordEncoder.encode(request.getPassword()));
        usuario.setEmail(request.getEmail());
        usuario.setNombre(request.getNombre());
        usuario.setApellido(request.getApellido());
        // Asignar rol USER por defecto - más seguro que permitir al usuario elegir
        usuario.setRoles(List.of("USER"));
        usuario.setActivo(true);

        Usuario savedUser = usuarioRepository.save(usuario);
        log.info("Usuario registrado exitosamente con ID: {}", savedUser.getId());

        return savedUser;
    }

    public Usuario findByUsername(String username) {
        log.debug("Buscando usuario por username: {}", username);
        return usuarioRepository.findByUsername(username)
                .orElseThrow(() -> {
                    log.error("Usuario no encontrado: {}", username);
                    return new RuntimeException("Usuario no encontrado");
                });
    }

    public Usuario findByEmail(String email) {
        log.debug("Buscando usuario por email: {}", email);
        return usuarioRepository.findByEmail(email)
                .orElseThrow(() -> {
                    log.error("Usuario no encontrado con email: {}", email);
                    return new RuntimeException("Usuario no encontrado");
                });
    }

    public List<Usuario> findAll() {
        log.debug("Listando todos los usuarios");
        return usuarioRepository.findAll();
    }

    public boolean existsByUsername(String username) {
        return usuarioRepository.existsByUsername(username);
    }

    public boolean existsByEmail(String email) {
        return usuarioRepository.existsByEmail(email);
    }
}