package fr.unilasalle.flight.api.repositories;

import fr.unilasalle.flight.api.beans.Avion;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.inject.Model;

import java.util.*;

@Model
public class AvionRepository implements PanacheRepositoryBase<Avion, Long> {

    /*public void oui(){
        this.listAll();
        this.findByIdOptional();
        this.persist();
    }*/

    public List<Avion> findByOperator(String operatorParameter){
        return find("operator",operatorParameter).list();
    }

}
