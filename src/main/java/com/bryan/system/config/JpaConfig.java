package com.bryan.system.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * JpaConfig
 *
 * @author Bryan Long
 */
@Configuration
@EntityScan("com.bryan.system.domain.entity")
@EnableJpaAuditing
public class JpaConfig {
}
