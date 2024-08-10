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
@TableName("news")
public class News implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @TableId("id")
    private Integer id;

    @TableField("title")
    private String title;

    @TableField("picture")
    private String picture;

    @TableField("type")
    private String type;

    @TableField("author")
    private String author;

    @TableField("date")
    private String date;

    @TableField("place")
    private String place;

    @TableField("description")
    private String description;
}
