package com.tech3.erp.repository;

import com.tech3.erp.entity.SizingBeamOutwardDetail;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SizingBeamOutwardDetailRepository extends JpaRepository<SizingBeamOutwardDetail, Long> {
    boolean existsByWeavingContractId(Long weavingContractId);

    List<SizingBeamOutwardDetail> findBySizingBeamOutwardId(Long sizingBeamOutwardId);

    List<SizingBeamOutwardDetail> findBySizedBeamNo(String sizedBeamNo);

    List<SizingBeamOutwardDetail> findBySizedBeamNoAndSizingBeamOutwardIsReturn(String sizedBeamNo, Integer isReturn);
}
