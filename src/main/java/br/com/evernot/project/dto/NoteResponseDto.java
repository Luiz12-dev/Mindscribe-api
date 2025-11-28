package br.com.evernot.project.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import br.com.evernot.project.domain.NoteEntity;

public record NoteResponseDto(
    UUID id,
    String title,
    String content,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {

    public static NoteResponseDto sucess(NoteEntity note){
        return new NoteResponseDto(
            note.getId(),
            note.getTitle(),
            note.getContent(),
            note.getCreatedAt(),
            note.getUpdatedAt()
        );
    }
}
