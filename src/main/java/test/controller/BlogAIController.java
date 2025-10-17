package test.controller;

import org.springframework.web.bind.annotation.*;
import test.entity.ApiResponse;
import test.entity.BlogGenerateRequest;
import test.service.BlogAIService;

import javax.annotation.Resource;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/blog")
public class BlogAIController {

    @Resource
    private BlogAIService blogAIService;
    
    @PostMapping("/generate-summary")
    public ApiResponse<String> generateBlogSummary(@Valid @RequestBody BlogGenerateRequest request) {
        try {
            String summary = blogAIService.generateBlogSummary(request);
            return ApiResponse.success(summary);
        } catch (Exception e) {
            return ApiResponse.error("生成博客简介失败: " + e.getMessage());
        }
    }
    
    @GetMapping("/health")
    public ApiResponse<String> healthCheck() {
        return ApiResponse.success("服务运行正常");
    }
}