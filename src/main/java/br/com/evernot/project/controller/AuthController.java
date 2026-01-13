package br.com.evernot.project.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.evernot.project.domain.UserEntity;
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
    public ResponseEntity<LoginResponseDto> login(@Valid @RequestBody LoginRequestDto req){
        UserEntity user = userRepository.findByEmail(req.email())
            .orElseThrow(()-> new RuntimeException("user not found"));

        if(passwordEncoder.matches(req.password(), user.getPassword())){
            String token = tokenService.generateToken(user);

            return ResponseEntity.ok(new LoginResponseDto(token));
        }

        return ResponseEntity.badRequest().build();
    }


}
