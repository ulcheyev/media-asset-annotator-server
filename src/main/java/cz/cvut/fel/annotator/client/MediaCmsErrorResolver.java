package cz.cvut.fel.annotator.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import cz.cvut.fel.annotator.shared.exception.MediaCmsException;
import cz.cvut.fel.annotator.shared.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class MediaCmsErrorResolver {

    private final ObjectMapper objectMapper;
    private final String UNKNOWN_MEDIA_CMS_ERROR = "Unknown MediaCMS error";

    public RuntimeException resolve(
            MediaCmsException ex,
            String context
    ) {
        String reason = extractReason(ex.getRawBody());

        if (ex.getStatus() == HttpStatus.NOT_FOUND) {
            log.warn("MediaCMS 404 {}: {}", context, reason);
            return new ResourceNotFoundException(reason);
        }

        if (ex.getStatus().is5xxServerError()) {
            log.error("MediaCMS 5xx {}: {}", context, reason);
            return new MediaCmsException(reason);
        }

        log.error(
                "MediaCMS {} {}: {}",
                ex.getStatus().value(),
                context,
                reason
        );
        return new MediaCmsException(reason);
    }

    private String extractReason(String body) {
        if (body == null || body.isBlank()) {
            return UNKNOWN_MEDIA_CMS_ERROR;
        }

        try {
            MediaCmsErrorDto dto =
                    objectMapper.readValue(body, MediaCmsErrorDto.class);
            return dto.detail() != null
                    ? dto.detail()
                    : UNKNOWN_MEDIA_CMS_ERROR;
        } catch (Exception e) {
            return UNKNOWN_MEDIA_CMS_ERROR;
        }
    }
}
