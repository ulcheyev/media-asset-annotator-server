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
public class TextAnnotationDto extends AnnotationDto {
    private String text;
    private Double fontSize;
    private Integer fontWeight;
}
