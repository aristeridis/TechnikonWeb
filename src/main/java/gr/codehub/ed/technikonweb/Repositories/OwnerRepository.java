package gr.codehub.ed.technikonweb.Repositories;

import gr.codehub.ed.technikonweb.exceptions.OwnerNotFoundException;
import gr.codehub.ed.technikonweb.exceptions.ResourceNotFoundException;
import gr.codehub.ed.technikonweb.models.Owner;
import gr.codehub.ed.technikonweb.utility.JPAUtil;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
public class OwnerRepository implements OwnerRepositoryInterface<Owner, Long, String> {

	@PersistenceContext(unitName = "Persistance")
	private EntityManager entityManager;

	@Override
	public Optional<Owner> findByOwnerId(Long ownerId) {
		try {
			Owner owner = entityManager.find(Owner.class, ownerId);
			return Optional.ofNullable(owner);
		} catch (OwnerNotFoundException onfe) {
			log.debug("Could not find Owner with ID: " + ownerId);
			entityManager.getTransaction().rollback();
			System.out.println(onfe.getMessage());
		}
		return Optional.empty();
	}

	@Override
	public Optional<Owner> findByVatNumber(Long vatNumber) {
		try {
			entityManager.getTransaction().begin();
			TypedQuery<Owner> query = entityManager.createQuery(
				"SELECT o FROM Owner o WHERE o.VatNumber = :vatNumber AND o.deleted = false", Owner.class);
			query.setParameter("vatNumber", vatNumber);
			Owner owner = query.getSingleResult();
			entityManager.getTransaction().commit();
			return Optional.of(owner);
		} catch (OwnerNotFoundException onfe) {
			log.debug("Could not find an Owner with this VAT number");
			entityManager.getTransaction().rollback();
			System.out.println(onfe.getMessage());
		}
		return Optional.empty();
	}

	@Override
	public Optional<Owner> findByEmail(String email) {
		try {
			entityManager.getTransaction().begin();
			TypedQuery<Owner> query = entityManager.createQuery(
				"SELECT o FROM Owner o WHERE o.Email = :email AND o.deleted = false", Owner.class);
			query.setParameter("email", email);
			Owner owner = query.getSingleResult();
			entityManager.getTransaction().commit();
			return Optional.of(owner);
		} catch (Exception e) {
			e.getMessage();
		}
		return Optional.empty();
	}

	@Override
	@Transactional
	public Optional<Owner> save(Owner owner) {
		try {
			entityManager.getTransaction().begin();
			entityManager.persist(owner);
			entityManager.getTransaction().commit();
			return Optional.of(owner);
		} catch (Exception e) {
			log.debug("Could not save user");
		}
		return Optional.empty();
	}

	@Override
	@Transactional
	public boolean deleteById(Long ownerId) {
		Owner persistentInstance = entityManager.find(getEntityClass(), ownerId);
		if (persistentInstance != null) {
			try {
				entityManager.getTransaction().begin();
				entityManager.remove(persistentInstance);
				entityManager.getTransaction().commit();
			} catch (Exception e) {
				log.debug("Could not delete Owner");
				return false;
			}
			return true;
		}
		return false;
	}

	@Override
	@Transactional
	public List<Owner> findAll() throws ResourceNotFoundException {
		try {

			TypedQuery<Owner> query = entityManager.createQuery(
				"SELECT o FROM Owner o WHERE o.deletedOwner = false", Owner.class);
			return query.getResultList();
		} catch (ResourceNotFoundException rnfe) {
			System.out.println(rnfe.getMessage());

		}
		return null;
	}

	private Class<Owner> getEntityClass() {
		return Owner.class;
	}

	@Override
	@Transactional
	public boolean safeDeleteById(Long ownerId) {
		Owner persistentInstance = entityManager.find(getEntityClass(), ownerId);
		if (persistentInstance != null) {
			try {
				entityManager.getTransaction().begin();
				persistentInstance.setDeletedOwner(true);
				entityManager.merge(persistentInstance);
				entityManager.getTransaction().commit();
				return true;
			} catch (Exception e) {
				log.debug("Could not safely delete Owner", e);
				entityManager.getTransaction().rollback();
				return false;
			}
		}
		return false;
	}

	@Override
	public Optional<Owner> update(Owner owner) {
		try {
			entityManager.getTransaction().begin();
			Owner o = entityManager.find(Owner.class, owner.getOwnerId());
			if (o != null) {
				o.setAddress(owner.getAddress());
				o.setEmail(owner.getEmail());
				o.setPassword(owner.getPassword());

				entityManager.merge(o);
				entityManager.getTransaction().commit();
				return Optional.of(o);
			} else {
				log.debug("Owner with ID: " + owner.getOwnerId() + " was not found and cannot be updated.");
			}
		} catch (Exception e) {
			log.debug("Could not update Owner", e);
			entityManager.getTransaction().rollback();
		}
		return Optional.empty();
	}
}
