package com.tech3.erp.repository;

import com.tech3.erp.entity.Mill;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MillRepository extends JpaRepository<Mill, Long> {
    @Query("select m.millName from Mill m")
    List<String> findAllMillNames();
}
