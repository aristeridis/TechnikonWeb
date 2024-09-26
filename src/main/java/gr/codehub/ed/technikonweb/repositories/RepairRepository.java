package gr.codehub.ed.technikonweb.repositories;

import gr.codehub.ed.technikonweb.exceptions.ResourceNotFoundException;
import gr.codehub.ed.technikonweb.models.Repair;
import gr.codehub.ed.technikonweb.utility.JPAUtil;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.RequestScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import lombok.NoArgsConstructor;

@Slf4j
@RequestScoped
@NoArgsConstructor
public class RepairRepository implements RepairRepositoryInterface<Repair, Long, Date> {

	@PersistenceContext(unitName = "Persistence")
	private EntityManager entityManager;

	@Override
	public Optional<Repair> findById(Long repairId) {
		try {
			Repair repair = entityManager.find(Repair.class, repairId);
			return Optional.ofNullable(repair);
		} catch (ResourceNotFoundException rnfe) {
			//log.debug("Could not find Property with ID: " + repairId);
			System.out.println(rnfe.getMessage());
		}
		return Optional.empty();
	}

	@Override
	public List<Repair> findByPropertyId(Long propertyId) {
		try {
			TypedQuery<Repair> query = entityManager.createQuery(
				"SELECT repair FROM Repair repair WHERE repair.property.propertyId = :propertyId", Repair.class);
			query.setParameter("propertyId", propertyId);
			List<Repair> repairs = query.getResultList();
			return repairs;
		} catch (Exception e) {
			log.error("Error finding repairs by Property ID: " + propertyId, e);
		}
		return List.of();
	}

	@Override
	public List<Repair> findByDate(Date dateOfStart) {
		try {
			TypedQuery<Repair> query = entityManager.createQuery("SELECT repair FROM Repair repair WHERE repair.dateOfStart = :dateOfStart", Repair.class);
			query.setParameter("dateOfStart", dateOfStart);
			List<Repair> repairs = query.getResultList();
			return repairs;
		} catch (Exception e) {
			log.error("Error finding repairs by date: " + dateOfStart, e);
		}
		return List.of();
	}

	@Override
	public List<Repair> findByRangeDates(Date dateOfStart, Date dateOfEnd) {
		try {
			TypedQuery<Repair> query = entityManager.createQuery("SELECT repair FROM Repair repair WHERE repair.dateOfSubmission BETWEEN :dateOfStart AND :dateOfEnd", Repair.class);
			query.setParameter("dateOfStart", dateOfStart);
			query.setParameter("dateOfEnd", dateOfEnd);
			List<Repair> repairs = query.getResultList();
			return repairs;
		} catch (Exception e) {
			log.error("Error finding repairs by date range: " + dateOfStart + " to " + dateOfEnd, e);
		}
		return List.of();
	}

	@Override
	@Transactional
	public List<Repair> findAll() {
		try {
			TypedQuery<Repair> query = entityManager.createQuery("SELECT r FROM Repair r", Repair.class);
			return query.getResultList();
		} catch (ResourceNotFoundException rnfe) {
			log.error("Error retrieving all repairs");
			System.out.println(rnfe.getMessage());
		}
		return List.of();
	}

	@Override
	@Transactional
	public Optional<Repair> save(Repair repair) {
		try {
			entityManager.persist(repair);
			return Optional.of(repair);
		} catch (Exception e) {
			log.error("Error saving repair: " + repair, e);
		}
		return Optional.empty();
	}

	@Override
	public Optional<Repair> update(Repair repair) {
		try {
			repair = entityManager.merge(repair);
			return Optional.of(repair);
		} catch (Exception e) {
			log.error("Error updating repair: " + repair, e);
		}
		return Optional.empty();
	}

	@Override
	@Transactional
	public boolean deleteById(Long repairId) {
		try {
			Repair repair = entityManager.find(Repair.class, repairId);
			if (repair != null) {
				entityManager.remove(repair);
				return true;
			}
		} catch (Exception e) {
			log.error("Error deleting repair with ID: " + repairId, e);
		}
		return false;
	}

	@Override
	@Transactional
	public boolean safeDeleteById(Long repairId) {
		try {
			Repair repair = entityManager.find(Repair.class, repairId);
			if (repair != null) {
				repair.setDeletedRepair(true);
				entityManager.merge(repair);
				return true;
			}
		} catch (Exception e) {
			log.error("Error safely deleting repair with ID: " + repairId, e);
		}
		return false;
	}
}
