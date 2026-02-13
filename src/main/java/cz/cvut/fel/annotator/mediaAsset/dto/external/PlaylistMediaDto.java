package cz.cvut.fel.annotator.mediaAsset.dto.external;

import java.time.Instant;

public record PlaylistMediaDto(
        String friendly_token,
        String api_url,
        String media_type,
        Integer duration,
        Instant add_date
) {
}
