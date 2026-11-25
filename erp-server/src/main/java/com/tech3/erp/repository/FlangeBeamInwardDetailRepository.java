// package com.tech3.erp.repository;

// import com.tech3.erp.entity.FlangeBeamInwardDetail;
// import org.springframework.data.jpa.repository.JpaRepository;
// import org.springframework.data.jpa.repository.Modifying;
// import org.springframework.data.jpa.repository.Query;
// import org.springframework.data.repository.query.Param;
// import org.springframework.stereotype.Repository;

// import java.util.List;

// @Repository
// public interface FlangeBeamInwardDetailRepository extends JpaRepository<FlangeBeamInwardDetail, Long> {

//     @Modifying(clearAutomatically = true, flushAutomatically = true)
//     @Query("UPDATE FlangeBeamInwardDetail d SET d.isIssuedToSizingProduction = :status WHERE d.flangeId IN :flangeIds")
//     int updateIssuedStatusByFlangeIds(@Param("flangeIds") List<Long> flangeIds, @Param("status") Integer status);
// }
package com.tech3.erp.repository;

import com.tech3.erp.entity.FlangeBeamInwardDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FlangeBeamInwardDetailRepository extends JpaRepository<FlangeBeamInwardDetail, Long> {

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("UPDATE FlangeBeamInwardDetail d SET d.isIssuedToSizingProduction = :status WHERE d.flangeId IN :flangeIds")
    int updateIssuedStatusByFlangeIds(@Param("flangeIds") List<Long> flangeIds, @Param("status") Integer status);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("UPDATE FlangeBeamInwardDetail d SET d.isIssuedToSizingProduction = :status WHERE d.flangeNo IN :flangeNos")
    int updateIssuedStatusByFlangeNos(@Param("flangeNos") List<String> flangeNos, @Param("status") Integer status);

    boolean existsByFlangeId(Long flangeId);
    boolean existsByFlangeNo(String flangeNo);
}

