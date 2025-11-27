package br.com.evernot.project.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record NoteResponseDto(
    UUID id,
    String title,
    String content,
    LocalDateTime created_at,
    LocalDateTime updated_at
    

) {}
