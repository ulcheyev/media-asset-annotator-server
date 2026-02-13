package cz.cvut.fel.annotator.mediaAsset.dto.internal;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.Instant;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record MediaAssetDto(
        String id,
        MediaType type,
        String src,
        MediaStatus status,
        Instant modifiedAt
) {
}
