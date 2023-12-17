package fr.unilasalle.flight.api.beans;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "reservations")
@Getter
@Setter
@NoArgsConstructor

public class Reservation {
    @Id
    @SequenceGenerator(name = "reservations_sequence_in_java_code", sequenceName = "reservations_sequence_in_database",allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "reservations_sequence_in_java_code")
    private Long id;

    //Vol / flights
    @NotNull(message = "Il faut un vol pour la réservation")
    @ManyToOne(cascade = {CascadeType.MERGE})
    @JoinColumn(name = "flight_id", referencedColumnName = "id", nullable = false)
    private Vol vol;

    @NotNull(message = "Il faut un passager pour la réservation")
    @Column(nullable = false)
    private Long passenger_id;
}
