package cz.cvut.fel.annotator.annotation.dto;

import lombok.*;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PolylineAnnotationDto extends AnnotationDto {
    private String fill;
    private Double strokeWidth;
}
