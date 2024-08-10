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
@TableName("award")
public class Award implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @TableId("id")
    private Integer id;

    @TableField("title")
    private String title;

    @TableField("picture")
    private String picture;

    @TableField("institution")
    private String institution;

    @TableField("team")
    private String team;

    @TableField("date")
    private String date;

    @TableField("link")
    private String link;

    @TableField("keyword")
    private String keyword;

    @TableField("description")
    private String description;

    @TableField("institution_abb")
    private String institutionAbb;

    @TableField("grade")
    private String grade;
}
