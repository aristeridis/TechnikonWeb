package gr.codehub.ed.technikonweb.resources;

import gr.codehub.ed.technikonweb.exceptions.CustomException;
import gr.codehub.ed.technikonweb.exceptions.ResourceNotFoundException;
import gr.codehub.ed.technikonweb.models.Owner;
import gr.codehub.ed.technikonweb.models.Property;
import gr.codehub.ed.technikonweb.models.Repair;
import gr.codehub.ed.technikonweb.services.AdminService;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import lombok.extern.slf4j.Slf4j;

/**
 *
 * @author alexandrosaristeridis
 */
@Path("Admin")
@Slf4j
@RequestScoped
public class AdminResource {

	@Inject
	private AdminService technikonService;

	/**
	 *
	 * @return
	 */
	@Path("/dayRepairs")
	@GET
	@Produces("application/json")
	public List<Repair> getAllRepairsOfTheDay() {

		try {
			return technikonService.getPendingRepairs();
		} catch (ResourceNotFoundException rnfe) {
			Logger.getLogger(OwnerResource.class.getName()).log(Level.SEVERE, null, rnfe);
		}
		return null;
	}

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
				.orElseThrow(() -> new ResourceNotFoundException("Property not found with id: " + repair));

		} catch (CustomException ce) {
			Logger.getLogger(OwnerResource.class.getName()).log(Level.SEVERE, null, ce);
			throw ce;
		}
	}

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
				.orElseThrow(() -> new ResourceNotFoundException("Property not found with id: " + repair));
		} catch (ResourceNotFoundException rnfe) {
			Logger.getLogger(OwnerResource.class.getName()).log(Level.SEVERE, null, rnfe);
			throw rnfe;

		}
	}

	/**
	 *
	 * @param date
	 * @return
	 */
	@Path("/repair/date/{repairDate}")
	@GET
	@Produces("application/json")
	public List<Repair> findByDate(@PathParam("repairDate") Date date) {
		try {
			return technikonService.findByDate(date);

		} catch (ResourceNotFoundException rnfe) {
			Logger.getLogger(OwnerResource.class.getName()).log(Level.SEVERE, null, rnfe);

		}
		return null;
	}

	/**
	 *
	 * @param dateStart
	 * @param dateEnd
	 * @return
	 */
	@Path("/repair/dates/{repairDates}")
	@GET
	@Produces("application/json")
	public List<Repair> findByRangeOfDates(@PathParam("repairDates") Date dateStart, Date dateEnd) {
		try {
			return technikonService.findByRangeOfDates(dateStart, dateEnd);

		} catch (ResourceNotFoundException rnfe) {
			Logger.getLogger(OwnerResource.class.getName()).log(Level.SEVERE, null, rnfe);

		}
		return null;
	}

	/**
	 *
	 * @param propertyId
	 * @return
	 */
	@Path("/repair/propertyId/{propertyId}")
	@GET
	@Produces("application/json")
	public List<Repair> findByPropertyId(@PathParam("propertyId") Long propertyId) {
		try {
			return technikonService.findByPropertyId(propertyId);

		} catch (ResourceNotFoundException rnfe) {
			Logger.getLogger(OwnerResource.class.getName()).log(Level.SEVERE, null, rnfe);

		}
		return null;
	}

	/**
	 *
	 * @param vatNumber
	 * @return
	 */
	@Path("/owner/vat/{vatNumber}")
	@GET
	@Produces("application/json")
	public Owner findByVatNumber(@PathParam("vatNumber") Long vatNumber) {
		try {
			return technikonService.findByVatNumber(vatNumber)
				.orElseThrow(() -> new ResourceNotFoundException("Owner not found with vatNumber: " + vatNumber));

		} catch (ResourceNotFoundException rnfe) {
			Logger.getLogger(OwnerResource.class.getName()).log(Level.SEVERE, null, rnfe);
			throw rnfe;
		}
	}

	/**
	 *
	 * @param email
	 * @return
	 */
	@Path("/owner/email/{email}")
	@GET
	@Produces("application/json")
	public Owner findByEmail(@PathParam("email") String email) {
		try {
			return technikonService.findOwnerByEmail(email)
				.orElseThrow(() -> new ResourceNotFoundException("Owner not found with email: " + email));
		} catch (ResourceNotFoundException rnfe) {
			Logger.getLogger(OwnerResource.class.getName()).log(Level.SEVERE, null, rnfe);
			throw rnfe;
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

			return technikonService.updateProperty(newProperty).orElseThrow(() -> new ResourceNotFoundException("Couldnt owner: " + property.getOwner()));

		}
		return null;
	}

	/**
	 *
	 * @param propertyId
	 * @return
	 */
	@Path("/property/id/{propertyId}")
	@GET
	@Produces("application/json")
	public Property findPropertyById(@PathParam("propertyId") Long propertyId) {
		try {
			return technikonService.findPropertyById(propertyId)
				.orElseThrow(() -> new ResourceNotFoundException("Property not found with id: " + propertyId));

		} catch (ResourceNotFoundException rnfe) {
			Logger.getLogger(OwnerResource.class.getName()).log(Level.SEVERE, null, rnfe);
			throw rnfe;

		}
	}

	/**
	 *
	 * @param vatNumber
	 * @return
	 */
	@Path("/property/vat/{vatNumber}")
	@GET
	@Produces("application/json")
	public List<Property> findPropertyByVatNumber(@PathParam("vatNumber") Long vatNumber) {
		try {
			Optional<Owner> owner = technikonService.findByVatNumber(vatNumber);
			return technikonService.findPropertyByOwnerId(owner.get().getOwnerId());

		} catch (ResourceNotFoundException rnfe) {
			Logger.getLogger(OwnerResource.class.getName()).log(Level.SEVERE, null, rnfe);

		}
		return null;
	}
}
