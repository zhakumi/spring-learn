package com.wang.test.enums;

import com.baomidou.mybatisplus.core.enums.IEnum;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * @Author: wangcan
 * @Date: 2018/12/20 16:00
 */
public enum EnumA implements IEnum<Integer> {
    ENABLE(1, "启用"),
    DISENABLE(2, "禁用");
    private Integer value;
    private String desc;

    EnumA(final Integer value, final String desc) {
        this.value = value;
        this.desc = desc;
    }

    @Override
    public Integer getValue() {
        return this.value;
    }

}
