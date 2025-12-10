package br.com.evernot.project.service;

import java.util.List;
import java.util.UUID;

import br.com.evernot.project.dto.NoteRequestDto;
import br.com.evernot.project.dto.NoteResponseDto;

public interface NoteService {

    NoteResponseDto createNote(NoteRequestDto req, UUID Id);

    List<NoteResponseDto> userNotes(UUID id);

    NoteResponseDto updateNote(UUID userId, UUID noteId,NoteRequestDto req);

    void deleteNote(UUID userId, UUID noteId);

}
