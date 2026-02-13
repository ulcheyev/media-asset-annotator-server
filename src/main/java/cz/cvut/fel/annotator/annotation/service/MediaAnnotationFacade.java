package cz.cvut.fel.annotator.annotation.service;

import cz.cvut.fel.annotator.annotation.dto.AnnotationDto;
import cz.cvut.fel.annotator.mediaAsset.persistence.MediaAsset;
import cz.cvut.fel.annotator.mediaAsset.service.MediaAssetService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MediaAnnotationFacade {

    private final MediaAssetService mediaAssetService;
    private final AnnotationService annotationService;

    public void patch(String referenceId, List<AnnotationDto> dtos) {
        MediaAsset asset = mediaAssetService.getEntityByIdOrPersist(referenceId);
        annotationService.patchMediaAsset(asset, dtos);
    }
}
