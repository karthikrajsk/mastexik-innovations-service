package com.tech3.erp.repository;

import com.tech3.erp.entity.ApplicationUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ApplicationUserRepository extends JpaRepository<ApplicationUser, Long> {
    
    Optional<ApplicationUser> findByUserName(String userName);
    
    List<ApplicationUser> findByRole(String role);
    
    
    @Query("SELECT u FROM ApplicationUser u WHERE LOWER(u.userName) LIKE LOWER(CONCAT('%', :search, '%')) OR LOWER(u.role) LIKE LOWER(CONCAT('%', :search, '%'))")
    List<ApplicationUser> searchByUserNameOrRole(@Param("search") String search);
    
    Optional<ApplicationUser> findByUserNameIgnoreCase(String userName);
    
    boolean existsByUserName(String userName);
}