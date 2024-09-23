package gr.codehub.ed.technikonweb.services;

import gr.codehub.ed.technikonweb.models.Property;
import gr.codehub.ed.technikonweb.models.Repair;
import java.util.List;

public interface OwnerServiceInterface {

    boolean acceptance(Repair repair);

    List<Property> getPropertiesByOwnerId(Long ownerId);
}
