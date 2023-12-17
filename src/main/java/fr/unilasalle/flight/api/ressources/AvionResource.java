package fr.unilasalle.flight.api.ressources;

import fr.unilasalle.flight.api.beans.Avion;
import fr.unilasalle.flight.api.repositories.AvionRepository;
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

@Path("/avions")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AvionResource extends GenericResource{

    @Inject
    AvionRepository repository;

    @Inject
    Validator validator;

    @GET
    public Response getPlanes(@QueryParam("operator") String operator){

        List<Avion> list;
        if(StringUtils.isNotBlank(operator))
            list = repository.findByOperator(operator);
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

    @POST
    @Transactional
    public Response createPlane(Avion avion){
        var violations = validator.validate(avion);
        if(!violations.isEmpty()){
            return Response.status(400).entity(new ErrorWrapper(violations)).build();
        }
        try {
            repository.persistAndFlush(avion);
            return Response.ok().status(201).build();
        }catch (PersistenceException e){
            return Response.serverError().entity(new ErrorWrapper("Erreur lors de l'enregistrement")).build();
        }
    }

}
