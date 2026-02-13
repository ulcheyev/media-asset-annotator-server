package cz.cvut.fel.annotator.shared.utils;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;

@Component
public class WebUtils {

    public HttpStatus mapToHttpStatus(HttpStatusCode statusCode) {
        try {
            return HttpStatus.valueOf(statusCode.value());
        } catch (IllegalArgumentException e) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }
}
