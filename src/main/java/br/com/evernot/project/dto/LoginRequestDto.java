package br.com.evernot.project.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginRequestDto(
    @NotBlank(message = "O email Deve ser obrigatório")
    @Email(message = "Digite o email em um formato válido")
    String email,
    
    @NotBlank(message = "A senha é obrigatória")
    String password
) {

}
