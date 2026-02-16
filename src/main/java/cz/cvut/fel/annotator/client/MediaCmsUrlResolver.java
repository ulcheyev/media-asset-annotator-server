package cz.cvut.fel.annotator.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class MediaCmsUrlResolver {

    @Value("${mediacms.api-base}")
    private String apiBase;

    @Value("${mediacms.internal-base-url}")
    private String internalBaseUrl;

    @Value("${mediacms.public-base-url}")
    private String publicBaseUrl;

    public String getOriginalMediaUrlWithAbsolutePath(String originalMediaUrl) {
        if (originalMediaUrl.startsWith("http")) {
            return originalMediaUrl;
        }

        return publicBaseUrl + normalize(originalMediaUrl);
    }

    public String mediaCmsApiUrlFromPlainId(String category, String id) {
        return internalBaseUrl + apiBase + "/" + normalize(category) + "/" + id;
    }

    private String normalize(String value) {
        if (value.endsWith("/")) {
            return value.substring(0, value.length() - 1);
        }
        return value;
    }
}

