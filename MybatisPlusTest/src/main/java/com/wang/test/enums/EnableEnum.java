package com.wang.test.enums;


import com.baomidou.mybatisplus.annotation.IEnum;

/**
 * @Author: wangcan
 * @Date: 2020/11/12 14:17
 */
public enum EnableEnum implements IEnum<Integer> {
    ENABLE(1, "启用"),
    DISABLE(2, "禁用");
    private int value;
    private String desc;

    EnableEnum(final int value, final String desc) {
        this.value = value;
        this.desc = desc;
    }

    @Override
    public Integer getValue() {
        return this.value;
    }

}