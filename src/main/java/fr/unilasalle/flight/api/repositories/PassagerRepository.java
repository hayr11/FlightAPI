package fr.unilasalle.flight.api.repositories;

import fr.unilasalle.flight.api.beans.Avion;
import fr.unilasalle.flight.api.beans.Passager;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.inject.Model;

import java.util.List;

@Model
public class PassagerRepository implements PanacheRepositoryBase<Passager, Long> {
    public List<Passager> findBySurname(String surnameParameter){
        return find("surname",surnameParameter).list();
    }

    public List<Passager> findByMail(String mailParameter){
        return find("email_address",mailParameter).list();
    }

}
