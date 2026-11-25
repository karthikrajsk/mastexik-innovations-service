package com.tech3.erp.repository;

import com.tech3.erp.entity.RequestFormRoundItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface RequestFormRoundItemRepository extends JpaRepository<RequestFormRoundItem, Long> {
    List<RequestFormRoundItem> findByRoundId(Long roundId);
}
