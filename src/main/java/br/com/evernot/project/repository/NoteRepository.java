package br.com.evernot.project.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.evernot.project.domain.NoteEntity;

public interface NoteRepository extends JpaRepository<NoteEntity, UUID>{
    
}
