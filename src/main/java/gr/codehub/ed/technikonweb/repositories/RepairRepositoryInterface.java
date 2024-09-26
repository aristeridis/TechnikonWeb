package gr.codehub.ed.technikonweb.repositories;

import jakarta.enterprise.context.RequestScoped;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author alexandrosaristeridis
 * @param <T>
 * @param <K>
 * @param <D>
 */
@RequestScoped
public interface RepairRepositoryInterface<T, K, D> {

	/**
	 *
	 * @param repairId
	 * @return
	 */
	Optional<T> findById(K repairId);

	/**
	 *
	 * @param propertyId
	 * @return
	 */
	List<T> findByPropertyId(K propertyId);

	/**
	 *
	 * @param date
	 * @return
	 */
	List<T> findByDate(D date);

	/**
	 *
	 * @param date1
	 * @param date2
	 * @return
	 */
	List<T> findByRangeDates(D date1, D date2);

	/**
	 *
	 * @return
	 */
	List<T> findAll();

	/**
	 *
	 * @param t
	 * @return
	 */
	Optional<T> update(T t);

	/**
	 *
	 * @param t
	 * @return
	 */
	Optional<T> save(T t);

	/**
	 *
	 * @param repairId
	 * @return
	 */
	boolean deleteById(K repairId);

	/**
	 *
	 * @param repairId
	 * @return
	 */
	boolean safeDeleteById(K repairId);

}
