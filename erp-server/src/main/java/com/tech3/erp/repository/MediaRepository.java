package com.tech3.erp.repository;

import com.tech3.erp.entity.Media;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface MediaRepository extends JpaRepository<Media, Long> {

    Optional<Media> findByContainerName(String containerName);

    boolean existsByContainerName(String containerName);

    @Query("SELECT m FROM Media m JOIN m.mediaDetails md WHERE md.mediaDetailSno = :mediaDetailSno")
    Optional<Media> findByMediaDetailSno(@Param("mediaDetailSno") Long mediaDetailSno);
}
