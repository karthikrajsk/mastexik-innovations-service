package com.tech3.erp.repository;

import com.tech3.erp.entity.RequestFormRound;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface RequestFormRoundRepository extends JpaRepository<RequestFormRound, Long> {
    List<RequestFormRound> findByRequestFormId(Long requestFormId);
}
