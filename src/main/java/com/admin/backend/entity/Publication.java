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
@TableName("publication")
public class Publication implements Serializable {

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

    @TableField("author")
    private String author;

    @TableField("year")
    private String year;

    @TableField("doi")
    private String doi;

    @TableField("link")
    private String link;

    @TableField("keyword")
    private String keyword;

    @TableField("description")
    private String description;

    @TableField("institution_abb")
    private String institutionAbb;
}
