// package com.tech3.erp.repository;

// import java.util.List;

// import org.springframework.data.jpa.repository.JpaRepository;

// import com.tech3.erp.entity.SizingProductionDetail;

// public interface SizingProductionDetailRepository extends JpaRepository<SizingProductionDetail, Long> {
//     List<SizingProductionDetail> findBySizingProductionId(Long sizingProductionId);

//     void deleteBySizingProductionId(Long sizingProductionId);

//     List<SizingProductionDetail> findBySizedBeamNoIn(List<String> sizedBeamNos);
// }


package com.tech3.erp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tech3.erp.entity.SizingProductionDetail;

public interface SizingProductionDetailRepository extends JpaRepository<SizingProductionDetail, Long> {
    List<SizingProductionDetail> findBySizingProductionId(Long sizingProductionId);

    void deleteBySizingProductionId(Long sizingProductionId);

    List<SizingProductionDetail> findBySizedBeamNoIn(List<String> sizedBeamNos);

    List<SizingProductionDetail> findBySizedBeamNo(String sizedBeamNo);

    List<SizingProductionDetail> findBySizedBeamNoAndIsReturn(String sizedBeamNo, Integer isReturn);

    boolean existsByFlangeId(Long flangeId);
    boolean existsByFlangeNo(String flangeNo);

    List<SizingProductionDetail> findBySizingProductionSizingPlanIdAndSizedBeamNoAndFlangeNo(Long sizingPlanId, String sizedBeamNo, String flangeNo);

    List<SizingProductionDetail> findBySizingProductionSizingPlanIdAndSizedBeamNo(Long sizingPlanId, String sizedBeamNo);
    List<SizingProductionDetail> findByReturnNo(String returnNo);
}