package test.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CurrData {
    @JsonProperty("AccountBalance")
    private Double accountBalance;

    @JsonProperty("AvailableAmount")
    private Double availableAmount;

    @JsonProperty("CreditAmount")
    private Double creditAmount;

    @JsonProperty("MyBankCreditAmount")
    private Double myBankCreditAmount;

    @JsonProperty("Currency")
    private String currency;

    @JsonProperty("AvailableCashAmount")
    private Double availableCashAmount;
}