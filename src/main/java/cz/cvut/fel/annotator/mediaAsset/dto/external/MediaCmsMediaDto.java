package cz.cvut.fel.annotator.mediaAsset.dto.external;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record MediaCmsMediaDto(

        @JsonProperty("original_media_url")
        String originalMediaUrl,

        @JsonProperty("media_type")
        String mediaType,

        @JsonProperty("duration")
        Integer duration,

        @JsonProperty("edit_date")
        String editDate

) {
}
