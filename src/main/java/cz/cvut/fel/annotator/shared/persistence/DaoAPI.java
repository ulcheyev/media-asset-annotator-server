package cz.cvut.fel.annotator.shared.persistence;

import cz.cvut.kbss.jopa.model.descriptors.EntityDescriptor;

import java.net.URI;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface DaoAPI<T> {
    Optional<T> findById(URI id);

    List<T> findAll();

    void persist(T t);

    void persist(T t, EntityDescriptor descriptor);

    void persist(Collection<T> entities);

    List<T> findLimitOrPage(int limit);

    T update(T t);

    void remove(T t);

    boolean exists(URI id);
}
