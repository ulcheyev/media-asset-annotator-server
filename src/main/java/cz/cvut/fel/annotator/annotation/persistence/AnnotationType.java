package cz.cvut.fel.annotator.annotation.persistence;

public enum AnnotationType {
    TEXT,
    POLYLINE;

    public String toString() {
        return name().toUpperCase();
    }

}