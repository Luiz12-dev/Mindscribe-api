package br.com.evernot.project.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.evernot.project.domain.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, UUID>{
    Optional<UserEntity> findByEmail(String email);

}
