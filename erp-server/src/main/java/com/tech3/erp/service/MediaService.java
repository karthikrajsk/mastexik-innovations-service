package com.tech3.erp.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tech3.erp.dto.MediaDetailResponse;
import com.tech3.erp.dto.MediaResponse;
import com.tech3.erp.dto.MediaUpdateRequest;
import com.tech3.erp.dto.MediaUploadRequest;
import com.tech3.erp.entity.Media;
import com.tech3.erp.entity.MediaDetail;
import com.tech3.erp.exception.ApiException;
import com.tech3.erp.repository.MediaDetailRepository;
import com.tech3.erp.repository.MediaRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MediaService {
    
    private static final Logger log = LoggerFactory.getLogger(MediaService.class);

    @Autowired
    private MediaRepository mediaRepository;

    @Autowired
    private MediaDetailRepository mediaDetailRepository;

    @Autowired
    private FileStorageService fileStorageService;

    @Transactional
    public MediaDetailResponse uploadMedia(MediaUploadRequest request) {
        try {
            log.info("Starting media upload for container: {}, fileName: {}, isCompressed: {}", 
                    request.getContainerName(), request.getFileName(), request.getIsCompressed());

            FileStorageService.UploadResult uploadResult;
            
            if (request.getIsCompressed() != null && request.getIsCompressed()) {
                log.info("File is already compressed from frontend. Skipping backend compression.");
                uploadResult = fileStorageService.uploadFile(
                        request.getBase64File(),
                        request.getContainerName(),
                        request.getFileName(),
                        request.getContentType(),
                        true,
                        false,
                        request.getFolderPath()
                );
            } else {
                log.info("File is not compressed. Applying backend compression.");
                uploadResult = fileStorageService.uploadFileWithCompression(
                        request.getBase64File(),
                        request.getContainerName(),
                        request.getFileName(),
                        request.getContentType(),
                        request.getFolderPath()
                );
            }

            String fileKey = uploadResult.getFileKey();

            // Media media = mediaRepository.findByContainerName(request.getContainerName())
            //         .orElseGet(() -> {
            //             Media newMedia = new Media();
            //             newMedia.setContainerName(request.getContainerName());
            //             return mediaRepository.save(newMedia);
            //         });
            Media media = mediaRepository.findByContainerName(request.getContainerName())
        .orElseGet(() -> {
            Media newMedia = new Media();
            newMedia.setContainerName(request.getContainerName());
            Media saved = mediaRepository.save(newMedia);
            mediaRepository.flush();  // Force immediate flush to catch constraint violations
            return saved;
        });


            String mediaUrl = fileStorageService.getFileUrl(fileKey);
            Long fileSize = fileStorageService.getFileSize(fileKey);

            MediaDetail mediaDetail = new MediaDetail();
            mediaDetail.setMedia(media);
            mediaDetail.setMediaUrl(mediaUrl);
            mediaDetail.setMediaType(request.getMediaType());
            mediaDetail.setContentType(request.getContentType());
            mediaDetail.setMediaSize(fileSize);
            mediaDetail.setMediaDetailDescription(request.getMediaDetailDescription());
            mediaDetail.setOriginalFileName(request.getFileName());
            mediaDetail.setIsUploaded(true);
            mediaDetail.setAzureId(fileKey);
            mediaDetail.setIsCompressed(uploadResult.isCompressed());
            mediaDetail.setOriginalFileSize(uploadResult.getOriginalSize());
            mediaDetail.setStoredFileSize(uploadResult.getCompressedSize());

            MediaDetail savedDetail = mediaDetailRepository.save(mediaDetail);

            log.info("Media uploaded successfully. MediaDetailSno: {}, FileKey: {}, Compressed: {}, Original: {} bytes, Stored: {} bytes, Ratio: {:.2f}%", 
                    savedDetail.getMediaDetailSno(), fileKey, uploadResult.isCompressed(), 
                    uploadResult.getOriginalSize(), uploadResult.getCompressedSize(), 
                    uploadResult.getCompressionRatio());

            return convertToDetailResponse(savedDetail);
        } catch (ApiException e) {
            throw e;
        } catch (Exception e) {
            log.error("Error uploading media", e);
            throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to upload media: " + e.getMessage());
        }
    }

    @Transactional
    public MediaDetailResponse updateMediaDetail(MediaUpdateRequest request) {
        try {
            log.info("Starting media update for mediaDetailSno: {}", request.getMediaDetailSno());

            MediaDetail mediaDetail = mediaDetailRepository.findById(request.getMediaDetailSno())
                    .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "Media detail not found: " + request.getMediaDetailSno()));

            if (request.getReplaceFile() && request.getBase64File() != null && !request.getBase64File().isEmpty()) {
                fileStorageService.deleteFile(mediaDetail.getAzureId());

                String fileKey = fileStorageService.uploadFile(
                        request.getBase64File(),
                        mediaDetail.getMedia().getContainerName(),
                        request.getFileName(),
                        request.getContentType()
                );

                String mediaUrl = fileStorageService.getFileUrl(fileKey);
                Long fileSize = fileStorageService.getFileSize(fileKey);

                mediaDetail.setMediaUrl(mediaUrl);
                mediaDetail.setAzureId(fileKey);
                mediaDetail.setMediaSize(fileSize);
                mediaDetail.setContentType(request.getContentType());
                mediaDetail.setOriginalFileName(request.getFileName());

                log.info("File replaced successfully. Old key deleted, new key: {}", fileKey);
            }

            if (request.getMediaDetailDescription() != null && !request.getMediaDetailDescription().isEmpty()) {
                mediaDetail.setMediaDetailDescription(request.getMediaDetailDescription());
            }

            MediaDetail updatedDetail = mediaDetailRepository.save(mediaDetail);

            log.info("Media updated successfully. MediaDetailSno: {}", updatedDetail.getMediaDetailSno());

            return convertToDetailResponse(updatedDetail);
        } catch (ApiException e) {
            throw e;
        } catch (Exception e) {
            log.error("Error updating media detail", e);
            throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to update media detail: " + e.getMessage());
        }
    }

    @Transactional(readOnly = true)
    public MediaResponse getMediaByContainerName(String containerName) {
        try {
            log.info("Fetching media for container: {}", containerName);

            Media media = mediaRepository.findByContainerName(containerName).orElse(null);
            if (media == null) {
                MediaResponse emptyResponse = new MediaResponse();
                emptyResponse.setMediaSno(0L);
                emptyResponse.setContainerName(containerName);
                emptyResponse.setMediaDetails(java.util.Collections.emptyList());
                return emptyResponse;
            }

            List<MediaDetail> mediaDetails = mediaDetailRepository.findByMediaMediaSno(media.getMediaSno())
                    .stream()
                    .filter(MediaDetail::getIsUploaded)
                    .collect(Collectors.toList());

            log.info("Found {} media details for container: {}", mediaDetails.size(), containerName);

            return convertToMediaResponse(media, mediaDetails);
        } catch (ApiException e) {
            throw e;
        } catch (Exception e) {
            log.error("Error fetching media for container: {}", containerName, e);
            throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to fetch media: " + e.getMessage());
        }
    }

    @Transactional(readOnly = true)
    public java.util.Map<String, Long> getFileCountsByContainers(List<String> containerNames) {
        try {
            if (containerNames == null || containerNames.isEmpty()) {
                return java.util.Collections.emptyMap();
            }
            List<Object[]> results = mediaDetailRepository.countByContainerNames(containerNames);
            java.util.Map<String, Long> countMap = new java.util.HashMap<>();
            for (Object[] row : results) {
                if (row != null && row.length >= 2) {
                    String container = (String) row[0];
                    Long count = ((Number) row[1]).longValue();
                    countMap.put(container, count);
                }
            }
            return countMap;
        } catch (Exception e) {
            log.error("Error fetching file counts by containers", e);
            throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to fetch file counts: " + e.getMessage());
        }
    }

    @Transactional(readOnly = true)
    public MediaResponse getMediaByMediaSno(Long mediaSno) {
        try {
            log.info("Fetching media for mediaSno: {}", mediaSno);

            Media media = mediaRepository.findById(mediaSno)
                    .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "Media not found: " + mediaSno));

            List<MediaDetail> mediaDetails = mediaDetailRepository.findByMediaMediaSno(mediaSno)
                    .stream()
                    .filter(MediaDetail::getIsUploaded)
                    .collect(Collectors.toList());

            log.info("Found {} media details for mediaSno: {}", mediaDetails.size(), mediaSno);

            return convertToMediaResponse(media, mediaDetails);
        } catch (ApiException e) {
            throw e;
        } catch (Exception e) {
            log.error("Error fetching media for mediaSno: {}", mediaSno, e);
            throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to fetch media: " + e.getMessage());
        }
    }

    @Transactional(readOnly = true)
    public MediaDetailResponse getMediaDetailByMediaDetailSno(Long mediaDetailSno) {
        try {
            log.info("Fetching media detail for mediaDetailSno: {}", mediaDetailSno);

            MediaDetail mediaDetail = mediaDetailRepository.findByMediaDetailSnoAndIsUploadedTrue(mediaDetailSno)
                    .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "Media detail not found: " + mediaDetailSno));

            log.info("Media detail found: {}", mediaDetailSno);

            return convertToDetailResponse(mediaDetail);
        } catch (ApiException e) {
            throw e;
        } catch (Exception e) {
            log.error("Error fetching media detail for mediaDetailSno: {}", mediaDetailSno, e);
            throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to fetch media detail: " + e.getMessage());
        }
    }

    @Transactional(readOnly = true)
    public MediaDetailResponse getFirstMediaDetailByMediaSno(Long mediaSno) {
        try {
            log.info("Fetching first media detail for mediaSno: {}", mediaSno);

            Media media = mediaRepository.findById(mediaSno)
                    .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "Media not found: " + mediaSno));

            MediaDetail mediaDetail = mediaDetailRepository.findByMediaMediaSno(mediaSno)
                    .stream()
                    .filter(MediaDetail::getIsUploaded)
                    .findFirst()
                    .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "No uploaded media details found for media: " + mediaSno));

            log.info("First media detail found for mediaSno: {}", mediaSno);

            return convertToDetailResponse(mediaDetail);
        } catch (ApiException e) {
            throw e;
        } catch (Exception e) {
            log.error("Error fetching first media detail for mediaSno: {}", mediaSno, e);
            throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to fetch media detail: " + e.getMessage());
        }
    }

    @Transactional
    public void softDeleteMedia(Long mediaDetailSno) {
        try {
            log.info("Soft deleting media detail: {}", mediaDetailSno);

            MediaDetail mediaDetail = mediaDetailRepository.findById(mediaDetailSno)
                    .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "Media detail not found: " + mediaDetailSno));

            mediaDetail.setIsUploaded(false);
            mediaDetailRepository.save(mediaDetail);

            log.info("Media detail soft deleted successfully. MediaDetailSno: {}", mediaDetailSno);
        } catch (ApiException e) {
            throw e;
        } catch (Exception e) {
            log.error("Error soft deleting media detail", e);
            throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to delete media: " + e.getMessage());
        }
    }

    @Transactional
    public void hardDeleteMedia(Long mediaDetailSno) {
        try {
            log.info("Hard deleting media detail: {}", mediaDetailSno);

            MediaDetail mediaDetail = mediaDetailRepository.findById(mediaDetailSno)
                    .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "Media detail not found: " + mediaDetailSno));

            fileStorageService.deleteFile(mediaDetail.getAzureId());

            mediaDetailRepository.deleteById(mediaDetailSno);

            log.info("Media detail hard deleted successfully. MediaDetailSno: {}", mediaDetailSno);
        } catch (ApiException e) {
            throw e;
        } catch (Exception e) {
            log.error("Error hard deleting media detail", e);
            throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to delete media: " + e.getMessage());
        }
    }

    private MediaDetailResponse convertToDetailResponse(MediaDetail mediaDetail) {
        MediaDetailResponse response = new MediaDetailResponse();
        response.setMediaDetailSno(mediaDetail.getMediaDetailSno());
        response.setMediaSno(mediaDetail.getMedia().getMediaSno());
        response.setAzureId(mediaDetail.getAzureId());
        response.setMediaUrl(mediaDetail.getMediaUrl());
        response.setThumbnailUrl(mediaDetail.getThumbnailUrl());
        response.setMediaType(mediaDetail.getMediaType());
        response.setContentType(mediaDetail.getContentType());
        response.setMediaSize(mediaDetail.getMediaSize());
        response.setMediaDetailDescription(mediaDetail.getMediaDetailDescription());
        response.setIsUploaded(mediaDetail.getIsUploaded());
        response.setCreatedAt(mediaDetail.getCreatedAt());
        response.setUpdatedAt(mediaDetail.getUpdatedAt());
        response.setOriginalFileName(mediaDetail.getOriginalFileName());
        response.setIsCompressed(mediaDetail.getIsCompressed());
        response.setOriginalFileSize(mediaDetail.getOriginalFileSize());
        response.setStoredFileSize(mediaDetail.getStoredFileSize());
        return response;
    }

    private MediaResponse convertToMediaResponse(Media media, List<MediaDetail> mediaDetails) {
        MediaResponse response = new MediaResponse();
        response.setMediaSno(media.getMediaSno());
        response.setContainerName(media.getContainerName());
        response.setCreatedAt(media.getCreatedAt());
        response.setUpdatedAt(media.getUpdatedAt());
        response.setMediaDetails(mediaDetails.stream()
                .map(this::convertToDetailResponse)
                .collect(Collectors.toList()));
        return response;
    }
}
