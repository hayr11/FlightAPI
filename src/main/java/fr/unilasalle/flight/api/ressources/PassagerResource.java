package fr.unilasalle.flight.api.ressources;

import fr.unilasalle.flight.api.beans.Avion;
import fr.unilasalle.flight.api.beans.Passager;
import fr.unilasalle.flight.api.beans.Reservation;
import fr.unilasalle.flight.api.repositories.AvionRepository;
import fr.unilasalle.flight.api.repositories.PassagerRepository;
import io.netty.util.internal.StringUtil;
import jakarta.inject.Inject;
import jakarta.persistence.PersistenceException;
import jakarta.transaction.Transactional;
import jakarta.validation.Validator;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Link;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

@Path("/passagers")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PassagerResource extends GenericResource{

    @Inject
    PassagerRepository repository;

    @Inject
    Validator validator;

    @GET
    public Response getPlanes(@QueryParam("mail") String mail){

        List<Passager> list;
        if(StringUtils.isNotBlank(mail))
            list = repository.findByMail(mail);
        else
            list = repository.listAll();

        return getOr404(list);
    }

    @GET
    @Path("/{id}")
    public Response getPlaneByID(@PathParam("id") Long id){
        var plane = repository.findByIdOptional(id).orElse(null);
        return getOr404(plane);
    }

    @PUT
    @Transactional
    @Path("/{id}")
    public Response setPassagerByMId(@PathParam("id") Long id, Passager passager){
        if(id != null) {
            Passager bddPassager = repository.findByIdOptional(id).orElse(null);
            if(bddPassager != null) {
                bddPassager.setFirstname(passager.getFirstname());
                bddPassager.setSurname(passager.getSurname());
                bddPassager.setEmail_address(passager.getEmail_address());
                try {
                    repository.persistAndFlush(bddPassager);
                    return Response.status(200).entity(new ErrorWrapper("Passager Updated")).build();
                } catch (PersistenceException e) {
                    return Response.status(500).entity(new ErrorWrapper("Erreur lors de la mise à jour du passager")).build();
                }
            }else{
                return Response.status(400).entity(new ErrorWrapper("Le passager n'existe pas")).build();
            }
        }else{
            return Response.status(400).entity(new ErrorWrapper("Le passager n'est pas connu")).build();
        }

    }

}
