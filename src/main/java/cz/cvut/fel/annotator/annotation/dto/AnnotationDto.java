package cz.cvut.fel.annotator.annotation.dto;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = TextAnnotationDto.class, name = "text"),
        @JsonSubTypes.Type(value = PolylineAnnotationDto.class, name = "polyline")
})
@AllArgsConstructor
@NoArgsConstructor
public abstract class AnnotationDto {
    private String id;
    private String label;
    private String points;
    private Double timeStart;
    private Double timeEnd;
    private String color;
    private Double opacity;
}
