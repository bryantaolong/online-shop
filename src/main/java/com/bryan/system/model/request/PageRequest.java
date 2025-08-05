package com.bryan.system.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * PageRequest
 *
 * @author Bryan Long
 * @version 1.0
 * @since 2025/7/26
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageRequest {
    private Long pageNum;

    private Long pageSize;
}
