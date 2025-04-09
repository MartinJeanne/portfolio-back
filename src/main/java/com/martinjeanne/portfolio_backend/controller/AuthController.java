package com.martinjeanne.portfolio_backend.controller;

import com.martinjeanne.portfolio_backend.configuration.JwtService;
import com.martinjeanne.portfolio_backend.model.dto.AuthResponseDto;
import com.martinjeanne.portfolio_backend.model.dto.UserDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final JwtService jwtService;

    @PostMapping("login")
    public ResponseEntity<AuthResponseDto> login(@RequestBody @Valid UserDto userDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        userDto.getUsername(),
                        userDto.getPassword()
                )
        );

        UserDetails userDetails = userDetailsService.loadUserByUsername(userDto.getUsername());

        String token = jwtService.generateToken(userDetails);
        return ResponseEntity.ok(new AuthResponseDto(token));
    }
}
