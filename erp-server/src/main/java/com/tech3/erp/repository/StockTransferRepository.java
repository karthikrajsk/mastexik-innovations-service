package com.tech3.erp.repository;

import com.tech3.erp.entity.StockTransferEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface StockTransferRepository extends JpaRepository<StockTransferEntity, Long> {

    List<StockTransferEntity> findByDate(LocalDate date);

    List<StockTransferEntity> findByDateBetween(LocalDate startDate, LocalDate endDate);

    List<StockTransferEntity> findByFromGodownId(Long fromGodownId);

    List<StockTransferEntity> findByToGodownId(Long toGodownId);

    List<StockTransferEntity> findByGoodsType(String goodsType);

    @Query("SELECT st FROM StockTransferEntity st " +
           "LEFT JOIN FETCH st.fromGodown " +
           "LEFT JOIN FETCH st.toGodown " +
           "LEFT JOIN FETCH st.fabricItems " +
           "LEFT JOIN FETCH st.yarnItems " +
           "LEFT JOIN FETCH st.itemItems " +
           "WHERE st.id = :id")
    Optional<StockTransferEntity> findByIdWithDetails(@Param("id") Long id);

    boolean existsByFromGodownIdAndToGodownIdAndDate(Long fromGodownId, Long toGodownId, LocalDate date);

    @Query("SELECT DISTINCT st FROM StockTransferEntity st " +
           "LEFT JOIN FETCH st.fromGodown " +
           "LEFT JOIN FETCH st.toGodown " +
           "LEFT JOIN FETCH st.fabricItems " +
           "LEFT JOIN FETCH st.yarnItems " +
           "LEFT JOIN FETCH st.itemItems")
    List<StockTransferEntity> findAllWithDetails();

    @Query("SELECT st FROM StockTransferEntity st " +
           "LEFT JOIN FETCH st.fromGodown " +
           "LEFT JOIN FETCH st.toGodown " +
           "LEFT JOIN FETCH st.fabricItems " +
           "LEFT JOIN FETCH st.yarnItems " +
           "LEFT JOIN FETCH st.itemItems " +
           "WHERE st.id = :id AND st.companyId = :companyId")
    Optional<StockTransferEntity> findByIdAndCompanyId(@Param("id") Long id, @Param("companyId") Long companyId);

    @Query("SELECT st.id FROM StockTransferEntity st WHERE st.companyId = :companyId")
    List<Long> findIdsByCompanyId(@Param("companyId") Long companyId);

    @Query("SELECT DISTINCT st FROM StockTransferEntity st " +
           "LEFT JOIN FETCH st.fromGodown " +
           "LEFT JOIN FETCH st.toGodown " +
           "LEFT JOIN FETCH st.fabricItems " +
           "LEFT JOIN FETCH st.yarnItems " +
           "LEFT JOIN FETCH st.itemItems " +
           "WHERE st.companyId = :companyId AND st.id IN :ids")
    List<StockTransferEntity> findByCompanyIdAndIds(@Param("companyId") Long companyId, @Param("ids") List<Long> ids);

    void deleteByIdAndCompanyId(Long id, Long companyId);
}
