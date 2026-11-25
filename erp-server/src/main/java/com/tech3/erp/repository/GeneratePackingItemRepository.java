//package com.tech3.erp.repository;
//
//import com.tech3.erp.entity.GeneratePackingItem;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.stereotype.Repository;
//
//@Repository
//public interface GeneratePackingItemRepository extends JpaRepository<GeneratePackingItem, Long> {
//}


package com.tech3.erp.repository;

import com.tech3.erp.entity.GeneratePackingItem;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface GeneratePackingItemRepository extends JpaRepository<GeneratePackingItem, Long> {
    
    @Transactional
    @Modifying
    @Query("DELETE FROM GeneratePackingItem i WHERE i.generatePacking.id = :packingId")
    void deleteByGeneratePackingId(@Param("packingId") Long packingId);

    @Transactional
    @Modifying
    @Query("UPDATE GeneratePackingItem i SET i.invoiceId = :invoiceId, i.isInvoice = 1 WHERE i.pieceNo IN :pieceNumbers")
    void markInvoicedByPieceNumbers(@Param("pieceNumbers") List<String> pieceNumbers, @Param("invoiceId") Long invoiceId);

    @Transactional
    @Modifying
    @Query("UPDATE GeneratePackingItem i SET i.invoiceId = NULL, i.isInvoice = 0 WHERE i.invoiceId = :invoiceId")
    void resetInvoiceStatus(@Param("invoiceId") Long invoiceId);

    @Transactional
    @Modifying
    @Query("UPDATE GeneratePackingItem i SET i.isScannedPacking = 1 WHERE i.rollNo IN :rollNos")
    void markScannedByRollNumbers(@Param("rollNos") List<String> rollNos);

    @Transactional
    @Modifying
    @Query("UPDATE GeneratePackingItem i SET i.isScannedPacking = 0 WHERE i.rollNo IN :rollNos")
    void unmarkScannedByRollNumbers(@Param("rollNos") List<String> rollNos);
}
