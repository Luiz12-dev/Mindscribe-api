package br.com.evernot.project.dto;

import jakarta.validation.constraints.NotBlank;

public record NoteRequestDto(
    @NotBlank
    String title,

    @NotBlank
    String content
) {

}
