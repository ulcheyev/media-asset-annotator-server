package cz.cvut.fel.annotator.annotation.rest;

import cz.cvut.fel.annotator.annotation.dto.AnnotationDto;
import cz.cvut.fel.annotator.annotation.service.AnnotationService;
import cz.cvut.fel.annotator.annotation.service.MediaAnnotationFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/annotation")
@RequiredArgsConstructor
public class AnnotationController {

    private final AnnotationService annotationService;
    private final MediaAnnotationFacade mediaAnnotationFacade;

    @GetMapping(path = "/media/{referenceId}", produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
    public List<AnnotationDto> getByMediaAssetReferenceId(
            @PathVariable String referenceId) {
        return annotationService.getByMediaAssetReferenceId(referenceId);
    }

    @PostMapping(value = "/media/{referenceId}", consumes = MimeTypeUtils.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> addAnnotationsForMediaAsset(
            @PathVariable String referenceId,
            @RequestBody List<AnnotationDto> annotationDtos) {
        annotationService.addAnnotationsForMediaAssetByReferenceId(referenceId, annotationDtos);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/media/{referenceId}/{annotationId}")
    public ResponseEntity<Void> deleteAnnotationsForMediaAsset(
            @PathVariable String referenceId,
            @PathVariable String annotationId) {
        annotationService.deleteAnnotationsForMediaAssetByReferenceId(referenceId, annotationId);
        return ResponseEntity.status(HttpStatus.OK).build();

    }

    @PatchMapping(path = "/media/{referenceId}", consumes = MimeTypeUtils.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> patchMediaAssetByReferenceId(
            @PathVariable String referenceId,
            @RequestBody List<AnnotationDto> annotationDtos) {
        mediaAnnotationFacade.patch(referenceId, annotationDtos);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
