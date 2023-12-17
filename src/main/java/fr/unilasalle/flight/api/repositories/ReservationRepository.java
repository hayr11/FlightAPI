package fr.unilasalle.flight.api.repositories;

import fr.unilasalle.flight.api.beans.Reservation;
import fr.unilasalle.flight.api.beans.Vol;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.inject.Model;

import java.util.List;

@Model
public class ReservationRepository implements PanacheRepositoryBase<Reservation, Long> {

    public List<Reservation> findByFlightId(String flightParameter){
        return find("vol.id",flightParameter).list();
    }
    public String deleteById(String idParameter){
        try{
            delete("id",idParameter);
            return "Réservation supprimé";
        }catch (Exception e){
            return "Erreur dans la suppression de la réservation";
        }
    }
}
