package fr.unilasalle.flight.api.ressources;

import fr.unilasalle.flight.api.beans.Avion;
import fr.unilasalle.flight.api.beans.Vol;
import fr.unilasalle.flight.api.repositories.AvionRepository;
import fr.unilasalle.flight.api.repositories.VolRepository;
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

@Path("/vols")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class VolResource extends  GenericResource{

    @Inject
    VolRepository repository;
    @Inject
    AvionRepository Avionrepository;

    @Inject
    Validator validator;

    /*@GET
    public Response getFlight(@QueryParam("destination") String destination){

        List<Vol> list = null;
        if(StringUtils.isNotBlank(destination))
            list = repository.findByDestination(destination);
        return getOr404(list);
    }*/

    @GET
    public Response getFlight(@QueryParam("destination") String destination){

        List<Vol> list;
        if(StringUtils.isNotBlank(destination))
            list = repository.findByDestination(destination);
        else
            list = repository.listAll();

        return getOr404(list);
    }

    @GET
    @Path("/{number}")
    public Response getPlaneByNumber(@PathParam("number") String number){
        List<Vol> list = null;
        if(StringUtils.isNotBlank(number))
            list = repository.findByNumber(number);
        return getOr404(list);
    }

    @POST
    @Transactional
    public Response createFlight(Vol vol){
        var violations = validator.validate(vol);
        if(!violations.isEmpty()){
            return Response.status(400).entity(new ErrorWrapper(violations)).build();
        }
        if(vol.getAvion() != null && vol.getAvion().getId() != null){
            var plane = Avionrepository.findByIdOptional(vol.getAvion().getId()).orElse(null);
            if(plane != null){
                vol.setAvion(plane);
                try {
                    repository.persistAndFlush(vol);
                    return Response.ok().status(201).build();
                }catch (PersistenceException e){
                    return Response.status(500).entity(new ErrorWrapper("Erreur lors de l'enregistrement du vol")).build();
                }
            }else {
                return Response.status(400).entity(new ErrorWrapper("L'avion n'existe pas")).build();
            }
        }else{
            return Response.status(400).entity(new ErrorWrapper("Renseigner l'avion")).build();
        }
    }

    @DELETE
    @Path("/{number}")
    @Transactional
    public Response getPlaneByID(@PathParam("number") String number){
        var msg = repository.deleteByNumber(number);
        return Response.status(200).entity(new ErrorWrapper(msg)).build();
    }

}
