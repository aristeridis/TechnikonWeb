package gr.codehub.ed.technikonweb.resources;

import gr.codehub.ed.technikonweb.models.Owner;
import gr.codehub.ed.technikonweb.services.OwnerService;
import gr.codehub.ed.technikonweb.models.Property;
import gr.codehub.ed.technikonweb.exceptions.CustomException;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import lombok.extern.slf4j.Slf4j;

@Path("Tachnikon")
@Slf4j
public class OwnerResource {

    @Inject
    private OwnerService technikonService;

    @Path("owner")
    @GET
    public String home() {
        return "Welcome to owner page";
    }
    
    //get all properties of the owner
    @Path("owner/{ownerId}")
    @GET
    @Produces("owner/json")
    public List<Property> getPropertiesByOwnerId(@PathParam("ownerId") long ownerId) {
        try {
            return technikonService.getPropertiesByOwnerId(ownerId);
        } catch (NotFoundException e) {
            return null;
        }
    }

    @Path("owner/property/{propertyId}")
    @GET
    @Produces("property/json")
    //details of the property by id
    public Optional<Property> findById(@PathParam("propertyid") Long propertyId) {
        return technikonService.findById(propertyId);
    }

    //create owner
    @Path("owner")
    @POST
    @Consumes("application/json")
    @Produces("application/json")
    public Owner save(Owner owner) {
        try {
            technikonService.save(owner);
            return owner;
        } catch (CustomException ce) {
            Logger.getLogger(OwnerResource.class.getName()).log(Level.SEVERE, null, ce);
        }
        return new Owner();
    }

    //property delete
    @Path("owner/property/{propertyId}")
    @DELETE
    @Consumes("application/json")
    @Produces("application/json")
    public boolean safeDeleteById(@PathParam("properyId") Long propertyId) {
        return technikonService.safeDeleteById(propertyId);

    }

    //update property
    @Path("owner/property")
    @POST
    @Consumes("application/json")
    @Produces("application/json")
    public Optional<Property> update(Property property) {
        return technikonService.update(property);
    }

}
