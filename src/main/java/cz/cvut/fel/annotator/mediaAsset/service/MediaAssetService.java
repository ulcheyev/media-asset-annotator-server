package cz.cvut.fel.annotator.mediaAsset.service;

import cz.cvut.fel.annotator.mediaAsset.dto.internal.MediaAssetDto;
import cz.cvut.fel.annotator.mediaAsset.dto.mapper.MediaAssetMapper;
import cz.cvut.fel.annotator.mediaAsset.persistence.MediaAsset;
import cz.cvut.fel.annotator.mediaAsset.persistence.MediaAssetDao;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class MediaAssetService {

    private final MediaAssetDao dao;
    private final MediaCmsAdapterService mediaCmsAdapterService;
    private final MediaAssetMapper mediaAssetMapper;

    public MediaAssetDto getById(String token) {
        log.debug("Fetching MediaAsset {}", token);
        return dao
                .getByReferenceId(token)
                .map(mediaAssetMapper::toDto)
                .orElseGet(() -> getFromAdapter(token));
// TODO       checkIntegrity(token);
    }


    public MediaAsset getEntityByIdOrPersist(String token) {
        log.debug("Fetching MediaAsset {}", token);
        return dao
                .getByReferenceId(token)
                .orElseGet(() -> {
                    MediaAssetDto mediaAssetDto = getFromAdapter(token);
                    MediaAsset mediaAsset = mediaAssetMapper.toEntity(mediaAssetDto);
                    dao.persist(mediaAsset);
                    return mediaAsset;
                });
// TODO       checkIntegrity(token);
    }


    private MediaAssetDto getFromAdapter(String token) {
        log.info("MediaAsset {} not found locally. Fetching from MediaCMS.", token);
        return mediaCmsAdapterService.getMediaAsset(token);
    }
}
