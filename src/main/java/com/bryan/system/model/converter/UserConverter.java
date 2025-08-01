package com.bryan.system.model.converter;

import com.bryan.system.model.entity.user.User;
import com.bryan.system.model.vo.UserExportVO;

/**
 * UserConvert
 *
 * @author Bryan Long
 * @version 1.0
 * @since 2025/7/26
 */
public class UserConverter {

    public static UserExportVO toExportVO(User user) {
        if (user == null) {
            return null;
        }

        UserExportVO vo = new UserExportVO();
        vo.setId(user.getId());
        vo.setUsername(user.getUsername());
        vo.setPhoneNumber(user.getPhoneNumber());
        vo.setEmail(user.getEmail());
        vo.setStatus(convertStatus(user.getStatus()));
        vo.setRoles(user.getRoles());
        vo.setLoginTime(user.getLoginTime());
        vo.setLoginIp(user.getLoginIp());
        vo.setPasswordResetTime(user.getPasswordResetTime());
        vo.setCreateTime(user.getCreateTime());
        vo.setCreateBy(user.getCreateBy());
        vo.setUpdateTime(user.getUpdateTime());
        vo.setUpdateBy(user.getUpdateBy());

        return vo;
    }

    private static String convertStatus(Integer status) {
        if (status == null) return "";
        return status == 0 ? "正常" : "禁用";
    }
}
