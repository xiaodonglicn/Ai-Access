package test.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class TongYiRequest {
    @JsonProperty("model")
    private String model;

    @JsonProperty("input")
    private Input input;

    @JsonProperty("parameters")
    private Parameters parameters;

    @Data
    public static class Input {
        @JsonProperty("messages")
        private Message[] messages;
    }

    @Data
    public static class Message {
        @JsonProperty("role")
        private String role;

        @JsonProperty("content")
        private String content;
    }

    @Data
    public static class Parameters {
        @JsonProperty("result_format")
        private String resultFormat = "message";

        // 添加温度参数控制随机性
        @JsonProperty("temperature")
        private Double temperature = 0.8;

        // 添加top_p参数
        @JsonProperty("top_p")
        private Double topP = 0.8;
    }
}