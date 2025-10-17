package test.entity;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @Author: Yeast
 * @Date: 2025/10/17 9:26 AM
 */
@Data
public class BlogGenerateRequest {

    @NotBlank(message = "博客标题不能为空")
    private String title;

    private String keywords;

    private String contentOutline;

    private Integer targetLength = 200; // 简介目标长度
}
