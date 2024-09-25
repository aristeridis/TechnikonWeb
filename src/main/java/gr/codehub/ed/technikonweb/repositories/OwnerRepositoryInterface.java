package gr.codehub.ed.technikonweb.repositories;

import gr.codehub.ed.technikonweb.exceptions.ResourceNotFoundException;
import jakarta.enterprise.context.RequestScoped;
import java.util.List;
import java.util.Optional;


@RequestScoped
public interface OwnerRepositoryInterface<T, K, S> {

    Optional<T> findByOwnerId(K id);

    Optional<T> findByVatNumber(K vt);

    Optional<T> findByEmail(S s);

    List<T> findAll() throws ResourceNotFoundException;

    Optional<T> save(T t);

    boolean deleteById(K id);

    boolean safeDeleteById(K id);

    Optional<T> update(T t);

}
