
package gr.codehub.ed.technikonweb.resources;

import gr.ed.technikon.models.Property;
import gr.ed.technikon.services.OwnerService;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import java.util.List;
 @Path("Homepage")
public class OwnerHomepage {
    @Inject
    private OwnerService ownerService;
    @Path("home")
    @GET
    public String home(){
        return "Welcome to owner page";
    }
    @Path("owner/{ownerId}")
    @GET
    @Produces("owner/json")
    public List<Property> getPropertiesByOwnerId(@PathParam("ownerId") long ownerId){ try {
           return ownerService.getPropertiesByOwnerId(ownerId);
        } catch (NotFoundException e) {
            return null;
        }
    }
    
}