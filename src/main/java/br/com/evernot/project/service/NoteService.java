package br.com.evernot.project.service;

import java.util.UUID;

import br.com.evernot.project.dto.NoteRequestDto;
import br.com.evernot.project.dto.NoteResponseDto;

public interface NoteService {

    NoteResponseDto createNote(NoteRequestDto req, UUID Id);

}
