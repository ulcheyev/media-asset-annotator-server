package cz.cvut.fel.annotator.mediaAsset.dto.external;

import java.util.List;

public record PlaylistResponseDto(
        List<PlaylistMediaDto> playlist_media
) {
}
