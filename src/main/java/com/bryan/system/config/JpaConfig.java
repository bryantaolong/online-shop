package com.bryan.system.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * JpaConfig
 *
 * @author Bryan Long
 * @version 1.0
 * @since 2025/8/6
 */
@Configuration
@EntityScan("com.bryan.system.model.entity")
@EnableJpaAuditing
public class JpaConfig {
}
