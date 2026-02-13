package cz.cvut.fel.annotator.shared.persistence;

import cz.cvut.fel.annotator.shared.exception.PersistenceException;
import cz.cvut.fel.annotator.shared.onto.OwlClassMapper;
import cz.cvut.fel.annotator.shared.onto.Vocabulary;
import cz.cvut.fel.annotator.shared.persistence.model.Thing;
import cz.cvut.kbss.jopa.model.EntityManager;
import cz.cvut.kbss.jopa.model.descriptors.Descriptor;
import cz.cvut.kbss.jopa.model.descriptors.EntityDescriptor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

import java.net.URI;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Getter
@Slf4j
public abstract class BaseDao<T extends Thing> implements DaoAPI<T> {

    protected final Class<T> type;
    protected final URI typeUri;
    protected final EntityManager em;

    protected BaseDao(Class<T> type, EntityManager em) {
        this.type = type;
        this.typeUri = URI.create(OwlClassMapper.getOwlClassForEntity(type));
        this.em = em;
    }

    @Override
    public List<T> findAll() {
        try {
            log.info("Fetching all entities of type {}", type.getSimpleName());
            return em.createNativeQuery("SELECT DISTINCT ?x WHERE { ?x a ?type . }", type).setParameter("type", typeUri)
                    .setDescriptor(getDefaultDescriptor())
                    .getResultList();
        } catch (RuntimeException e) {
            log.error("Error occurred while fetching all entities of type {}", type.getSimpleName(), e);
            throw new PersistenceException(e);
        }
    }

    @Override
    public List<T> findLimitOrPage(int limit) {
        try {
            log.info("Fetching entities of type {} limit {}", type.getSimpleName(), limit);
            return em.createNativeQuery("SELECT DISTINCT ?x WHERE { ?x a ?type . } limit " + limit, type).setParameter("type", typeUri)
                    .setDescriptor(getDefaultDescriptor())
                    .getResultList();
        } catch (RuntimeException e) {
            log.info("Error occurred while fetching entities of type {} limit {}", type.getSimpleName(), limit, e);
            throw new PersistenceException(e);
        }
    }

    @Override
    public Optional<T> findById(URI id) {
        Objects.requireNonNull(id);
        try {
            log.info("Fetching entity by ID: {} of type {}", id, type.getSimpleName());
            return Optional.ofNullable(em.find(type, id, getDefaultDescriptor()));
        } catch (RuntimeException e) {
            log.error("Error occurred while fetching entity by ID: {} of type {}", id, type.getSimpleName(), e);
            throw new PersistenceException(e);
        }
    }


    @Transactional
    @Override
    public void persist(T entity) {
        Objects.requireNonNull(entity);
        try {
            log.info("Persisting entity of type {}", type.getSimpleName());
            em.persist(entity, getDefaultDescriptor());
        } catch (RuntimeException e) {
            log.error("Error occurred while persisting entity of type {}", type.getSimpleName(), e);
            throw new PersistenceException(e);
        }
    }

    @Transactional
    @Override
    public void persist(Collection<T> entities) {
        Objects.requireNonNull(entities);
        try {
            log.info("Persisting collection of entities of type {}", type.getSimpleName());
            entities.forEach(this::persist);
        } catch (RuntimeException e) {
            log.error("Error occurred while persisting collection of entities of type {}", type.getSimpleName(), e);
            throw new PersistenceException(e);
        }
    }

    @Transactional
    @Override
    public void persist(T entity, EntityDescriptor descriptor) {
        Objects.requireNonNull(entity);
        try {
            log.info("Persisting entity of type {} with descriptor {}", type.getSimpleName(), descriptor);
            em.persist(entity, descriptor);
        } catch (RuntimeException e) {
            log.error("Error occurred while persisting entity of type {}", type.getSimpleName(), e);
            throw new PersistenceException(e);
        }
    }


    @Transactional
    @Override
    public T update(T entity) {
        Objects.requireNonNull(entity);
        try {
            log.info("Updating entity of type {}", type.getSimpleName());
            return em.merge(entity, getDefaultDescriptor());
        } catch (RuntimeException e) {
            log.error("Error occurred while updating entity of type {}", type.getSimpleName(), e);
            throw new PersistenceException(e);
        }
    }

    @Transactional
    @Override
    public void remove(T entity) {
        Objects.requireNonNull(entity);
        Objects.requireNonNull(entity.getEntityId());
        try {
            log.info("Removing entity of type {}", type.getSimpleName());
            T managedEntity = em.find(type, entity.getEntityId(), getDefaultDescriptor());
            if (managedEntity != null) {
                em.remove(managedEntity);
            } else {
                log.warn("Entity of type {} with ID {} not found for removal", type.getSimpleName(), entity.getEntityId());
            }
        } catch (RuntimeException e) {
            log.error("Error occurred while removing entity of type {}", type.getSimpleName(), e);
            throw new PersistenceException(e);
        }
    }

    @Override
    public boolean exists(URI id) {
        Objects.requireNonNull(id);
        try {
            log.info("Finding entity of type {}", type.getSimpleName());
            return em.createNativeQuery("ASK { ?x a ?type . }", Boolean.class).setParameter("x", id)
                    .setParameter("type", typeUri).getSingleResult();
        } catch (RuntimeException e) {
            log.error("Error occurred while finding entity of type {}", type.getSimpleName(), e);
            throw new PersistenceException(e);
        }
    }

    public Descriptor getDefaultDescriptor() {
        return new EntityDescriptor(URI.create(Vocabulary.MEDIA_DATA_GRAPH));

    }


}