package fr.unilasalle.flight.api.beans;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Time;
import java.util.Date;

@Entity
@Table(name = "flights")
@Getter
@Setter
@NoArgsConstructor

public class Vol{
    @Id
    @SequenceGenerator(name = "flights_sequence_in_java_code", sequenceName = "flights_sequence_in_database",allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "flights_sequence_in_java_code")
    private Long id;

    @NotBlank(message = "Le nombre ne doit pas être vide")
    @Column(nullable = false, unique = true)
    private String number;

    @NotBlank(message = "L'origine ne doit pas être vide")
    @Column(nullable = false)
    private String origin;

    @NotBlank(message = "La destination ne doit pas être vide")
    @Column(nullable = false)
    private String destination;

    @NotNull(message = "La date de départ ne doit pas être vide")
    @Column(nullable = false)
    private Date departure_date;

    @NotNull(message = "L'heure de départ ne doit pas être vide")
    @Column(nullable = false)
    private Time departure_time;

    @NotNull(message = "La date d'arriver ne doit pas être vide")
    @Column(nullable = false)
    private Date arrival_date;

    @NotNull(message = "L'heure d'arriver ne doit pas être vide")
    @Column(nullable = false)
    private Time arrival_time;

    //Avion / planes
    @NotNull(message = "Il faut un avion pour le vol")
    //Voir doc CascadeType
    @ManyToOne(cascade = {CascadeType.MERGE})
    @JoinColumn(name = "plane_id", referencedColumnName = "id", nullable = false)
    private Avion avion;
}
