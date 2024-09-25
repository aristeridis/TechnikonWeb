package gr.codehub.ed.technikonweb.resources;

import gr.codehub.ed.technikonweb.models.Owner;
import gr.codehub.ed.technikonweb.services.OwnerService;
import gr.codehub.ed.technikonweb.models.Property;
import gr.codehub.ed.technikonweb.exceptions.CustomException;
import gr.codehub.ed.technikonweb.exceptions.OwnerNotFoundException;
import gr.codehub.ed.technikonweb.exceptions.ResourceNotFoundException;
import gr.codehub.ed.technikonweb.models.Repair;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import lombok.extern.slf4j.Slf4j;

@Path("Owner")
@Slf4j
@RequestScoped
public class OwnerResource {

	@Inject
	private OwnerService technikonService;

//    @Path("owner")
//    @GET
//    public String home() {
//        return "Welcome to owner page";
//    }
	//get all properties of the owner
	@Path("/{ownerId}")
	@GET
	@Produces("text/json")
	public List<Property> getPropertiesByOwnerId(@PathParam("ownerId") long ownerId) {
		try {
			return technikonService.getPropertiesByOwnerId(ownerId);
		} catch (OwnerNotFoundException onfe) {
			onfe.getMessage();
			return null;
		}
	}

	@Path("/property/{propertyId}")
	@GET
	@Produces("text/json")
	//details of the property by id
	public Optional<Property> findById(@PathParam("propertyId") Long propertyId) {
		try {
			return technikonService.findById(propertyId);

		} catch (ResourceNotFoundException rnfe) {
			Logger.getLogger(OwnerResource.class.getName()).log(Level.SEVERE, null, rnfe);

		}
		return null;
	}

	//create owner
	@Path("owner")
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	public Owner createOwner(Owner owner) {
		log.debug("Owner: " + owner.getSurName() + owner.getName());
		try {
			technikonService.saveOwner(owner);
			return owner;
		} catch (OwnerNotFoundException onfe) {
			Logger.getLogger(OwnerResource.class.getName()).log(Level.SEVERE, null, onfe);
		}
		return new Owner();
	}

	//property delete
	@Path("/property/{propertyId}")
	@DELETE
	@Consumes("application/json")
	@Produces("application/json")
	public boolean safeDeleteById(@PathParam("propertyId") Long propertyId) {
		try {
			return technikonService.safeDeleteById(propertyId);

		} catch (CustomException ce) {
			Property property = new Property();
			property.setPropertyCode(-1);
			return false;
		}
	}

	//update property
	@Path("/property")
	@PUT
	@Consumes("application/json")
	@Produces("application/json")
	public Optional<Property> updatePorperty(Property property) {
		try {
			return technikonService.updatePorperty(property);

		} catch (ResourceNotFoundException rnfe) {
			rnfe.getMessage();

		}
		return null;
	}

	//create property
	@Path("/property")
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	public Optional<Property> createProperty(Property property) {
		log.debug("property: " + property.getOwner());
		try {
			return technikonService.saveProperty(property);

		} catch (ResourceNotFoundException rnfe) {
			Logger.getLogger(OwnerResource.class.getName()).log(Level.SEVERE, null, rnfe);
		}
		return null;
	}

	//create repair
	@Path("/repair")
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	public Optional<Repair> createRepair(Repair repair) {
		log.debug("repair: " + repair.getProperty());

		try {
			return technikonService.saveRepair(repair);

		} catch (CustomException ce) {
			Logger.getLogger(OwnerResource.class.getName()).log(Level.SEVERE, null, ce);

		}
		return null;
	}

	//update repair
	@Path("/repair")
	@PUT
	@Consumes("application/json")
	@Produces("application/json")
	public Optional<Repair> updateRepair(Repair repair) {
		log.debug("Property:" + repair.getProperty());
		try {
			return technikonService.updateRepair(repair);

		} catch (ResourceNotFoundException rnfe) {
			Logger.getLogger(OwnerResource.class.getName()).log(Level.SEVERE, null, rnfe);

		}
		return null;
	}
}
