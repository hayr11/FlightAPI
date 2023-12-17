package fr.unilasalle.flight.api.ressources;

import fr.unilasalle.flight.api.beans.Vol;
import fr.unilasalle.flight.api.beans.Reservation;
import fr.unilasalle.flight.api.repositories.ReservationRepository;
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

@Path("/reservations")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ReservationResource extends  GenericResource{

    @Inject
    ReservationRepository repository;
    @Inject
    VolRepository VolRepository;

    @Inject
    Validator validator;

    @GET
    public Response getFlight(@QueryParam("vol") String vol){

        List<Reservation> list;
        if(StringUtils.isNotBlank(vol))
            list = repository.findByFlightId(vol);
        else
            list = repository.listAll();

        return getOr404(list);
    }

    @POST
    @Transactional
    public Response createReservation(Reservation reservation){
        var violations = validator.validate(reservation);
        if(!violations.isEmpty()){
            return Response.status(400).entity(new ErrorWrapper(violations)).build();
        }
        if(reservation.getVol() != null && reservation.getVol().getId() != null){
            var flight = VolRepository.findByIdOptional(reservation.getVol().getId()).orElse(null);
            if(flight != null){
                reservation.setVol(flight);
                try {
                    repository.persistAndFlush(reservation);
                    return Response.ok().status(201).build();
                }catch (PersistenceException e){
                    return Response.status(500).entity(new ErrorWrapper("Erreur lors de l'enregistrement de la réservation")).build();
                }
            }else {
                return Response.status(400).entity(new ErrorWrapper("Le vol n'existe pas")).build();
            }
        }else{
            return Response.status(400).entity(new ErrorWrapper("Renseigner le vol")).build();
        }
    }
    @DELETE
    @Path("/{id}")
    @Transactional
    public Response getPlaneByID(@PathParam("id") String id){
        var msg = repository.deleteById(id);
        return Response.status(200).entity(new ErrorWrapper(msg)).build();
    }

}
