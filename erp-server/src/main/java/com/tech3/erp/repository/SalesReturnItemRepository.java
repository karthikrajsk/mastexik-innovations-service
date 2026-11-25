package com.tech3.erp.repository;

import com.tech3.erp.entity.SalesReturnItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface SalesReturnItemRepository extends JpaRepository<SalesReturnItem, Long> {
    List<SalesReturnItem> findBySalesReturnId(Long salesReturnId);

    @Query("SELECT sri FROM SalesReturnItem sri WHERE sri.pieceNo = :pieceNo AND sri.salesReturn.companyId = :companyId")
    List<SalesReturnItem> findByPieceNoAndCompanyId(@Param("pieceNo") String pieceNo, @Param("companyId") Long companyId);
}
