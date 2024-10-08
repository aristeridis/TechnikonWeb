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
import jakarta.ws.rs.core.Response;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import lombok.extern.slf4j.Slf4j;

/**
 *
 * @author alexandrosaristeridis
 */
@Path("Owner")
@Slf4j
@RequestScoped
public class OwnerResource {

	@Inject
	private OwnerService technikonService;

//	@Path("/owner")
//	@GET
//	public String owner() {
//		return "Welcome to owner page";
//	}

	//update owner

	/**
	 *
	 * @param owner
	 * @return
	 */
	@Path("/owner")
	@PUT
	@Consumes("application/json")
	@Produces("application/json")
	public Owner updateOwner(Owner owner) {
		log.debug("Owner: " + owner.getSurName() + owner.getName());
		try {
			technikonService.updateOwner(owner);
			return owner;
		} catch (OwnerNotFoundException onfe) {
			Logger.getLogger(OwnerResource.class.getName()).log(Level.SEVERE, null, onfe);
		}
		return new Owner();
	}

	//owner delete

	/**
	 *
	 * @param ownerId
	 * @return
	 */
	@Path("/owner/{ownerId}")
	@DELETE
	@Consumes("application/json")
	@Produces("application/json")
	public boolean ownerDeleteById(@PathParam("ownerId") Long ownerId) {
		try {
			return technikonService.deleteOwnerById(ownerId);

		} catch (CustomException ce) {
			Owner owner = new Owner();
			owner.setOwnerId(-1);
			return false;
		}
	}

	//get all properties of the owner

	/**
	 *
	 * @param ownerId
	 * @return
	 */
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

	/**
	 *
	 * @param propertyId
	 * @return
	 */
	@Path("/property/{propertyId}")
	@GET
	@Produces("text/json")
	public Property propertyFindById(@PathParam("propertyId") Long propertyId) {
		try {
			return technikonService.findById(propertyId)
				.orElseThrow(() -> new ResourceNotFoundException("Property not found with id: " + propertyId));

		} catch (ResourceNotFoundException rnfe) {
			Logger.getLogger(OwnerResource.class.getName()).log(Level.SEVERE, null, rnfe);
			throw rnfe;
		}
	}

	//property delete

	/**
	 *
	 * @param propertyId
	 * @return
	 */
	@Path("/property/{propertyId}")
	@DELETE
	@Consumes("application/json")
	@Produces("application/json")
	public boolean propertyDeleteById(@PathParam("propertyId") Long propertyId) {
		try {
			return technikonService.safeDeleteById(propertyId);

		} catch (CustomException ce) {
			Property property = new Property();
			property.setPropertyCode(-1);
			return false;
		}
	}

	//create repair
	/**
	 *
	 * @param repair
	 * @return
	 */
	@Path("/repair")
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	public Repair createRepair(Repair repair) {
		log.debug("repair: " + repair.getProperty());

		try {
			return technikonService.createRepair(repair)
				.orElseThrow(() -> new ResourceNotFoundException("Couldnt create repair: " + repair));

		} catch (CustomException ce) {
			Logger.getLogger(OwnerResource.class.getName()).log(Level.SEVERE, null, ce);
			throw ce;

		}
	}
	//update repair

	/**
	 *
	 * @param repair
	 * @return
	 */
	@Path("/repair")
	@PUT
	@Consumes("application/json")
	@Produces("application/json")
	public Repair updateRepair(Repair repair) {
		log.debug("Property:" + repair.getProperty());
		try {
			return technikonService.updateRepair(repair)
				.orElseThrow(() -> new ResourceNotFoundException("Couldnt update repair: " + repair));

		} catch (ResourceNotFoundException rnfe) {
			Logger.getLogger(OwnerResource.class.getName()).log(Level.SEVERE, null, rnfe);
			throw rnfe;

		}

	}

	//repair delete

	/**
	 *
	 * @param repairId
	 * @return
	 */
	@Path("/repair/{repairId}")
	@DELETE
	@Consumes("application/json")
	@Produces("application/json")
	public boolean repairDeleteById(@PathParam("repairId") Long repairId) {
		try {
			return technikonService.deleteRepairById(repairId);

		} catch (CustomException ce) {
			Property property = new Property();
			property.setPropertyCode(-1);
			return false;
		}
	}

	/**
	 *
	 * @param ownerId
	 * @param property
	 * @return
	 */
	@Path("/property/{ownerId}")
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	public Property createPropertyByOwnerId(@PathParam("ownerId") Long ownerId, Property property) {

		Optional<Owner> optionalOwner = technikonService.findOwnerById(ownerId);

		if (optionalOwner.isPresent()) {
			Owner owner = optionalOwner.get();
			Property newProperty = new Property(
				property.getPropertyCode(),
				property.getAddress(),
				property.getYearOfConstruction(),
				property.getPropertyType(),
				owner
			);

			return technikonService.saveProperty(newProperty).orElseThrow(() -> new ResourceNotFoundException("Couldnt owner: " + property.getOwner()));

		}
		return null;
	}
	
	/**
	 *
	 * @param ownerId
	 * @param property
	 * @return
	 */
	@Path("/property/{ownerId}")
	@PUT
	@Consumes("application/json")
	@Produces("application/json")
	public Property updatePropertyByOwnerId(@PathParam("ownerId") Long ownerId, Property property) {

		Optional<Owner> optionalOwner = technikonService.findOwnerById(ownerId);

		if (optionalOwner.isPresent()) {
			Owner owner = optionalOwner.get();
			Property newProperty = new Property(
				property.getPropertyCode(),
				property.getAddress(),
				property.getYearOfConstruction(),
				property.getPropertyType(),
				owner
			);

			return technikonService.updatePorperty(newProperty).orElseThrow(() -> new ResourceNotFoundException("Couldnt owner: " + property.getOwner()));

		}
		return null;
	}

	/**
	 *
	 * @param property
	 * @return
	 */
	@Path("/property")
	@PUT
	@Consumes("application/json")
	@Produces("application/json")
	public Property updateProperty(Property property) {
//		log.debug("Property:" + property.getPropertyId());
		try {
			return technikonService.updatePorperty(property)
				.orElseThrow(() -> new ResourceNotFoundException("Property not found with id: " + property));
		} catch (ResourceNotFoundException rnfe) {
			Logger.getLogger(OwnerResource.class.getName()).log(Level.SEVERE, null, rnfe);
			throw rnfe;

		}
	}
}
