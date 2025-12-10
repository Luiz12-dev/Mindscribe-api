package br.com.evernot.project.controller;
import java.util.List;
import java.util.UUID;

import org.apache.catalina.connector.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.evernot.project.dto.NoteRequestDto;
import br.com.evernot.project.dto.NoteResponseDto;
import br.com.evernot.project.service.NoteService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/users")
public class NoteController {

    private final NoteService noteService;

    public NoteController(NoteService noteService){
        this.noteService=noteService;
    }

    @PostMapping("/{userId}/notes")
    public ResponseEntity<NoteResponseDto> createNote(@Valid @RequestBody NoteRequestDto req, @PathVariable UUID userId){

        NoteResponseDto res = noteService.createNote(req, userId);
    
        return new ResponseEntity<>(res, HttpStatus.CREATED);
        
    }

    @GetMapping("/{userId}/notes")
    public ResponseEntity<List<NoteResponseDto>> userNotes(@PathVariable UUID userId){
        List<NoteResponseDto> allNotes = noteService.userNotes(userId);

        return ResponseEntity.ok(allNotes);
    }

    @PutMapping("/{userId}/notes/{noteId}")
    public ResponseEntity<NoteResponseDto> updateNote(@PathVariable UUID userId, @PathVariable UUID noteId, @Valid @RequestBody NoteRequestDto req ){
        NoteResponseDto updateNote = noteService.updateNote(userId, noteId, req);

        return ResponseEntity.ok(updateNote);
    }

    @DeleteMapping("/{userId}/notes/{noteId}")
    public ResponseEntity<Void> deleteNote(@PathVariable UUID userId, @PathVariable UUID noteId){

        noteService.deleteNote(userId, noteId);

    
        return ResponseEntity.noContent().build();
    }

}
