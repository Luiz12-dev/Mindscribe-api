package br.com.evernot.project.dto;


import jakarta.validation.constraints.NotBlank;

public record UserRequestDto(
    @NotBlank
    String username,
    @NotBlank
    String email,
    
    @NotBlank
    String password

) {



    

}
