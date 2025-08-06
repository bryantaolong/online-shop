package com.bryan.system.service;

import com.bryan.system.model.entity.UserProfile;
import com.bryan.system.repository.UserProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * UserProfileService
 *
 * @author Bryan Long
 * @version 1.0
 * @since 2025/8/6
 */
@Service
@Transactional
@RequiredArgsConstructor
public class UserProfileService {

    private final UserProfileRepository userProfileRepository;

    public Page<UserProfile> page(Long userId, int page, int size) {
        return userProfileRepository.findByUserIdAndDeletedFalse(
                userId,
                PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createTime"))
        );
    }

    public UserProfile save(UserProfile entity) {
        return userProfileRepository.save(entity); // insert or update
    }

    public Optional<UserProfile> get(Long id) {
        return userProfileRepository.findById(id);
    }

    public void softDelete(Long id) {
        userProfileRepository.findById(id).ifPresent(e -> {
            e.setDeleted(1);
            userProfileRepository.save(e);   // 触发 @SQLDelete
        });
    }
}
