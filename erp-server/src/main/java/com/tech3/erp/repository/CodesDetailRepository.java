package com.tech3.erp.repository;

import com.tech3.erp.entity.CodesDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface CodesDetailRepository extends JpaRepository<CodesDetail, Long> {
    List<CodesDetail> findByCodesHeaderId(Long headerId);
    Optional<CodesDetail> findByValue(String value);
    Optional<CodesDetail> findByCode(String code);
    Optional<CodesDetail> findByCodeOrValue(String code, String value);
    List<CodesDetail> findByCodesHeaderCodeGroupAndIsActiveTrue(String codeGroup);
    Optional<CodesDetail> findByCodesHeaderCodeGroupAndCodeAndIsActiveTrue(String codeGroup, String code);

}
