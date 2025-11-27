package br.com.evernot.project.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;

public record NoteRequestDto(
    @NotBlank
    String title,

    @NotBlank
    @Column(columnDefinition = "TEXT")
    String content
) {

}
