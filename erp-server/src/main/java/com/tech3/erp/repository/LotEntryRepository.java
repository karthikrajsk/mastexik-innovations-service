//package com.tech3.erp.repository;
//
//import com.tech3.erp.entity.LotEntry;
//import com.tech3.erp.entity.YarnMaster;
//
//import java.util.List;
//import java.util.Optional;
//
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.stereotype.Repository;
//
//@Repository
//public interface LotEntryRepository extends JpaRepository<LotEntry, Long> {
//	List<LotEntry> findByQuantityNot(int quantity);
//
//	Optional<LotEntry> findByLotNumber(String lotNumber);
//
//}

package com.tech3.erp.repository;

import com.tech3.erp.entity.LotEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface LotEntryRepository extends JpaRepository<LotEntry, Long> {
    
    List<LotEntry> findByQuantityNot(Integer quantity);
    
    Optional<LotEntry> findByLotNumber(String lotNumber);

    
    // Find by poTypeId and fabricId with non-zero quantity
    List<LotEntry> findByPoTypeIdAndFabricIdAndQuantityNot(Integer poTypeId, Integer fabricId, Integer quantity);
    
    // Find by poTypeId with non-zero quantity
    List<LotEntry> findByPoTypeIdAndQuantityNot(Integer poTypeId, Integer quantity);
    
    // Find by fabricId with non-zero quantity
    List<LotEntry> findByFabricIdAndQuantityNot(Integer fabricId, Integer quantity);
    
    // Find by multiple fabricIds with non-zero quantity
    List<LotEntry> findByFabricIdInAndQuantityNot(List<Integer> fabricIds, Integer quantity);
    
    // Alternative using @Query if you need more complex logic
    @Query("SELECT l FROM LotEntry l WHERE l.quantity > 0 AND l.fabricId IN :fabricIds")
    List<LotEntry> findAvailableLotsByFabricIds(@Param("fabricIds") List<Integer> fabricIds);
}