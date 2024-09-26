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
public class AdminResourve {

	@Inject
	private AdminService technikonService;

	/**
	 *
	 * @return
	 */
	@Path("")
	@GET
	@Consumes("application/json")
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
	public Optional<Repair> createRepair(Repair repair) {
		log.debug("repair: " + repair.getProperty());

		try {
			return technikonService.createRepair(repair);

		} catch (CustomException ce) {
			Logger.getLogger(OwnerResource.class.getName()).log(Level.SEVERE, null, ce);

		}
		return Optional.empty();
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
	public Optional<Repair> updateRepair(Repair repair) {
		log.debug("Property:" + repair.getProperty());
		try {
			return technikonService.updateRepair(repair);

		} catch (ResourceNotFoundException rnfe) {
			Logger.getLogger(OwnerResource.class.getName()).log(Level.SEVERE, null, rnfe);

		}
		return Optional.empty();
	}

	/**
	 *
	 * @param date
	 * @return
	 */
	@Path("/repair/{repairDate}")
	@GET
	@Consumes("application/json")
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
	@Path("/repair/{repairDates}")
	@GET
	@Consumes("application/json")
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
	@Path("/repair/{propertyId}")
	@GET
	@Consumes("application/json")
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
	@Path("/owner/{vatNumber}")
	@GET
	@Consumes("application/json")
	public Optional<Owner> findByVatNumber(@PathParam("vatNumber") Long vatNumber) {
		try {
			return technikonService.findByVatNumber(vatNumber);

		} catch (ResourceNotFoundException rnfe) {
			Logger.getLogger(OwnerResource.class.getName()).log(Level.SEVERE, null, rnfe);

		}
		return Optional.empty();
	}

	/**
	 *
	 * @param email
	 * @return
	 */
	@Path("/owner/{email}")
	@GET
	@Consumes("application/json")
	public Optional<Owner> findByVatNumber(@PathParam("email") String email) {
		try {
			return technikonService.findOwnerByEmail(email);

		} catch (ResourceNotFoundException rnfe) {
			Logger.getLogger(OwnerResource.class.getName()).log(Level.SEVERE, null, rnfe);

		}
		return Optional.empty();
	}

	/**
	 *
	 * @param property
	 * @return
	 */
	@Path("/property")
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	public Optional<Property> createProperty(Property property) {
		try {
			return technikonService.createProperty(property);

		} catch (ResourceNotFoundException rnfe) {
			Logger.getLogger(OwnerResource.class.getName()).log(Level.SEVERE, null, rnfe);

		}
		return Optional.empty();
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
	public Optional<Property> updateProperty(Property property) {
		try {
			return technikonService.updateProperty(property);

		} catch (ResourceNotFoundException rnfe) {
			Logger.getLogger(OwnerResource.class.getName()).log(Level.SEVERE, null, rnfe);

		}
		return Optional.empty();
	}

	/**
	 *
	 * @param propertyId
	 * @return
	 */
	@Path("/property/{propertyId}")
	@GET
	@Consumes("application/json")
	public Optional<Property> findPropertyById(@PathParam("propertyId") Long propertyId) {
		try {
			return technikonService.findPropertyById(propertyId);

		} catch (ResourceNotFoundException rnfe) {
			Logger.getLogger(OwnerResource.class.getName()).log(Level.SEVERE, null, rnfe);

		}
		return Optional.empty();
	}

	/**
	 *
	 * @param vatNumber
	 * @return
	 */
	@Path("/property/{vatNumber}")
	@GET
	@Consumes("application/json")
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
