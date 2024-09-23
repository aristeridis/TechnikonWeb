
package gr.codehub.ed.technikonweb.resources;


import gr.codehub.ed.technikonweb.services.OwnerService;
import gr.codehub.ed.technikonweb.models.Property;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
 @Path("Tachnikon")
 @Slf4j
public class OwnerTechnikon {
    @Inject
    private OwnerService ownerService;

    @Path("owner")
    @GET
    public String home(){
        return "Welcome to owner page";
    }
    @Path("owner/{ownerId}")
    @GET
    @Produces("owner/json")
    public List<Property> getPropertiesByOwnerId(@PathParam("ownerId") long ownerId){ 
        try {
           return ownerService.getPropertiesByOwnerId(ownerId);
        } catch (NotFoundException e) {
            return null;
        }
    }
        public List<Property> findByOwnerId(@PathParam("ownerId")Long ownerId) {
        return null;}

    
}