package com.bryan.system.repository;

import com.bryan.system.domain.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * UserRoleRepository
 *
 * @author Bryan Long
 */
@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Long> {

    Optional<UserRole> findByIsDefaultTrue();

    List<UserRole> findAllByIdIn(Collection<Long> ids);
}
