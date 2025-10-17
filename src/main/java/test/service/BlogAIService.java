package test.service;

import org.springframework.stereotype.Service;
import test.entity.BlogGenerateRequest;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Service
public class BlogAIService {

    @Resource
    private TongYiService tongYiService;
    
    public String generateBlogSummary(BlogGenerateRequest request) {
        // 参数验证和默认值处理
        String title = request.getTitle();
        String keywords = request.getKeywords();
        String contentOutline = request.getContentOutline();
        Integer targetLength = request.getTargetLength() != null ? request.getTargetLength() : 200;
        
        // 调用AI服务生成简介
        return tongYiService.generateBlogSummary(title, keywords, contentOutline, targetLength);
    }
    
    // 批量生成简介的方法
    public Map<String, String> generateBatchSummaries(Map<String, BlogGenerateRequest> requests) {
        Map<String, String> results = new HashMap<>();
        for (Map.Entry<String, BlogGenerateRequest> entry : requests.entrySet()) {
            try {
                String summary = generateBlogSummary(entry.getValue());
                results.put(entry.getKey(), summary);
            } catch (Exception e) {
                results.put(entry.getKey(), "生成失败: " + e.getMessage());
            }
        }
        return results;
    }
}