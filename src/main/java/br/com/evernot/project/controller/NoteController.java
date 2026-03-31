package br.com.evernot.project.controller;
import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.evernot.project.domain.UserEntity;
import br.com.evernot.project.dto.NoteRequestDto;
import br.com.evernot.project.dto.NoteResponseDto;
import br.com.evernot.project.service.NoteService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/notes")
public class NoteController {

    private final NoteService noteService;

    public NoteController(NoteService noteService){
        this.noteService=noteService;
    }

    @PostMapping
    public ResponseEntity<NoteResponseDto> createNote(@Valid @RequestBody NoteRequestDto req, @AuthenticationPrincipal UserEntity usuarioLogado){

       String emailUsuario = usuarioLogado.getEmail();

       NoteResponseDto createdNote = noteService.createNote(req, emailUsuario);

        return ResponseEntity.status(HttpStatus.CREATED).body(createdNote);
    }

    @GetMapping
    public ResponseEntity<List<NoteResponseDto>> getMyNotes(Authentication authentication){
        var userEmail = authentication.getName();

        List<NoteResponseDto> notes = noteService.getAllNotes(userEmail);

        return ResponseEntity.ok(notes);
    }

    @PutMapping("/{noteId}")
    public ResponseEntity<NoteResponseDto> updateNote(@AuthenticationPrincipal UserEntity user, @PathVariable UUID noteId, @Valid @RequestBody NoteRequestDto req ){

        String userEmail = user.getEmail();

        NoteResponseDto updatedNote = noteService.updateNote( userEmail, noteId, req);
        return ResponseEntity.ok(updatedNote);
    }

    @DeleteMapping("/{noteId}")
    public ResponseEntity<Void> deleteNote(@AuthenticationPrincipal UserEntity userEntity, @PathVariable UUID noteId){
        
        noteService.deleteNote(userEntity.getEmail(), noteId);
    
        return ResponseEntity.noContent().build();
    }

}
