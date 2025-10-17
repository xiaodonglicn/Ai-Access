package test.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import test.config.TongYiConfig;
import test.entity.TongYiRequest;
import test.entity.TongYiResponse;

import javax.annotation.Resource;

@Slf4j
@Service
public class TongYiService {

    @Resource
    private TongYiConfig tongYiConfig;
    @Resource
    private RestTemplate restTemplate;
    @Resource
    private ObjectMapper objectMapper;

    public String generateBlogSummary(String title, String keywords, String contentOutline, Integer targetLength) {
        try {
            String prompt = buildPrompt(title, keywords, contentOutline, targetLength);
            TongYiRequest request = buildTongYiRequest(prompt);
            log.info("调用通义千问API，模型: {}", tongYiConfig.getModel());
            log.debug("请求Prompt: {}", prompt);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Authorization", "Bearer " + tongYiConfig.getKey());
            HttpEntity<TongYiRequest> entity = new HttpEntity<>(request, headers);
            ResponseEntity<TongYiResponse> response = restTemplate.exchange(
                    tongYiConfig.getUrl(),
                    HttpMethod.POST,
                    entity,
                    TongYiResponse.class
            );
            log.info("API响应状态: {}", response.getStatusCode());
            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                TongYiResponse responseBody = response.getBody();
                log.debug("完整响应: {}", objectMapper.writeValueAsString(responseBody));
                String summary = extractTextFromResponse(responseBody);
                if (summary != null && !summary.trim().isEmpty()) {
                    log.info("AI生成的博客简介成功，长度: {}", summary.length());
                    return summary.trim();
                } else {
                    log.warn("API返回内容为空");
                    throw new RuntimeException("AI返回内容为空");
                }
            } else {
                throw new RuntimeException("API调用失败: " + response.getStatusCode());
            }
        } catch (Exception e) {
            log.error("调用通义千问API失败", e);
            throw new RuntimeException("生成博客简介失败: " + e.getMessage());
        }
    }

    /**
     * 从响应中提取文本内容（适配多种响应格式）
     */
    private String extractTextFromResponse(TongYiResponse response) {
        if (response == null) {
            return null;
        }
        // 方式1：从output.text获取
        if (response.getOutput() != null && response.getOutput().getText() != null) {
            return response.getOutput().getText();
        }
        // 方式2：从choices[0].message.content获取
        if (response.getOutput() != null &&
                response.getOutput().getChoices() != null &&
                !response.getOutput().getChoices().isEmpty()) {
            TongYiResponse.Choice firstChoice = response.getOutput().getChoices().get(0);
            if (firstChoice.getMessage() != null && firstChoice.getMessage().getContent() != null) {
                return firstChoice.getMessage().getContent();
            }
        }
        return null;
    }

    private String buildPrompt(String title, String keywords, String contentOutline, Integer targetLength) {
        StringBuilder prompt = new StringBuilder();
        prompt.append("请你作为一个专业的博客作者，为下面的博客内容生成一段吸引人的简介。\n\n");

        prompt.append("博客标题：").append(title).append("\n");

        if (keywords != null && !keywords.trim().isEmpty()) {
            prompt.append("关键词：").append(keywords).append("\n");
        }

        if (contentOutline != null && !contentOutline.trim().isEmpty()) {
            prompt.append("内容大纲：").append(contentOutline).append("\n");
        }

        prompt.append("\n要求：\n");
        prompt.append("1. 简介长度约").append(targetLength).append("字\n");
        prompt.append("2. 语言生动有趣，能够吸引读者\n");
        prompt.append("3. 突出博客的核心价值和亮点\n");
        prompt.append("4. 不要使用'本文'、'本博客'这样的词语\n");
        prompt.append("5. 直接输出简介内容，不要有其他解释\n");

        return prompt.toString();
    }

    private TongYiRequest buildTongYiRequest(String prompt) {
        TongYiRequest request = new TongYiRequest();
        request.setModel(tongYiConfig.getModel());

        // 构建输入消息
        TongYiRequest.Input input = new TongYiRequest.Input();
        TongYiRequest.Message[] messages = new TongYiRequest.Message[1];
        messages[0] = new TongYiRequest.Message();
        messages[0].setRole("user");
        messages[0].setContent(prompt);
        input.setMessages(messages);
        request.setInput(input);

        // 构建参数 - 确保使用同步调用格式
        TongYiRequest.Parameters parameters = new TongYiRequest.Parameters();
        parameters.setResultFormat("message"); // 使用message格式
        parameters.setTemperature(0.8);
        parameters.setTopP(0.8);
        request.setParameters(parameters);

        return request;
    }
}