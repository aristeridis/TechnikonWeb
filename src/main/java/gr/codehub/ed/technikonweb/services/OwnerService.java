package gr.codehub.ed.technikonweb.services;

import gr.codehub.ed.technikonweb.Repositories.PropertyRepositoryInterface;
import gr.codehub.ed.technikonweb.models.Owner;
import gr.codehub.ed.technikonweb.models.Property;
import gr.codehub.ed.technikonweb.models.Repair;
import gr.codehub.ed.technikonweb.Repositories.OwnerRepositoryInterface;
import gr.codehub.ed.technikonweb.Repositories.RepairRepositoryInterface;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

@Slf4j
@RequestScoped
public class OwnerService implements OwnerServiceInterface {

	@Inject
	private PropertyRepositoryInterface propertyRepository;
	@Inject
	private OwnerRepositoryInterface ownerRepository;
	@Inject
	private RepairRepositoryInterface repairepository;

	@Override
	public boolean acceptance(Repair repair) {
		System.out.println(repair.toString());
		System.out.println("Do you accept the proposed repair ?");
		Scanner scanner = new Scanner(System.in);
		boolean acceptance = scanner.nextBoolean();
		repair.setAcceptance(acceptance);
		return acceptance;

	}

	public OwnerService(PropertyRepositoryInterface propertyRepository) {
		this.propertyRepository = propertyRepository;
	}

	@Override
	public List<Property> getPropertiesByOwnerId(Long ownerId) {
		return propertyRepository.findByOwnerId(ownerId);
	}

	public Optional<Property> findById(Long propertyId) {
		return propertyRepository.findById(propertyId);
	}

	public Optional<Owner> saveOwner(Owner owner) {
		return ownerRepository.save(owner);
	}

	public boolean safeDeleteById(Long propertyId) {
		return propertyRepository.deleteById(propertyId);
	}

	public Optional<Property> updatePorperty(Property property) {
		return propertyRepository.update(property);
	}
	public Optional<Property> saveProperty(Property property){
		return propertyRepository.save(property);
	}
	
	
	public Optional<Repair> saveRepair(Repair repair){
		return  repairepository.save(repair);
	}
	public Optional<Repair> updateRepair(Repair repair){
		return repairepository.update(repair);
	}

	

}
