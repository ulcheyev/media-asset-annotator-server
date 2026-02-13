package cz.cvut.fel.annotator.annotation.persistence;

import cz.cvut.fel.annotator.shared.exception.PersistenceException;
import cz.cvut.fel.annotator.shared.onto.Vocabulary;
import cz.cvut.fel.annotator.shared.persistence.BaseDao;
import cz.cvut.kbss.jopa.model.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.net.URI;
import java.util.List;

@Slf4j
@Repository
public class AnnotationDao extends BaseDao<Annotation> {

    public AnnotationDao(EntityManager em) {
        super(Annotation.class, em);
    }


    public List<Annotation> findByMediaAssetReferenceId(String referenceId) {
        try {

            log.debug("Fetching annotations for media asset referenceId {}", referenceId);

            var query = em.createNativeQuery("""
                    SELECT ?ann WHERE {
                        GRAPH ?g {
                            ?media a ?mediaType ;
                                   ?refProp ?refId ;
                                   ?hasAnn ?ann .
                            ?ann a ?annType .
                        }
                    }
                    """, Annotation.class);

            query.setParameter("g", URI.create(Vocabulary.MEDIA_DATA_GRAPH));
            query.setParameter("mediaType", URI.create(Vocabulary.MediaAsset));
            query.setParameter("refProp", URI.create(Vocabulary.hasReferenceId));
            query.setParameter("refId", referenceId);
            query.setParameter("hasAnn", URI.create(Vocabulary.hasAnnotation));
            query.setParameter("annType", URI.create(Vocabulary.Annotation));

            return query.getResultList();

        } catch (RuntimeException e) {
            log.error("Error fetching annotations for media asset {}", referenceId, e);
            throw new PersistenceException(e);
        }
    }


}
