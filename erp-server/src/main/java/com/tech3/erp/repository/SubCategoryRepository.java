// package com.tech3.erp.repository;

// import com.tech3.erp.entity.SubCategory;
// import org.springframework.data.domain.Page;
// import org.springframework.data.domain.Pageable;
// import org.springframework.data.jpa.repository.EntityGraph;
// import org.springframework.data.jpa.repository.JpaRepository;
// import org.springframework.data.jpa.repository.Query;
// import org.springframework.data.repository.query.Param;
// import org.springframework.stereotype.Repository;
// import java.util.List;
// import java.util.Optional;

// @Repository
// public interface SubCategoryRepository extends JpaRepository<SubCategory, Long> {
    
// //    @EntityGraph(attributePaths = "category")
// //    @Query("SELECT s FROM SubCategory s WHERE s.activeFlag = true ORDER BY s.id DESC")
// //    List<SubCategory> findAllActive();
// //    
// //    @EntityGraph(attributePaths = "category")
// //    @Query("SELECT s FROM SubCategory s WHERE s.activeFlag = true ORDER BY s.id DESC")
// //    Page<SubCategory> findAllActivePaginated(Pageable pageable);
    
//     @EntityGraph(attributePaths = "category")
//     @Query("SELECT s FROM SubCategory s WHERE s.id = :id")
//     Optional<SubCategory> findByIdWithCategory(@Param("id") Long id);
    
//     @EntityGraph(attributePaths = "category")
//     Optional<SubCategory> findBySubCategoryName(String subCategoryName);
    
//     @EntityGraph(attributePaths = "category")
//     List<SubCategory> findByCategoryCategoryName(String categoryName);
    
//     @EntityGraph(attributePaths = "category")
//     @Query("SELECT s FROM SubCategory s ORDER BY s.id DESC")
//     List<SubCategory> findAllWithCategory();

//     @EntityGraph(attributePaths = "category")
//     @Query("SELECT s FROM SubCategory s ORDER BY s.id DESC")
//     Page<SubCategory> findAllWithCategoryPaginated(Pageable pageable);
    
//     boolean existsByCategoryIdAndSubCategoryNameAndIdNot(Long categoryId, String subCategoryName, Long excludeId);
    
//     @Query("SELECT COUNT(s) > 0 FROM SubCategory s WHERE s.category.id = :categoryId AND s.subCategoryName = :subCategoryName AND s.id != :excludeId")
//     boolean existsByCategoryAndSubCategoryNameExcludingId(
//         @Param("categoryId") Long categoryId, 
//         @Param("subCategoryName") String subCategoryName,
//         @Param("excludeId") Long excludeId
//     );
// }

package com.tech3.erp.repository;

import com.tech3.erp.entity.SubCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface SubCategoryRepository extends JpaRepository<SubCategory, Long> {
    
//    @EntityGraph(attributePaths = "category")
//    @Query("SELECT s FROM SubCategory s WHERE s.activeFlag = true ORDER BY s.id DESC")
//    List<SubCategory> findAllActive();
//    
//    @EntityGraph(attributePaths = "category")
//    @Query("SELECT s FROM SubCategory s WHERE s.activeFlag = true ORDER BY s.id DESC")
//    Page<SubCategory> findAllActivePaginated(Pageable pageable);
    
    @EntityGraph(attributePaths = "category")
    @Query("SELECT s FROM SubCategory s WHERE s.id = :id")
    Optional<SubCategory> findByIdWithCategory(@Param("id") Long id);
    
    @EntityGraph(attributePaths = "category")
    Optional<SubCategory> findBySubCategoryName(String subCategoryName);
    
    @EntityGraph(attributePaths = "category")
    List<SubCategory> findByCategoryCategoryName(String categoryName);
    
    @EntityGraph(attributePaths = "category")
    @Query("SELECT s FROM SubCategory s ORDER BY s.id DESC")
    List<SubCategory> findAllWithCategory();

    @EntityGraph(attributePaths = "category")
    @Query("SELECT s FROM SubCategory s ORDER BY s.id DESC")
    Page<SubCategory> findAllWithCategoryPaginated(Pageable pageable);
    
    boolean existsByCategoryIdAndSubCategoryNameAndIdNot(Long categoryId, String subCategoryName, Long excludeId);
    
    @EntityGraph(attributePaths = "category")
    @Query("SELECT s FROM SubCategory s WHERE s.activeFlag = true OR s.id = :id")
    List<SubCategory> findAllActiveOrId(@Param("id") Long id);

    @EntityGraph(attributePaths = "category")
    List<SubCategory> findByActiveFlagTrue();

    List<SubCategory> findByCategory_IdAndIdNot(Long categoryId, Long excludeId);

}