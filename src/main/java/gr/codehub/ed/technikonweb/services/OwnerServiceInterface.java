package gr.codehub.ed.technikonweb.services;

import gr.codehub.ed.technikonweb.models.Property;
import gr.codehub.ed.technikonweb.models.Repair;
import java.util.List;

/**
 *
 * @author alexandrosaristeridis
 */
public interface OwnerServiceInterface {

	/**
	 *
	 * @param repair
	 * @return
	 */
	boolean acceptance(Repair repair);

	/**
	 *
	 * @param ownerId
	 * @return
	 */
	List<Property> getPropertiesByOwnerId(Long ownerId);
}
