package gr.codehub.ed.technikonweb.services;

import gr.codehub.ed.technikonweb.repositories.RepairRepository;
import gr.codehub.ed.technikonweb.repositories.RepairRepositoryInterface;
import gr.codehub.ed.technikonweb.enums.RepairStatus;
import gr.codehub.ed.technikonweb.exceptions.CustomException;
import gr.codehub.ed.technikonweb.models.Owner;
import gr.codehub.ed.technikonweb.models.Property;
import gr.codehub.ed.technikonweb.models.Repair;
import gr.codehub.ed.technikonweb.repositories.OwnerRepository;
import gr.codehub.ed.technikonweb.repositories.PropertyRepository;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 *
 * @author alexandrosaristeridis
 */
public class AdminService implements AdminServiceInterface {

	@Inject
	private RepairRepository repairRepository;
	@Inject
	private OwnerRepository ownerRepository;
	@Inject
	private PropertyRepository propertyRepository;

	/**
	 *
	 * @return
	 */
	@Override
	@Transactional
	public List<Repair> getPendingRepairs() {
		RepairRepository getRepairs = new RepairRepository();
		List<Repair> allRepairs = getRepairs.findAll();
		return allRepairs.stream().filter((Repair pendingRepair) -> RepairStatus.PENDING.equals(pendingRepair.getRepairStatus())).collect(Collectors.toList());

	}

	/**
	 *
	 * @param repairId
	 * @param proposedCost
	 */
	@Override
	public void proposeCost(Long repairId, BigDecimal proposedCost) {
		Repair rp = new Repair();
		RepairRepositoryInterface rr = new RepairRepository();
		rr.findById(repairId);
		rp.setProposedCost(proposedCost);

	}

	/**
	 *
	 * @return
	 */
	public List<BigDecimal> getProposedCost() {
		Repair rp = new Repair();
		RepairRepository getRepairs = new RepairRepository();
		List<Repair> allRepairs = getRepairs.findAll();
		BigDecimal BDCost;
		List<BigDecimal> Costs = new ArrayList();
		for (Repair repair : allRepairs) {
			BDCost = rp.getProposedCost();
			Costs.add(BDCost);

		}
		return Costs;

	}

	/**
	 *
	 * @param proposedDateOfStart
	 * @param proposedDateOfEnd
	 * @return
	 */
	@Override
	public List<Optional> proposedStartEndDates(Date proposedDateOfStart, Date proposedDateOfEnd) {
		RepairRepositoryInterface rri = new RepairRepository();
		return rri.findByRangeDates(proposedDateOfStart, proposedDateOfEnd);

	}

	/**
	 *
	 * @return
	 */
	public List<Repair> getAllRepairs() {
		RepairRepository getRepairs = new RepairRepository();
		List<Repair> allRepairs = getRepairs.findAll();
		return getRepairs.findAll();
	}

	/**
	 *
	 * @param repairId
	 * @return
	 */
	@Override
	public List<Date> checkActuallDate(Long repairId) {
		RepairRepository rr = new RepairRepository();
		List<Date> rDates = new ArrayList<>();

		Optional<Repair> optionalRepair = rr.findById(repairId);
		if (optionalRepair.isPresent()) {
			Repair rp = optionalRepair.get();
			if (rp.getRepairStatus().equals(RepairStatus.COMPLETE)) {
				rDates.add(rp.getDateOfStart());
				rDates.add(rp.getDateOfEnd());
			}
		}

		return rDates;
	}

	/**
	 *
	 * @param repairId
	 * @param proposedCost
	 * @param proposedStartDate
	 * @param proposedEndDate
	 */
	public void proposeCostsAndDates(Long repairId, BigDecimal proposedCost, Date proposedStartDate, Date proposedEndDate) {
		RepairRepositoryInterface rri = new RepairRepository();
		Optional<Repair> optionalRepair = rri.findById(repairId);
		if (optionalRepair.isPresent()) {
			Repair repair = optionalRepair.get();
			if (RepairStatus.PENDING.equals(repair.getRepairStatus())) {
				repair.setProposedCost(proposedCost);
				repair.setProposedDateOfStart(proposedStartDate);
				repair.setProposedDateOfEnd(proposedEndDate);
			}
		}
	}

	/**
	 *
	 * @param repair
	 * @return
	 */
	public Optional<Repair> createRepair(Repair repair) {
		try {
			return repairRepository.save(repair);
		} catch (CustomException e) {
		}
		return Optional.empty();

	}

	/**
	 *
	 * @param repair
	 * @return
	 */
	public Optional<Repair> updateRepair(Repair repair) {
		return repairRepository.update(repair);
	}

	/**
	 *
	 * @param date
	 * @return
	 */
	public List<Repair> findByDate(Date date) {
		return repairRepository.findByDate(date);
	}

	/**
	 *
	 * @param dateStart
	 * @param dateEnd
	 * @return
	 */
	public List<Repair> findByRangeOfDates(Date dateStart, Date dateEnd) {
		return repairRepository.findByRangeDates(dateStart, dateEnd);
	}

	/**
	 *
	 * @param propertyId
	 * @return
	 */
	public List<Repair> findByPropertyId(Long propertyId) {
		return repairRepository.findByPropertyId(propertyId);
	}

	/**
	 *
	 * @param vatNumber
	 * @return
	 */
	public Optional<Owner> findByVatNumber(Long vatNumber) {
		return ownerRepository.findByVatNumber(vatNumber);
	}

	/**
	 *
	 * @param email
	 * @return
	 */
	public Optional<Owner> findOwnerByEmail(String email) {
		return ownerRepository.findByEmail(email);
	}

	/**
	 *
	 * @param property
	 * @return
	 */
	public Optional<Property> createProperty(Property property) {
		return propertyRepository.save(property);
	}

	/**
	 *
	 * @param property
	 * @return
	 */
	public Optional<Property> updateProperty(Property property) {
		return propertyRepository.save(property);
	}

	/**
	 *
	 * @param propertyId
	 * @return
	 */
	public Optional<Property> findPropertyById(Long propertyId) {
		return propertyRepository.findById(propertyId);
	}

	/**
	 *
	 * @param ownerId
	 * @return
	 */
	public List<Property> findPropertyByOwnerId(Long ownerId) {
		return propertyRepository.findByOwnerId(ownerId);
	}

	/**
	 *
	 * @param ownerId
	 * @return
	 */
	public Optional<Owner> findOwnerById(Long ownerId) {
		return ownerRepository.findByOwnerId(ownerId);
	}

	/**
	 *
	 * @param property
	 * @return
	 */
	public Optional<Property> saveProperty(Property property) {
		return propertyRepository.save(property);
	}
}
