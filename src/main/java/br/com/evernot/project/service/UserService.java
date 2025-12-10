package br.com.evernot.project.service;

import br.com.evernot.project.dto.UserRequestDto;
import br.com.evernot.project.dto.UserResponseDto;

public interface UserService {

    UserResponseDto createUser(UserRequestDto req);

    
}
