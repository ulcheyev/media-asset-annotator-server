package cz.cvut.fel.annotator.client;

import cz.cvut.fel.annotator.mediaAsset.dto.external.MediaCmsMediaDto;
import cz.cvut.fel.annotator.mediaAsset.dto.external.PlaylistResponseDto;
import cz.cvut.fel.annotator.shared.exception.MediaCmsException;
import cz.cvut.fel.annotator.shared.utils.WebUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@Component
@RequiredArgsConstructor
@Slf4j
public class MediaCmsClient {

    private final WebClient webClient;
    private final WebUtils webUtils;
    private final MediaCmsUrlResolver urlResolver;

    public PlaylistResponseDto getPlaylist(String playlistId) {
        try {
            return webClient.get()
                    .uri(urlResolver.mediaCmsApiUrlFromPlainId("playlists", playlistId))
                    .retrieve()
                    .bodyToMono(PlaylistResponseDto.class)
                    .block();
        } catch (WebClientResponseException e) {
            throw new MediaCmsException(
                    webUtils.mapToHttpStatus(e.getStatusCode()),
                    e.getResponseBodyAsString()
            );
        }
    }

    public MediaCmsMediaDto getMediaByUrl(String mediaApiUrl) {
        try {
            return webClient.get()
                    .uri(mediaApiUrl)
                    .retrieve()
                    .bodyToMono(MediaCmsMediaDto.class)
                    .block();
        } catch (WebClientResponseException e) {
            throw new MediaCmsException(
                    webUtils.mapToHttpStatus(e.getStatusCode()),
                    e.getResponseBodyAsString()
            );
        }
    }

    public MediaCmsMediaDto getMediaById(String mediaId) {
        try {
            return webClient.get()
                    .uri(urlResolver.mediaCmsApiUrlFromPlainId("media", mediaId))
                    .retrieve()
                    .bodyToMono(MediaCmsMediaDto.class)
                    .block();
        } catch (WebClientResponseException e) {
            throw new MediaCmsException(
                    webUtils.mapToHttpStatus(e.getStatusCode()),
                    e.getResponseBodyAsString()
            );
        }
    }
}

