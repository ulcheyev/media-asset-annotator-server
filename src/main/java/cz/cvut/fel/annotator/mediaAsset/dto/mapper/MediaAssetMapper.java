package cz.cvut.fel.annotator.mediaAsset.dto.mapper;

import cz.cvut.fel.annotator.mediaAsset.dto.internal.MediaAssetDto;
import cz.cvut.fel.annotator.mediaAsset.persistence.MediaAsset;
import org.springframework.stereotype.Component;

@Component
public class MediaAssetMapper {

    public MediaAssetDto toDto(MediaAsset entity) {
        return new MediaAssetDto(
                entity.getReferenceId(),
                entity.getType(),
                entity.getSource(),
                MediaAssetMapperUtils.mapStatus(entity.getAnnotations().size()),
                null
        );
    }

    public MediaAsset toEntity(MediaAssetDto dto) {
        return MediaAsset.builder()
                .referenceId(dto.id())
                .type(dto.type())
                .source(dto.src())
                .build();
    }
}
