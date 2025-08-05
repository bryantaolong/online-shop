package com.bryan.system.model.entity.product;

import com.baomidou.mybatisplus.annotation.*;
import com.bryan.system.model.entity.BaseEntity;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * ProductCategory 商品分类
 *
 * @author Bryan Long
 * @version 1.0
 * @since 2025/8/1
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("pms_category")
public class ProductCategory implements Serializable {
    @TableId(type = IdType.AUTO)
    private Long id;

    private String name;
    private Long parentId;
    private Integer level;
    private Integer sortOrder;

    /** 逻辑删除 */
    @TableLogic
    private Integer deleted;

    /** 乐观锁 */
    @Version
    private Integer version;

    /** 创建时间 */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /** 更新时间 */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    /** 创建人 */
    @TableField(fill = FieldFill.INSERT)
    private String createBy;

    /** 更新人 */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String updateBy;
}
