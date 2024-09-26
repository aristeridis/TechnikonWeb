package gr.codehub.ed.technikonweb.repositories;

import jakarta.enterprise.context.RequestScoped;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author alexandrosaristeridis
 * @param <T>
 * @param <K>
 */
@RequestScoped
public interface PropertyRepositoryInterface<T, K> {

	/**
	 *
	 * @param id
	 * @return
	 */
	Optional<T> findById(K id);

	/**
	 *
	 * @param ownerId
	 * @return
	 */
	List<T> findByOwnerId(K ownerId);

	/**
	 *
	 * @return
	 */
	List<T> findAll();

	/**
	 *
	 * @param property
	 * @return
	 */
	Optional<T> save(T property);

	/**
	 *
	 * @param id
	 * @return
	 */
	boolean deleteById(K id);

	/**
	 *
	 * @param id
	 * @return
	 */
	boolean safeDeleteById(K id);

	/**
	 *
	 * @param entity
	 * @return
	 */
	Optional<T> update(T entity);
}
