package gr.codehub.ed.technikonweb.services;

import gr.codehub.ed.technikonweb.exceptions.ResourceNotFoundException;
import gr.codehub.ed.technikonweb.models.Owner;
import gr.codehub.ed.technikonweb.models.Property;
import gr.codehub.ed.technikonweb.models.Repair;
import gr.codehub.ed.technikonweb.repositories.OwnerRepository;
import gr.codehub.ed.technikonweb.repositories.PropertyRepository;
import gr.codehub.ed.technikonweb.repositories.RepairRepository;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import lombok.NoArgsConstructor;

@Slf4j
@NoArgsConstructor
@RequestScoped
public class OwnerService implements OwnerServiceInterface {

	@Inject
	private PropertyRepository propertyRepository;
	@Inject
	private RepairRepository repairRepository;
	@Inject
	private OwnerRepository ownerRepository;

	@Override
	public boolean acceptance(Repair repair) {
		System.out.println(repair.toString());
		System.out.println("Do you accept the proposed repair ?");
		Scanner scanner = new Scanner(System.in);
		boolean acceptance = scanner.nextBoolean();
		repair.setAcceptance(acceptance);
		return acceptance;

	}

//	public OwnerService(PropertyRepository propertyRepository) {
//		this.propertyRepository = propertyRepository;
//	}
	@Override
	public List<Property> getPropertiesByOwnerId(Long ownerId) {
		return propertyRepository.findByOwnerId(ownerId);
	}

	public Optional<Property> findById(Long propertyId) {
		return propertyRepository.findById(propertyId);
	}

	public boolean safeDeleteById(Long propertyId) {
		return propertyRepository.deleteById(propertyId);
	}

	public Optional<Property> updatePorperty(Property property) {
		return propertyRepository.update(property);
	}

	public Optional<Property> saveProperty(Property property) {
		return propertyRepository.save(property);
	}

	public Optional<Repair> findRepairById(Long repairId) {
		return repairRepository.findById(repairId);
	}

	public Optional<Repair> createRepair(Repair repair) {
		return repairRepository.save(repair);
	}

	public Optional<Repair> updateRepair(Repair repair) {
		return repairRepository.update(repair);
	}

	public boolean deleteRepairById(Long repairId) {
		try {
			repairRepository.deleteById(repairId);
			return true;
		} catch (ResourceNotFoundException rnfe) {
			rnfe.getMessage();
		}
		return false;
	}

	public Optional<Owner> updateOwner(Owner owner) {
		return ownerRepository.update(owner);
	}

	public boolean deleteOwnerById(Long ownerId) {
		try {
			ownerRepository.deleteById(ownerId);
			return true;
		} catch (ResourceNotFoundException rnfe) {
			rnfe.getMessage();
		}
		return false;
	}
}
