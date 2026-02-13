package cz.cvut.fel.annotator.shared.onto;

import cz.cvut.kbss.jopa.model.annotations.OWLClass;

public class OwlClassMapper {
    public static String getOwlClassForEntity(Class<?> entityClass) {
        final OWLClass owlClass = entityClass.getDeclaredAnnotation(OWLClass.class);
        if (owlClass == null) {
            throw new IllegalArgumentException("Class " + entityClass + " cannot not be mapped to OWL class.");
        }
        return owlClass.iri();
    }
}