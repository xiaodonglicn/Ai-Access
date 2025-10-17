package test.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class TongYiResponse {
    @JsonProperty("output")
    private Output output;

    @JsonProperty("usage")
    private Usage usage;

    @JsonProperty("request_id")
    private String requestId;

    @Data
    public static class Output {
        @JsonProperty("text")
        private String text;

        @JsonProperty("finish_reason")
        private String finishReason;

        // 通义千问可能使用choices数组
        @JsonProperty("choices")
        private java.util.List<Choice> choices;
    }

    @Data
    public static class Choice {
        @JsonProperty("message")
        private Message message;

        @JsonProperty("finish_reason")
        private String finishReason;
    }

    @Data
    public static class Message {
        @JsonProperty("content")
        private String content;
    }

    @Data
    public static class Usage {
        @JsonProperty("input_tokens")
        private Integer inputTokens;

        @JsonProperty("output_tokens")
        private Integer outputTokens;

        @JsonProperty("total_tokens")
        private Integer totalTokens;
    }

    // 获取文本内容的辅助方法
    public String getResponseText() {
        if (output != null) {
            // 优先从choices中获取
            if (output.getChoices() != null && !output.getChoices().isEmpty()) {
                Message message = output.getChoices().get(0).getMessage();
                if (message != null && message.getContent() != null) {
                    return message.getContent();
                }
            }
            // 其次从text字段获取
            if (output.getText() != null) {
                return output.getText();
            }
        }
        return null;
    }
}