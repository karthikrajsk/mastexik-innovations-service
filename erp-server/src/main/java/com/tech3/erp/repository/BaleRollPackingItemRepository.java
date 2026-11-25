package com.tech3.erp.repository;

import com.tech3.erp.entity.BaleRollPackingItem;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BaleRollPackingItemRepository extends JpaRepository<BaleRollPackingItem, Long> {

    @Modifying
    @Query("DELETE FROM BaleRollPackingItem i WHERE i.baleRollPacking.id = :packingId")
    void deleteByPackingId(Long packingId);

    List<BaleRollPackingItem> findByPieceId(Long pieceId);

    List<BaleRollPackingItem> findByBaleRollPackingId(Long packingId);
}