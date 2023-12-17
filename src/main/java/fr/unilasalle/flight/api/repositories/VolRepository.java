package fr.unilasalle.flight.api.repositories;

import fr.unilasalle.flight.api.beans.Vol;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.inject.Model;

import java.util.List;

@Model
public class VolRepository implements  PanacheRepositoryBase<Vol,Long>{
    public List<Vol> findByDestination(String destinationParameter){
        return find("destination",destinationParameter).list();
    }

    public List<Vol> findByNumber(String numberParameter){
        return find("number",numberParameter).list();
    }

    public String deleteByNumber(String numberParameter){
        try{
            delete("number",numberParameter);
            return "Vol supprimé";
        }catch (Exception e){
            return "Erreur dans la suppression du vol";
        }
    }
}
