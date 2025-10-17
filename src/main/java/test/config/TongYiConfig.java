package test.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "tongyi.api")
public class TongYiConfig {
    private String key;
    private String url;
    private String model;
}