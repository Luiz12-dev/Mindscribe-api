package br.com.evernot.project.service.impl;


import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import br.com.evernot.project.domain.NoteEntity;
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
    public NoteResponseDto createNote(NoteRequestDto req, String userEmail){

        var user = userRepository.findByEmail(userEmail)
        .orElseThrow(()-> new RuntimeException("Usuário não econtrado"));  


        NoteEntity newNote = new NoteEntity();

        newNote.setContent(req.content());
        newNote.setTitle(req.title());
        newNote.setUser(user);

        NoteEntity savedNote = noteRepository.save(newNote); 

        return toResponse(savedNote);
      }


    @Override
    public NoteResponseDto updateNote(String userEmail, UUID noteId, NoteRequestDto req){
        
        NoteEntity note = noteRepository.findById(noteId)
            .orElseThrow(()-> new RuntimeException("Note not found"));

        if(!note.getUser().getEmail().equals(userEmail)){
            throw new RuntimeException("Incorrect note owner");
        }

        note.setTitle(req.title());
        note.setContent(req.content());

        NoteEntity savedNote = noteRepository.save(note);

        return toResponse(savedNote);
        
    }




    @Override
    public List<NoteResponseDto> getAllNotes(String userEmail){

        var user = userRepository.findByEmail(userEmail)
        .orElseThrow(()-> new RuntimeException("Usuário não econtrado"));

        var notes = noteRepository.findAllByUserId(user.getId());

        return notes.stream()
        .map(note -> toResponse(note))
        .collect(Collectors.toList());
    }

    @Override
    public void deleteNote(String userEmail, UUID noteId){

        NoteEntity note = noteRepository.findById(noteId)
        .orElseThrow(()-> new RuntimeException("Note not found"));

        if(!note.getUser().getEmail().equals(userEmail)){
            throw new RuntimeException("Incorrect note owner");
        }

        noteRepository.delete(note);
    }


    public NoteResponseDto toResponse(NoteEntity req){
        return new NoteResponseDto(
            req.getId(),
            req.getTitle(),
            req.getContent(),
            req.getCreatedAt(),
            req.getUpdatedAt()
        );
    }
}
