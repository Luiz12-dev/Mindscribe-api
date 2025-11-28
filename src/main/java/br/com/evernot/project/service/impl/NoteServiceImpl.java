package br.com.evernot.project.service.impl;


import java.util.UUID;

import org.springframework.stereotype.Service;

import br.com.evernot.project.domain.NoteEntity;
import br.com.evernot.project.domain.UserEntity;
import br.com.evernot.project.dto.NoteRequestDto;
import br.com.evernot.project.dto.NoteResponseDto;
import br.com.evernot.project.repository.NoteRepository;
import br.com.evernot.project.repository.UserRepository;
import br.com.evernot.project.service.NoteService;

@Service
public class NoteServiceImpl implements NoteService{

    private final NoteRepository noteRepository;
    private final UserRepository userRepository;

    public NoteServiceImpl(NoteRepository noteRepository, UserRepository userRepository) {
        this.noteRepository = noteRepository;
        this.userRepository = userRepository;
    }

    @Override
    public NoteResponseDto createNote(NoteRequestDto req, UUID Id){

        UserEntity user = userRepository.findById(Id)
        .orElseThrow(()-> new RuntimeException("Usuário não econtrado"));


        NoteEntity newNote = new NoteEntity();

        newNote.setContent(req.content());
        newNote.setTitle(req.title());
        newNote.setUser(user);

        NoteEntity savedNote = noteRepository.save(newNote);  
        return NoteResponseDto.sucess(savedNote);
      }
}
