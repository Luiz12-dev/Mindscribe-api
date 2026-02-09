package br.com.evernot.project.Service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.evernot.project.repository.NoteRepository;
import br.com.evernot.project.repository.UserRepository;
import br.com.evernot.project.service.impl.NoteServiceImpl;
import br.com.evernot.project.domain.NoteEntity;
import br.com.evernot.project.domain.UserEntity;
import br.com.evernot.project.dto.NoteRequestDto;
import br.com.evernot.project.dto.NoteResponseDto;

@ExtendWith(MockitoExtension.class)
public class NoteServiceTest {

    @Mock
    private NoteRepository noteRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks  
    private NoteServiceImpl noteService;

    @Test
    @DisplayName("Deve criar uma anotação com sucesso quando o usuário existir")
    void shouldCreateNoteSuccessfully()  {

     var email = "luizTeste@email.com";

     var userId = UUID.randomUUID();

     var requestDto = new NoteRequestDto("Estudar Java", "Como desenvolver Testes unitários");

     var userMock = new UserEntity();

     userMock.setEmail(email);

     userMock.setId(userId);


     var note = new NoteEntity();

     note.setContent(requestDto.content());

     note.setId(UUID.randomUUID());

     note.setTitle(requestDto.title());

     note.setUser(userMock);


     when(userRepository.findByEmail(email)).thenReturn(Optional.of(userMock));

     when(noteRepository.save(any(NoteEntity.class))).thenReturn(note);

     NoteResponseDto response = noteService.createNote(requestDto, email);

     assertNotNull(response);
     assertEquals("Estudar Java", response.title());

     verify(noteRepository, times(1)).save((any(NoteEntity.class)));

    }


    @Test
    @DisplayName("Deve lançar erro ao tentar criar nota para usuário inexistente")
    void shouldThrowExceptionWhenUserNotFound() {

        var email = "Unknown@Test.com";
        var requestDto = new NoteRequestDto("Aprender Angular", "Entender os conceitos diferentes entre conversação de TS com HTML");

        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class,() -> {
            noteService.createNote(requestDto, email);
        });

        verify(noteRepository, never()).save(any());


    }

    @Test
    @DisplayName("Deve excluir uma nota de um usuário existente")
    void shouldDeleteNoteSuccessfully() {

        var email = "deleteNote@email.com";

        var noteId = UUID.randomUUID();

        
        var user = new UserEntity();
        user.setId(UUID.randomUUID());
        user.setEmail(email);

        var note = new NoteEntity();

        note.setId(noteId);
        note.setUser(user);

        when(noteRepository.findById(noteId)).thenReturn(Optional.of(note));

        noteService.deleteNote(email, noteId);

        verify(noteRepository, times(1)).delete(note);

    }
    
}
