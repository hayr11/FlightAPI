package fr.unilasalle.flight.api.beans;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "passagers")
@Getter
@Setter
@NoArgsConstructor


public class Passager {
    @Id
    @SequenceGenerator(name = "passagers_sequence_in_java_code", sequenceName = "passagers_sequence_in_database",allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "passagers_sequence_in_java_code")
    private Long id;

    @NotBlank(message = "Le prénom ne doit pas être vide")
    @Column(nullable = false)
    private String surname;

    @NotBlank(message = "Le nom ne doit pas être vide")
    @Column(nullable = false)
    private String firstname;

    @NotBlank(message = "L'adresse ne doit pas être vide")
    @Column(nullable = false, unique = true)
    private String email_address;

}
