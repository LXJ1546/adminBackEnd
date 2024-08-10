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
@TableName("member")
public class Member implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @TableId("id")
    private Integer id;

    @TableField("name")
    private String name;

    @TableField("avatar")
    private String avatar;

    @TableField("type")
    private String type;

    @TableField("identity")
    private String identity;

    @TableField("research")
    private String research;

    @TableField("email")
    private String email;

    @TableField("phone")
    private String phone;

    @TableField("whereabouts")
    private String whereabouts;

    @TableField("year")
    private String year;

    @TableField("description")
    private String description;
}
