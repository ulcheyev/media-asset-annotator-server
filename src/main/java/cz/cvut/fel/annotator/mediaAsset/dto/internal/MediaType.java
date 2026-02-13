package cz.cvut.fel.annotator.mediaAsset.dto.internal;

import com.fasterxml.jackson.annotation.JsonValue;

public enum MediaType {
    IMAGE,
    VIDEO;

    @JsonValue
    public String toJson() {
        return name().toLowerCase();
    }
}
