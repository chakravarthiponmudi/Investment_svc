package com.chakra.projects.investment.Domain.MutualFund;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@ToString
//@Builder
@NoArgsConstructor
public class FundTransaction {

    private Integer id;
    private Date date;
    private String description;
    private TransactionType type;
    private double amount;
    private double units;
    private double nav;
    @JsonProperty("balance")
    private double runningUnitBalance;
    @JsonProperty("dividend_rate")
    private double dividendRate;

    private String isin;

    private String batchId;

    private double investment;

    private double profit;


    public boolean isInvestment() {
        return this.type == TransactionType.PURCHASE_SIP || this.type == TransactionType.PURCHASE;
    }

    public boolean isRedemption() {
        return this.type == TransactionType.REDEMPTION;
    }

    public boolean isCharges() {
        return this.type == TransactionType.STT_TAX || this.type == TransactionType.STAMP_DUTY_TAX;
    }


}
