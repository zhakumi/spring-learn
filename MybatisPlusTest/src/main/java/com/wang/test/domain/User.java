package com.wang.test.domain;


import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.wang.test.enums.EnumA;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @Author: wangcan
 * @Date: 2018/12/20 15:08
 */
@Data
public class User {
    private Long id;
    private String name;
    private EnumA status;
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
}
