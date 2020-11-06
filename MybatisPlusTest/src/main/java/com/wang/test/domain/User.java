package com.wang.test.domain;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.wang.test.enums.EnumA;

import java.time.LocalDateTime;

import lombok.Data;

/**
 * @Author: wangcan
 * @Date: 2018/12/20 15:08
 */
@Data
@TableName("OMS_USER")
public class User {
    @TableId(value = "ID_STR", type = IdType.AUTO)
    private Long id;
    private String name;
    private String password;
    private EnumA status;
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
}
