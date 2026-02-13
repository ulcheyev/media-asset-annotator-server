package cz.cvut.fel.annotator.mediaAsset.dto.internal;

import com.fasterxml.jackson.annotation.JsonValue;

public enum MediaStatus {
    ANNOTATED,
    PENDING;

    @JsonValue
    public String toJson() {
        return name().toLowerCase();
    }
}
