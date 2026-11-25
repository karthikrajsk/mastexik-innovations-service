package com.tech3.erp.controller;


import com.tech3.erp.dto.MediaDetailResponse;
import com.tech3.erp.dto.MediaResponse;
import com.tech3.erp.dto.MediaUpdateRequest;
import com.tech3.erp.dto.MediaUploadRequest;
import com.tech3.erp.service.MediaService;
import com.tech3.erp.util.ResponseUtil;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/media")
@CrossOrigin(origins = "*", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class MediaController {
    
    private static final Logger log = LoggerFactory.getLogger(MediaController.class);

    @Autowired
    private MediaService mediaService;

    @PostMapping("/counts")
    public ResponseEntity<?> getFileCountsByContainers(@RequestBody List<String> containerNames) {
        log.info("Received request to fetch file counts for {} containers", containerNames != null ? containerNames.size() : 0);
        
        Map<String, Long> counts = mediaService.getFileCountsByContainers(containerNames);
        
        return ResponseEntity.ok(ResponseUtil.successResponse(counts, "File counts fetched successfully"));
    }

    @PostMapping("/upload")
    public ResponseEntity<?> uploadMedia(@Valid @RequestBody MediaUploadRequest request) {
        log.info("Received media upload request for container: {}", request.getContainerName());
        
        MediaDetailResponse response = mediaService.uploadMedia(request);
        
        return ResponseEntity.ok(ResponseUtil.successResponse(response, "Media uploaded successfully"));
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateMedia(@Valid @RequestBody MediaUpdateRequest request) {
        log.info("Received media update request for mediaDetailSno: {}", request.getMediaDetailSno());
        
        MediaDetailResponse response = mediaService.updateMediaDetail(request);
        
        return ResponseEntity.ok(ResponseUtil.successResponse(response, "Media updated successfully"));
    }

    @GetMapping("/container/{containerName}")
    public ResponseEntity<?> getMediaByContainer(@PathVariable String containerName) {
        log.info("Received request to fetch media for container: {}", containerName);
        
        MediaResponse response = mediaService.getMediaByContainerName(containerName);
        
        return ResponseEntity.ok(ResponseUtil.successResponse(response, "Media fetched successfully"));
    }

    @GetMapping("/{mediaSno}")
    public ResponseEntity<?> getMediaByMediaSno(@PathVariable Long mediaSno) {
        log.info("Received request to fetch media for mediaSno: {}", mediaSno);
        
        MediaResponse response = mediaService.getMediaByMediaSno(mediaSno);
        
        return ResponseEntity.ok(ResponseUtil.successResponse(response, "Media fetched successfully"));
    }

    @GetMapping("/detail/{mediaSno}")
    public ResponseEntity<?> getMediaDetail(@PathVariable Long mediaSno) {
        log.info("Received request to fetch media detail for mediaSno: {}", mediaSno);
        
        MediaDetailResponse response = mediaService.getFirstMediaDetailByMediaSno(mediaSno);
        
        return ResponseEntity.ok(ResponseUtil.successResponse(response, "Media detail fetched successfully"));
    }

    @DeleteMapping("/{mediaDetailSno}/soft")
    public ResponseEntity<?> softDeleteMedia(@PathVariable Long mediaDetailSno) {
        log.info("Received request to soft delete media: {}", mediaDetailSno);
        
        mediaService.softDeleteMedia(mediaDetailSno);
        
        Map<String, String> response = new HashMap<>();
        response.put("message", "Media soft deleted successfully");
        response.put("mediaDetailSno", String.valueOf(mediaDetailSno));
        
        return ResponseEntity.ok(ResponseUtil.successResponse(response, "Media soft deleted"));
    }

    @DeleteMapping("/{mediaDetailSno}/hard")
    public ResponseEntity<?> hardDeleteMedia(@PathVariable Long mediaDetailSno) {
        log.info("Received request to hard delete media: {}", mediaDetailSno);
        
        mediaService.hardDeleteMedia(mediaDetailSno);
        
        Map<String, String> response = new HashMap<>();
        response.put("message", "Media hard deleted successfully");
        response.put("mediaDetailSno", String.valueOf(mediaDetailSno));
        
        return ResponseEntity.ok(ResponseUtil.successResponse(response, "Media hard deleted"));
    }
}
