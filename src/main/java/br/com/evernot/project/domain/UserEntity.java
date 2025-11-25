package br.com.evernot.project.domain;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tb_users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotBlank(message = "The username cannot be empty")
    @Column(nullable = false)
    private String username;

    @Email(message = "It must be on email format")
    @NotBlank(message = "The email cannot be empty")
    @Column(nullable = false)
    private String email;

    @NotBlank(message = "The password cannot be empty")
    @JsonIgnore
    @Column(nullable = false)
    private String password;

    @CreationTimestamp
    @Column(nullable = false)
    private LocalDateTime created_at;

}
