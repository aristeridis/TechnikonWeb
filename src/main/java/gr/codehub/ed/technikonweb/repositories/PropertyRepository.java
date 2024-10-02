package gr.codehub.ed.technikonweb.repositories;

import gr.codehub.ed.technikonweb.exceptions.CustomException;
import gr.codehub.ed.technikonweb.exceptions.OwnerNotFoundException;
import gr.codehub.ed.technikonweb.models.Property;
import gr.codehub.ed.technikonweb.utility.JPAUtil;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.RequestScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import java.text.ParseException;
import lombok.extern.slf4j.Slf4j;
import java.util.List;
import java.util.Optional;
import lombok.NoArgsConstructor;

/**
 *
 * @author alexandrosaristeridis
 */
@Slf4j
@RequestScoped
@NoArgsConstructor
public class PropertyRepository implements PropertyRepositoryInterface<Property, Long> {

	@PersistenceContext(unitName = "Persistence")
	private EntityManager entityManager;

	/**
	 *
	 * @param propertyId
	 * @return
	 */
	@Override
	public Optional<Property> findById(Long propertyId) {
		try {
			Property property = entityManager.find(Property.class, propertyId);
			return Optional.ofNullable(property);
		} catch (CustomException ce) {
			log.debug("Could not find Property with ID: " + propertyId);
			entityManager.getTransaction().rollback();
			System.out.println(ce.getMessage());
		}
		return Optional.empty();
	}

	/**
	 *
	 * @param ownerId
	 * @return
	 */
	@Override
	public List<Property> findByOwnerId(Long ownerId) {
		try {
			TypedQuery<Property> query = entityManager.createQuery(
				"SELECT p FROM Property p WHERE p.owner.OwnerId = :ownerId", Property.class); //paizei na einai ownerId
			query.setParameter("ownerId", ownerId);
			return query.getResultList();
		} catch (OwnerNotFoundException oe) {
			log.debug("Could not find Properties for Owner ID: " + ownerId);
			System.out.println(oe.getMessage());
		}
		return List.of();
	}

	/**
	 *
	 * @return
	 */
	@Override
	@Transactional
	public List<Property> findAll() {
		try {
			TypedQuery<Property> query = entityManager.createQuery(
				"SELECT p FROM Property p WHERE p.deleted = false", Property.class);
			return query.getResultList();
		} catch (Exception e) {
			log.debug("Could not retrieve all Properties", e);
			return List.of();
		}
	}

	/**
	 *
	 * @param property
	 * @return
	 */
	@Override
	@Transactional
	public Optional<Property> save(Property property) {
		try {
			entityManager.persist(property);
			return Optional.of(property);
		} catch (Exception e) {
			log.debug("Could not save Property", e);
		}
		return Optional.empty();
	}

	/**
	 *
	 * @param propertyId
	 * @return
	 */
	@Override
	@Transactional
	public boolean deleteById(Long propertyId) {
		try {
			Property property = entityManager.find(Property.class, propertyId);
			if (property != null) {
				entityManager.remove(property);
				return true;
			}
		} catch (Exception e) {
			log.debug("Could not delete Property with ID: " + propertyId);
		}
		return false;
	}

	/**
	 *
	 * @param propertyId
	 * @return
	 */
	@Override
	@Transactional
	public boolean safeDeleteById(Long propertyId) {
		try {
			Property property = entityManager.find(Property.class, propertyId);
			if (property != null) {
				property.setDeletedProperty(true);
				entityManager.merge(property);
				return true;
			}
		} catch (Exception e) {
			log.debug("Could not safely delete Property with ID: " + propertyId, e);
		}
		return false;
	}

	/**
	 *
	 * @param property
	 * @return
	 */
	@Override
	@Transactional
	public Optional<Property> update(Property property) {
		try {
			Property p = entityManager.find(Property.class, property.getPropertyId());
			if (p != null) {

				p.setAddress(property.getAddress());
				p.setYearOfConstruction(property.getYearOfConstruction());
				p.setPropertyType(property.getPropertyType());
				p.setOwner(property.getOwner());

				entityManager.merge(p);
				return Optional.of(p);
			} else {
				log.debug("Property with ID: " + property.getPropertyId() + " not found for update.");
			}
		} catch (Exception e) {
			log.debug("Could not update Property", e);
		}
		return Optional.empty();
	}

	/**
	 *
	 * @param property
	 * @return
	 */
	@Transactional
	public Optional<Property> updateProperty(Property property) {
		try {
			property = entityManager.merge(property);
			return Optional.of(property);
		} catch (Exception e) {
			log.error("Error updating property: " + property, e);
		}
		return Optional.empty();
	}

//    private Class<Property> getEntityClass() {
//        return Property.class;
//    }
//
//    private String getEntityClassName() {
//        return Property.class.getName();
//    }
}
