package br.com.evernot.project.service.impl;


import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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

        return toResponse(savedNote);
      }


    @Override
    public NoteResponseDto updateNote(UUID userId, UUID noteId, NoteRequestDto req){
        
        NoteEntity note = noteRepository.findById(noteId)
            .orElseThrow(()-> new RuntimeException("Note not found"));

        if(!note.getUser().getId().equals(userId)){
            throw new RuntimeException("Incorrect note owner");
        }

        note.setTitle(req.title());
        note.setContent(req.content());

        NoteEntity savedNote = noteRepository.save(note);

        return toResponse(savedNote);
        
    }




    @Override
    public List<NoteResponseDto> userNotes(UUID id){

        return noteRepository.findAllByUserId(id).stream()
        .map(this::toResponse)
        .collect(Collectors.toList());
        
    }

    @Override
    public void deleteNote(UUID userId, UUID noteId){

        NoteEntity note = noteRepository.findById(noteId)
        .orElseThrow(()-> new RuntimeException("Note not found"));

        if(!note.getUser().getId().equals(userId)){
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
