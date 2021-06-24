package com.wang.test.domain;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.Version;
import com.wang.test.enums.EnableEnum;

import lombok.Data;

/**
 * @Author: wangcan
 * @Date: 2018/12/20 15:08
 */
@Data
@TableName("OMS_USER")
public class User extends BaseEntity{
    @TableId(value = "ID", type = IdType.ASSIGN_ID)
    private Long id;
    private String userName;
    private String userPassword;
    private EnableEnum userStatus;
    @Version
    @TableField(value = "version", fill = FieldFill.INSERT)
    private Integer version;
}
