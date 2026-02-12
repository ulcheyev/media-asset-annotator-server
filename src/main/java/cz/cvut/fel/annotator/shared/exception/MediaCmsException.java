package cz.cvut.fel.annotator.shared.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class MediaCmsException extends RuntimeException {

    private final HttpStatus status;
    private final String rawBody;

    public MediaCmsException(HttpStatus status, String rawBody) {
        super("MediaCMS error: " + status);
        this.status = status;
        this.rawBody = rawBody;
    }

    public MediaCmsException(String message) {
        super(message);
        this.status = HttpStatus.BAD_GATEWAY;
        this.rawBody = null;
    }

}

