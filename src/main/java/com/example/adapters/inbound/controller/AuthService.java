package com.example.adapters.inbound.controller;


import com.example.adapters.outbound.repository.entities.Role;
import com.example.application.JwtService;
import com.example.application.UserEntityService;
import com.example.domain.exception.UnauthorizationException;
import lombok.RequiredArgsConstructor;
import org.openapitools.model.UserLoginDto;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class AuthService {

    private final UserEntityService userEntityService;
    private final JwtService jwtService;

    public String login(UserLoginDto userLogin) {
        return userEntityService
                .findByUsername(userLogin.getUsername())
                .map(user -> {
                    if (user.getPassword().equals(userLogin.getPassword())) {
                        // Obtener los roles del usuario
                        List<String> roles = user.getRoles().stream()
                                .map(Role::getName)
                                .toList();

                        // Generar el token JWT usando el username y los roles
                        return jwtService.generateToken(user.getUsername(), roles);
                    }
                    throw new UnauthorizationException();
                })
                .orElseThrow(UnauthorizationException::new);
    }

    //TODO Codificar el password en la base de datos
}
