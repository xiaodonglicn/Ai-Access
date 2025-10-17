package test.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class AliCloudQuotaResponse {
    @JsonProperty("Data")
    private CurrData data;
    
    @JsonProperty("RequestId")
    private String requestId;
    
    @JsonProperty("Success")
    private Boolean success;
    

}