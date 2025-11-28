package br.com.evernot.project.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.evernot.project.dto.UserRequestDto;
import br.com.evernot.project.dto.UserResponseDto;
import br.com.evernot.project.service.UserService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<UserResponseDto> register( @Valid @RequestBody UserRequestDto req){

        UserResponseDto res = userService.createUser(req);

        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }

    @GetMapping
    public String teste(){
        return "EndpointFuncioanndo corretamente !";
    }

}
