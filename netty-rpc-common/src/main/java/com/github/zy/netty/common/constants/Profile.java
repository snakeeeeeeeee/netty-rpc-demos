package com.github.zy.netty.common.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @version 1.0 created by zy on 2020/4/27 17:37
 */
@Getter
@AllArgsConstructor
public enum Profile {

    DEV("dev", "开发环境"),
    STABLE("stable", "测试环境"),
    PREPROD("preprod", "灰度环境"),
    PROD("prod", "正式环境"),
    UNKNOWN("unknown", "未知");

    private String type;

    private String desc;

    public static Profile get(String profileStr) {
        for (Profile profile : Profile.values()) {
            if(profile.getType().equals(profileStr)){
                return profile;
            }
        }
        return UNKNOWN;
    }
}
