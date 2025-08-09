package com.bryan.system.repository;

import com.bryan.system.domain.entity.UserProfile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * UserProfileRepository
 *
 * @author Bryan Long
 */
@Repository
public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {

    Page<UserProfile> findByUserIdAndDeletedFalse(Long userId, Pageable pageable);
}
