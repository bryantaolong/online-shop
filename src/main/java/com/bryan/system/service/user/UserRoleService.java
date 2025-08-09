package com.bryan.system.service.user;

import com.bryan.system.domain.dto.RoleOptionDTO;
import com.bryan.system.domain.entity.user.UserRole;
import com.bryan.system.repository.user.UserRoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

/**
 * UserRoleService
 *
 * @author Bryan Long
 */
@Service
@RequiredArgsConstructor
public class UserRoleService {
    private final UserRoleRepository userRoleRepository;

    public List<RoleOptionDTO> listAll() {
        return userRoleRepository.findAll()
                .stream()
                .map(r -> new RoleOptionDTO(r.getId(), r.getRoleName()))
                .toList();
    }

    public List<UserRole> findByIds(Collection<Long> ids) {
        return userRoleRepository.findAllByIdIn(ids);
    }
}
