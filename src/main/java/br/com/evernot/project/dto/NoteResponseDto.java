package br.com.evernot.project.dto;

import java.time.LocalDateTime;
import java.util.UUID;


public record NoteResponseDto(
    UUID id,
    String title,
    String content,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {
}
