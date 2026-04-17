package com.example.ms_usuarios.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Respuesta del login con el token JWT")
public class LoginResponse {

    @Schema(description = "Token JWT para autenticación",
            example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...")
    private String token;

    @Schema(description = "Nombre de usuario autenticado", example = "juan.perez")
    private String username;

    @Schema(description = "Roles del usuario en el sistema", example = "[\"USER\", \"ADMIN\"]")
    private List<String> roles;
}















