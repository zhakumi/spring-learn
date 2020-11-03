package com.wang.test.enums;

import com.baomidou.mybatisplus.core.enums.IEnum;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * @Author: wangcan
 * @Date: 2018/12/20 16:00
 */
public enum EnumA implements IEnum<String> {
    ENABLE("1", "启用"),
    DISENABLE("2", "禁用");
    private String status;
    private String desc;

    EnumA(final String status, final String desc) {
        this.status = status;
        this.desc = desc;
    }

    @Override
    public String getValue() {
        return this.status;
    }
    @JsonValue
    public String getDesc() {
        return this.desc;
    }

}
