package gr.codehub.ed.technikonweb.services;

import gr.codehub.ed.technikonweb.Repositories.PropertyRepositoryInterface;
import gr.codehub.ed.technikonweb.models.Property;
import gr.codehub.ed.technikonweb.models.Repair;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import java.util.List;
import java.util.Scanner;

@Slf4j
@RequestScoped
public class OwnerService implements OwnerServiceInterface {
@Inject
    private PropertyRepositoryInterface propertyRepository;
    //private final OwnerRepositoryInterface ownerRepository;

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
}
