package com.tech3.erp.repository;

import com.tech3.erp.entity.LLMQueryResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LLMQueryResultRepository extends JpaRepository<LLMQueryResult, Long> {
}