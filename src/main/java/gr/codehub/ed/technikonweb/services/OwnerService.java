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
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import lombok.NoArgsConstructor;

/**
 *
 * @author alexandrosaristeridis
 */
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

	/**
	 *
	 * @param repair
	 * @return
	 */
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
	/**
	 *
	 * @param ownerId
	 * @return
	 */
	@Override
	//@Transactional
	public List<Property> getPropertiesByOwnerId(Long ownerId) {
		return propertyRepository.findByOwnerId(ownerId);
	}

	/**
	 *
	 * @param propertyId
	 * @return
	 */
	public Optional<Property> findById(Long propertyId) {
		return propertyRepository.findById(propertyId);
	}

	/**
	 *
	 * @param propertyId
	 * @return
	 */
	public boolean safeDeleteById(Long propertyId) {
		return propertyRepository.safeDeleteById(propertyId);
	}

	/**
	 *
	 * @param property
	 * @return
	 */
	public Optional<Property> updatePorperty(Property property) {
		return propertyRepository.updateProperty(property);
	}

	/**
	 *
	 * @param property
	 * @return
	 */
	public Optional<Property> saveProperty(Property property) {
		return propertyRepository.save(property);
	}

	/**
	 *
	 * @param repairId
	 * @return
	 */
	public Optional<Repair> findRepairById(Long repairId) {
		return repairRepository.findById(repairId);
	}

	/**
	 *
	 * @param repair
	 * @return
	 */
	public Optional<Repair> createRepair(Repair repair) {
		return repairRepository.save(repair);
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
	 * @param repairId
	 * @return
	 */
	public boolean deleteRepairById(Long repairId) {
		try {
			repairRepository.safeDeleteById(repairId);
			return true;
		} catch (ResourceNotFoundException rnfe) {
			rnfe.getMessage();
		}
		return false;
	}

	/**
	 *
	 * @param owner
	 * @return
	 */
	public Optional<Owner> updateOwner(Owner owner) {
		return ownerRepository.update(owner);
	}

	/**
	 *
	 * @param ownerId
	 * @return
	 */
	public boolean deleteOwnerById(Long ownerId) {
		try {
			ownerRepository.safeDeleteById(ownerId);
			return true;
		} catch (ResourceNotFoundException rnfe) {
			rnfe.getMessage();
		}
		return false;
	}

	/**
	 *
	 * @param ownerId
	 * @return
	 */
	public Optional<Owner> findOwnerById(Long ownerId) {
		return ownerRepository.findByOwnerId(ownerId);
	}
}
