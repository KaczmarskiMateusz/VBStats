package pl.vbstats.User.Model;

import jakarta.persistence.*;
import lombok.*;
import pl.vbstats.Club.Model.Address;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private UUID externalId;

    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false, unique = true)
    private String username;
    @Column
    private String password;

    private String firstName;
    private String lastName;

    @Column(nullable = false, unique = true)
    private String phone;

    private LocalDate birthDate;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    @Builder.Default
    private boolean active = false;

    @Builder.Default
    private boolean confirmed = false;

    @Embedded
    private Address address;

    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        updatedAt = LocalDateTime.now();
    }

}
