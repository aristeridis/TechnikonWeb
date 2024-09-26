package gr.codehub.ed.technikonweb.repositories;

import gr.codehub.ed.technikonweb.exceptions.ResourceNotFoundException;
import jakarta.enterprise.context.RequestScoped;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author alexandrosaristeridis
 * @param <T>
 * @param <K>
 * @param <S>
 */
@RequestScoped
public interface OwnerRepositoryInterface<T, K, S> {

	/**
	 *
	 * @param id
	 * @return
	 */
	Optional<T> findByOwnerId(K id);

	/**
	 *
	 * @param vt
	 * @return
	 */
	Optional<T> findByVatNumber(K vt);

	/**
	 *
	 * @param s
	 * @return
	 */
	Optional<T> findByEmail(S s);

	/**
	 *
	 * @return
	 * @throws ResourceNotFoundException
	 */
	List<T> findAll() throws ResourceNotFoundException;

	/**
	 *
	 * @param t
	 * @return
	 */
	Optional<T> save(T t);

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
	 * @param t
	 * @return
	 */
	Optional<T> update(T t);

}
