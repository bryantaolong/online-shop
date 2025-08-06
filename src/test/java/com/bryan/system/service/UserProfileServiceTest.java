package com.bryan.system.service;

import com.bryan.system.common.enums.GenderEnum;
import com.bryan.system.model.entity.UserProfile;
import com.bryan.system.repository.UserProfileRepository;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * UserProfileServiceTest
 *
 * @author Bryan Long
 * @version 1.0
 * @since 2025/8/6
 */
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserProfileServiceTest {

    @Autowired
    UserProfileRepository repo;

    @Test
    @Order(1)
    void insert() {
        UserProfile p = UserProfile.builder()
                .realName("张三")
                .gender(GenderEnum.MALE)
                .birthday(LocalDateTime.now())
                .build();
        UserProfile saved = repo.save(p);
        assertThat(saved.getUserId()).isNotNull();
        assertThat(saved.getCreateTime()).isNotNull();
    }

    @Test
    @Order(2)
    void select() {
        Page<UserProfile> page = repo.findByUserIdAndDeletedFalse(-1L, PageRequest.of(0, 10));
        System.out.println(page.getContent());
    }

    @Test
    @Order(3)
    void updateAndOptimisticLock() {
        UserProfile p = repo.findAll().get(0);
        p.setAvatar("new.png");
        UserProfile updated = repo.save(p);
        assertThat(updated.getVersion()).isEqualTo(p.getVersion() + 1);
    }

    @Test
    @Order(4)
    void softDelete() {
        UserProfile p = repo.findAll().get(0);
        p.setDeleted(1);
        repo.save(p);
        assertThat(repo.findById(p.getUserId())).isEmpty(); // 因 @SQLRestriction 已过滤
    }
}
