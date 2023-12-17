package fr.unilasalle.flight.api.beans;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "planes")
@Getter
@Setter
@NoArgsConstructor

public class Avion extends PanacheEntityBase {
    @Id
    @SequenceGenerator(name = "planes_sequence_in_java_code", sequenceName = "planes_sequence_in_database",allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "planes_sequence_in_java_code")
    private Long id;

    @NotBlank(message = "Le fabricant ne doit pas être vide")
    @Column(nullable = false)
    private String operator;

    @NotBlank(message = "Le model ne doit pas être vide")
    @Column(nullable = false)
    private String model;

    @NotBlank(message = "L'immatriculation ne doit pas être vide")
    @Size(max = 6, message = "l'immatriculation doit faire au maximum 6 caractères")
    @Column(nullable = false, unique = true)
    private String immatriculation;

    @NotNull(message = "La capacité doit être fournie")
    @Min(value = 2,message = "La capacité de l'avion doit être supérieur à 2")
    @Column(nullable = false)
    private Integer capacity;

}
