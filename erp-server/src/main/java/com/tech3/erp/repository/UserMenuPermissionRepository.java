package com.tech3.erp.repository;

import com.tech3.erp.entity.UserMenuPermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserMenuPermissionRepository extends JpaRepository<UserMenuPermission, Long> {
    List<UserMenuPermission> findByUserId(Long userId);
    List<UserMenuPermission> findByUserIdAndSubMenu_ActiveTrue(Long userId);
}
