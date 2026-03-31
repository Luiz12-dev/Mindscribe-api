package br.com.evernot.project.controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.evernot.project.dto.LoginRequestDto;
import br.com.evernot.project.dto.LoginResponseDto;
import br.com.evernot.project.repository.UserRepository;
import br.com.evernot.project.security.jwt.TokenService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final TokenService tokenService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody @Valid LoginRequestDto req){
        var user = userRepository.findByEmail(req.email()).orElse(null);

        if(user != null && passwordEncoder.matches(req.password(), user.getPassword())){

            String token = this.tokenService.generateToken(user);

            return ResponseEntity.ok(new LoginResponseDto(token));
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
        .body(Map.of(
            "message:", "Usuário ou senha inválidos"
        ));
    }


}
