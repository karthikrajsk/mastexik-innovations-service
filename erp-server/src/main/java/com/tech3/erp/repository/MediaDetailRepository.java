package com.tech3.erp.repository;

import com.tech3.erp.entity.MediaDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface MediaDetailRepository extends JpaRepository<MediaDetail, Long> {

    List<MediaDetail> findByMediaMediaSno(Long mediaSno);

    List<MediaDetail> findByMediaContainerNameAndIsUploadedTrue(String containerName);

    Optional<MediaDetail> findByMediaDetailSnoAndIsUploadedTrue(Long mediaDetailSno);

    @Query("SELECT md FROM MediaDetail md WHERE md.media.containerName = :containerName AND md.isUploaded = true ORDER BY md.createdAt DESC")
    List<MediaDetail> findAllByContainerNameOrderByCreatedAtDesc(@Param("containerName") String containerName);

    @Query("SELECT md.media.mediaSno FROM MediaDetail md WHERE md.mediaDetailSno = :mediaDetailSno")
    Optional<Long> findMediaSnoByMediaDetailSno(@Param("mediaDetailSno") Long mediaDetailSno);

    @Query("SELECT md.media.containerName, COUNT(md) FROM MediaDetail md WHERE md.media.containerName IN :containerNames AND md.isUploaded = true GROUP BY md.media.containerName")
    List<Object[]> countByContainerNames(@Param("containerNames") List<String> containerNames);
}
