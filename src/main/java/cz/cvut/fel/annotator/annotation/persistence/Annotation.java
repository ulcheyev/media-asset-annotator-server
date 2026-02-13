package cz.cvut.fel.annotator.annotation.persistence;

import cz.cvut.fel.annotator.shared.onto.Vocabulary;
import cz.cvut.fel.annotator.shared.persistence.model.Thing;
import cz.cvut.kbss.jopa.model.annotations.*;
import cz.cvut.kbss.jopa.vocabulary.RDFS;
import cz.cvut.kbss.jopa.vocabulary.XSD;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

@OWLClass(iri = Vocabulary.Annotation)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@ToString
public class Annotation extends Thing implements Serializable {

    @OWLDataProperty(iri = RDFS.LABEL, datatype = XSD.STRING, fetch = FetchType.EAGER)
    private String label;

    @Enumerated(EnumType.STRING)
    @OWLDataProperty(iri = Vocabulary.hasAnnotationType, simpleLiteral = true, fetch = FetchType.EAGER)
    private AnnotationType type;

    @OWLDataProperty(iri = Vocabulary.hasStartTime, fetch = FetchType.EAGER)
    private Double startTime;

    @OWLDataProperty(iri = Vocabulary.hasEndTime, fetch = FetchType.EAGER)
    private Double endTime;

    @OWLDataProperty(iri = Vocabulary.hasGeometryPoints, simpleLiteral = true, datatype = XSD.STRING, fetch = FetchType.EAGER)
    private String geometryPoints;

    @OWLDataProperty(iri = Vocabulary.hasColor, simpleLiteral = true, datatype = XSD.STRING, fetch = FetchType.EAGER)
    private String color;

    @OWLDataProperty(iri = Vocabulary.hasOpacity, fetch = FetchType.EAGER)
    private Double opacity;

    @OWLDataProperty(iri = Vocabulary.hasText, simpleLiteral = true, datatype = XSD.STRING, fetch = FetchType.EAGER)
    private String text;

    @OWLDataProperty(iri = Vocabulary.hasFontSize, fetch = FetchType.EAGER)
    private Double fontSize;

    @OWLDataProperty(iri = Vocabulary.hasFontWeight, fetch = FetchType.EAGER)
    private Integer fontWeight;

    @OWLDataProperty(iri = Vocabulary.hasStrokeWidth, fetch = FetchType.EAGER)
    private Double strokeWidth;

    @OWLDataProperty(iri = Vocabulary.hasFillColor, simpleLiteral = true, datatype = XSD.STRING, fetch = FetchType.EAGER)
    private String fillColor;
}
