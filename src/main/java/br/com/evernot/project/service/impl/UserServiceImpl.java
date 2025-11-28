package br.com.evernot.project.service.impl;


import org.springframework.stereotype.Service;

import br.com.evernot.project.domain.UserEntity;
import br.com.evernot.project.dto.UserRequestDto;
import br.com.evernot.project.dto.UserResponseDto;
import br.com.evernot.project.repository.UserRepository;
import br.com.evernot.project.service.UserService;
import lombok.RequiredArgsConstructor;
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;

    public UserResponseDto createUser(UserRequestDto req){
        

        if(userRepository.findByEmail(req.email()).isPresent()){

            throw new RuntimeException("Email already Registred");

        }

        UserEntity newUser = new UserEntity();

        newUser.setEmail(req.email());
        newUser.setUsername(req.username());
        newUser.setPassword(req.password());

        UserEntity savedUser = userRepository.save(newUser);

        return new UserResponseDto(
            savedUser.getId(),
            savedUser.getUsername(),
            savedUser.getEmail());

    }



}
