package cz.cvut.fel.annotator.mediaAsset.service;

import cz.cvut.fel.annotator.annotation.service.AnnotationService;
import cz.cvut.fel.annotator.client.MediaCmsClient;
import cz.cvut.fel.annotator.client.MediaCmsErrorResolver;
import cz.cvut.fel.annotator.client.MediaCmsUrlResolver;
import cz.cvut.fel.annotator.mediaAsset.dto.external.MediaCmsMediaDto;
import cz.cvut.fel.annotator.mediaAsset.dto.external.PlaylistResponseDto;
import cz.cvut.fel.annotator.mediaAsset.dto.internal.MediaAssetDto;
import cz.cvut.fel.annotator.mediaAsset.dto.mapper.MediaCmsMapper;
import cz.cvut.fel.annotator.shared.exception.MediaCmsException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class MediaCmsAdapterService {

    private final MediaCmsClient mediaCmsClient;
    private final MediaCmsErrorResolver errorResolver;
    private final MediaCmsUrlResolver urlResolver;
    private final MediaCmsMapper mapper;
    private final AnnotationService annotationService;


    public List<MediaAssetDto> getMediaAssetsList(String playlistId) {
        PlaylistResponseDto playlist;

        try {
            playlist = mediaCmsClient.getPlaylist(playlistId);
        } catch (MediaCmsException ex) {
            throw errorResolver.resolve(ex, "playlist " + playlistId);
        }

        if (playlist.playlist_media() == null) {
            return List.of();
        }

        return playlist.playlist_media().stream()
                .map(media -> {
                    try {
                        MediaCmsMediaDto mediaDto =
                                mediaCmsClient.getMediaByUrl(media.api_url());

                        String src = urlResolver.getOriginalMediaUrlWithAbsolutePath(
                                mediaDto.originalMediaUrl()
                        );

                        return mapper.mediaAssetFromPlaylistMedia(media, src, annotationService.getAnnotationsCountByMediaAssetReferenceId(media.friendly_token()));

                    } catch (MediaCmsException ex) {
                        throw errorResolver.resolve(
                                ex,
                                "media " + media.api_url()
                        );
                    }
                })
                .toList();
    }


    public MediaAssetDto getMediaAsset(String id) {
        log.debug("Fetching media asset {}", id);

        try {
            MediaCmsMediaDto media = mediaCmsClient.getMediaById(id);

            String src = urlResolver.getOriginalMediaUrlWithAbsolutePath(
                    media.originalMediaUrl()
            );

            return mapper.mediaAssetFromMediaCms(id, media, src, annotationService.getAnnotationsCountByMediaAssetReferenceId(id));

        } catch (MediaCmsException ex) {
            throw errorResolver.resolve(ex, "media " + id);
        }
    }
}

