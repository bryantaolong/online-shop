package com.bryan.system.repository.user;

import com.bryan.system.domain.entity.user.UserAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * UserAddressRepository
 *
 * @author Bryan Long
 */
@Repository
public interface UserAddressRepository extends JpaRepository<UserAddress, Long> {
}
