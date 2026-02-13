package cz.cvut.fel.annotator.annotation.dto.mapper;

import cz.cvut.fel.annotator.annotation.dto.AnnotationDto;
import cz.cvut.fel.annotator.annotation.dto.PolylineAnnotationDto;
import cz.cvut.fel.annotator.annotation.dto.TextAnnotationDto;
import cz.cvut.fel.annotator.annotation.persistence.Annotation;
import cz.cvut.fel.annotator.annotation.persistence.AnnotationType;
import lombok.NonNull;
import org.springframework.stereotype.Component;

@Component
public class AnnotationMapper {

    public AnnotationDto toDto(@NonNull Annotation entity) {

        return switch (entity.getType()) {
            case TEXT -> mapText(entity);
            case POLYLINE -> mapPolyline(entity);
        };
    }

    public Annotation toEntity(@NonNull AnnotationDto dto) {

        return switch (dto) {
            case TextAnnotationDto textDto -> mapTextToEntity(textDto);
            case PolylineAnnotationDto polylineDto -> mapPolylineToEntity(polylineDto);
            default -> throw new IllegalArgumentException(
                    "Unsupported annotation DTO type: " + dto.getClass());
        };
    }


    private TextAnnotationDto mapText(Annotation entity) {
        return TextAnnotationDto.builder()
                .id(extractId(entity))
                .label(entity.getLabel())
                .points(entity.getGeometryPoints())
                .timeStart(entity.getStartTime())
                .timeEnd(entity.getEndTime())
                .color(entity.getColor())
                .opacity(entity.getOpacity())
                .text(entity.getText())
                .fontSize(entity.getFontSize())
                .fontWeight(entity.getFontWeight())
                .build();
    }

    private PolylineAnnotationDto mapPolyline(Annotation entity) {
        return PolylineAnnotationDto.builder()
                .id(extractId(entity))
                .label(entity.getLabel())
                .points(entity.getGeometryPoints())
                .timeStart(entity.getStartTime())
                .timeEnd(entity.getEndTime())
                .color(entity.getColor())
                .opacity(entity.getOpacity())
                .strokeWidth(entity.getStrokeWidth())
                .fill(entity.getFillColor())
                .build();
    }

    private Annotation mapTextToEntity(TextAnnotationDto dto) {
        return Annotation.builder()
                .label(dto.getLabel())
                .type(AnnotationType.TEXT)
                .geometryPoints(dto.getPoints())
                .startTime(dto.getTimeStart())
                .endTime(dto.getTimeEnd())
                .color(dto.getColor())
                .opacity(dto.getOpacity())
                .text(dto.getText())
                .fontSize(dto.getFontSize())
                .fontWeight(dto.getFontWeight())
                .build();
    }

    private Annotation mapPolylineToEntity(PolylineAnnotationDto dto) {
        return Annotation.builder()
                .label(dto.getLabel())
                .type(AnnotationType.POLYLINE)
                .geometryPoints(dto.getPoints())
                .startTime(dto.getTimeStart())
                .endTime(dto.getTimeEnd())
                .color(dto.getColor())
                .opacity(dto.getOpacity())
                .strokeWidth(dto.getStrokeWidth())
                .fillColor(dto.getFill())
                .build();
    }


    private Double toDouble(String value) {
        try {
            return value != null ? Double.valueOf(value) : null;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid numeric value: " + value, e);
        }
    }

    private Integer toInteger(String value) {
        try {
            return value != null ? Integer.valueOf(value) : null;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid numeric value: " + value, e);
        }
    }


    private String extractId(Annotation entity) {
        return entity.getEntityId() != null
                ? entity.getEntityId().toString()
                : null;
    }

    private String toString(Object value) {
        return value != null ? value.toString() : null;
    }
}
