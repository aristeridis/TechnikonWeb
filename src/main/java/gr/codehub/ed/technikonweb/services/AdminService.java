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
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class AdminService implements AdminServiceInterface {

	@Inject
	private RepairRepository repairRepository;
	@Inject
	private OwnerRepository ownerRepository;
	@Inject
	private PropertyRepository propertyRepository;

	@Override
	public List<Repair> getPendingRepairs() {
		RepairRepository getRepairs = new RepairRepository();
		List<Repair> allRepairs = getRepairs.findAll();
		return allRepairs.stream().filter((Repair pendingRepair) -> RepairStatus.PENDING.equals(pendingRepair.getRepairStatus())).collect(Collectors.toList());

	}

	@Override
	public void proposeCost(Long repairId, BigDecimal proposedCost) {
		Repair rp = new Repair();
		RepairRepositoryInterface rr = new RepairRepository();
		rr.findById(repairId);
		rp.setProposedCost(proposedCost);

	}

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

	@Override
	public List<Optional> proposedStartEndDates(Date proposedDateOfStart, Date proposedDateOfEnd) {
		RepairRepositoryInterface rri = new RepairRepository();
		return rri.findByRangeDates(proposedDateOfStart, proposedDateOfEnd);

	}

	public List<Repair> getAllRepairs() {
		RepairRepository getRepairs = new RepairRepository();
		List<Repair> allRepairs = getRepairs.findAll();
		return getRepairs.findAll();
	}

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

	public Optional<Repair> createRepair(Repair repair) {
		try {
			return repairRepository.save(repair);
		} catch (CustomException e) {
		}
		return Optional.empty();

	}

	public Optional<Repair> updateRepair(Repair repair) {
		return repairRepository.update(repair);
	}

	public List<Repair> findByDate(Date date) {
		return repairRepository.findByDate(date);
	}

	public List<Repair> findByRangeOfDates(Date dateStart, Date dateEnd) {
		return repairRepository.findByRangeDates(dateStart, dateEnd);
	}

	public List<Repair> findByPropertyId(Long propertyId) {
		return repairRepository.findByPropertyId(propertyId);
	}

	public Optional<Owner> findByVatNumber(Long vatNumber) {
		return ownerRepository.findByVatNumber(vatNumber);
	}
	

	public Optional<Owner> findOwnerByEmail(String email) {
		return ownerRepository.findByEmail(email);
	}

	public Optional<Property> createProperty(Property property) {
		return propertyRepository.save(property);
	}
	public Optional<Property> updateProperty(Property property) {
		return propertyRepository.update(property);
	}
	public Optional<Property> findPropertyById(Long propertyId) {
		return propertyRepository.findById(propertyId);
	}
	public List<Property> findPropertyByOwnerId(Long ownerId) {
		return propertyRepository.findByOwnerId(ownerId);
}
}
