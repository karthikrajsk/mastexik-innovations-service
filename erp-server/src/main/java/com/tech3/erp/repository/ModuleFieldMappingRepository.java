package com.tech3.erp.repository;

import com.tech3.erp.entity.ModuleFieldMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ModuleFieldMappingRepository extends JpaRepository<ModuleFieldMapping, Long> {
    
    List<ModuleFieldMapping> findByModuleName(String moduleName);
    
    List<ModuleFieldMapping> findByModuleNameAndSectionName(String moduleName, String sectionName);
    
    Optional<ModuleFieldMapping> findByModuleNameAndSectionNameAndFieldName(
        String moduleName, String sectionName, String fieldName);
    
    @Query("SELECT DISTINCT m.moduleName FROM ModuleFieldMapping m WHERE m.activeFlag = true")
    List<String> findAllModuleNames();
    
    @Query("SELECT DISTINCT m.sectionName FROM ModuleFieldMapping m WHERE m.moduleName = :moduleName AND m.activeFlag = true")
    List<String> findSectionsByModuleName(@Param("moduleName") String moduleName);
}