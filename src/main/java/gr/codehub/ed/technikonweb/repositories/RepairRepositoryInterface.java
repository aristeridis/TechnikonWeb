package gr.codehub.ed.technikonweb.repositories;

import jakarta.enterprise.context.RequestScoped;
import java.util.List;
import java.util.Optional;


@RequestScoped
public interface RepairRepositoryInterface<T, K, D> {

    Optional<T> findById(K repairId);

    List<T> findByPropertyId(K propertyId);

    List<T> findByDate(D date);

    List<T> findByRangeDates(D date1, D date2);

    List<T> findAll();

    Optional<T> update(T t);

    Optional<T> save(T t);

    boolean deleteById(K repairId);

    boolean safeDeleteById(K repairId);

}
