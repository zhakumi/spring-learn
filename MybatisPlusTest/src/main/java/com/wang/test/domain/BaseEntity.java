package com.wang.test.domain;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;

import java.time.LocalDateTime;

import lombok.Data;

/**
 * @Author: wangcan
 * @Date: 2020/11/9 10:13
 */
@Data
public class BaseEntity {
    /**
     * 创建日期
     */
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

}
