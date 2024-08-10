package com.admin.backend.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serial;
import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 
 * </p>
 *
 * @author lxj
 * @since 2024-08-05
 */
@Getter
@Setter
@TableName("user")
public class User implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @TableId("id")
    private Integer id;

    @TableField("name")
    private String name;

    @TableField("avatar")
    private String avatar;

    @TableField("role")
    private String role;

    @TableField("sex")
    private String sex;

    @TableField("password")
    private String password;

    @TableField("email")
    private String email;

    @TableField("date")
    private String date;

    @TableField("status")
    private String status;

    @TableField("token")
    private String token;
}
