package gr.codehub.ed.technikonweb.resources;

import gr.codehub.ed.technikonweb.enums.RepairStatus;
import gr.codehub.ed.technikonweb.exceptions.CustomException;
import gr.codehub.ed.technikonweb.exceptions.ResourceNotFoundException;
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

@Path("Admin")
@Slf4j
@RequestScoped
public class AdminResourve {

	@Inject
	private AdminService technikonService;

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
	@Path("/repair/{repairDate}")
	@GET
	@Consumes("application/json")
	public List<Repair> findByDate(@PathParam("repairDate")Date date) {
		try {
			return technikonService.findByDate(date);

		} catch (ResourceNotFoundException rnfe) {
			Logger.getLogger(OwnerResource.class.getName()).log(Level.SEVERE, null, rnfe);

		}
		return null;
	}
	@Path("/repair/{repairDates}")
	@GET
	@Consumes("application/json")
	public List<Repair> findByRangeOfDates(@PathParam("repairDates")Date dateStart,Date dateEnd) {
		try {
			return technikonService.findByRangeOfDates(dateStart, dateEnd);

		} catch (ResourceNotFoundException rnfe) {
			Logger.getLogger(OwnerResource.class.getName()).log(Level.SEVERE, null, rnfe);

		}
		return null;
	}
	@Path("/repair/{propertyId}")
	@GET
	@Consumes("application/json")
	public List<Repair> findByPropertyId(@PathParam("propertyId")Long propertyId) {
		try {
			return technikonService.findByPropertyId(propertyId);

		} catch (ResourceNotFoundException rnfe) {
			Logger.getLogger(OwnerResource.class.getName()).log(Level.SEVERE, null, rnfe);

		}
		return null;
	}
}
