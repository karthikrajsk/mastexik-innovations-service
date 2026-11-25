package com.tech3.erp.repository;

import com.tech3.erp.entity.YarnOutward;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.Optional;
import java.util.List;

public interface YarnOutwardRepository extends JpaRepository<YarnOutward, Long> {
    
    Optional<YarnOutward> findByOutwardNo(String outwardNo);

    List<YarnOutward> findAllByOutwardNo(String outwardNo);
    
    List<YarnOutward> findByOutwardNoContaining(String outwardNo);
    
    @Query("SELECT yo FROM YarnOutward yo WHERE yo.orderId = :orderId AND yo.remarks LIKE %:remark%")
    List<YarnOutward> findByOrderIdAndRemarksContaining(@Param("orderId") Long orderId, @Param("remark") String remark);
    
    @Query("SELECT yo FROM YarnOutward yo WHERE yo.remarks = :remarks")
    List<YarnOutward> findByRemarks(@Param("remarks") String remarks);

    @Query("SELECT yo FROM YarnOutward yo WHERE yo.remarks LIKE %:remarks%")
    List<YarnOutward> findByRemarksContaining(@Param("remarks") String remarks);

    @Query("SELECT CASE WHEN COUNT(yo) > 0 THEN true ELSE false END FROM YarnOutward yo WHERE yo.outwardNo = :outwardNo")
    boolean existsByOutwardNo(@Param("outwardNo") String outwardNo);
}