package cz.cvut.fel.annotator.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class MediaCmsUrlResolver {

    @Value("${mediacms.api-base}")
    private String MEDIA_CMS_API_BASE;

    @Value("${mediacms.base-url}")
    private String MEDIA_CMS_BASE_URL;

    public String getOriginalMediaUrlWithAbsolutPath(String originalMediaUrl) {
        if (originalMediaUrl.startsWith("http")) {
            return originalMediaUrl;
        }

        return MEDIA_CMS_BASE_URL + normalize(originalMediaUrl);
    }

    public String mediaCmsApiUrlFromPlainId(String category, String id) {
        return MEDIA_CMS_API_BASE + "/" + normalize(category) + "/" + id;
    }

    private String normalize(String value) {
        if (value.endsWith("/")) {
            return value.substring(0, value.length() - 1);
        }
        return value;
    }
}
