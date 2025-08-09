package com.bryan.system.util.id;

import org.springframework.util.IdGenerator;
import org.springframework.util.SimpleIdGenerator;

/**
 * IdUtils
 *
 * @author Bryan Long
 */
public final class IdUtils {
    private static final IdGenerator GENERATOR = new SimpleIdGenerator();

    public static String getSnowflakeNextIdStr() {
        // 返回 36 位 UUID（无横杠）或改成 toString()
        return GENERATOR.generateId().toString();
    }
}
