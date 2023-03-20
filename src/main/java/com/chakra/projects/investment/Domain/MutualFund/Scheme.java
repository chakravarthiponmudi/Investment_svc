package com.chakra.projects.investment.Domain.MutualFund;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@ToString
public class Scheme {

    @JsonProperty("scheme")
    private String schemeName;

    private SchemeType type;

    private String advisor;

    private String isin;
    private List<FundTransaction> transactions;

    private String amfi;

    @JsonProperty("open")
    private double openNavUnits;

    @JsonProperty("close")
    private double closeNavUnits;

    @JsonProperty("close_calculated")
    private double closeCalculated;
    private double navValue;
    private Date navDate;

    private Date statementStartDate;
    private Date statementEndDate;
    private double marketValue;

    private Date schemeCloseDate;

    private Integer folioId;

    private Double investment;

    @JsonProperty("valuation")
    public void periodDeserializer(Map<String,Object> valuation) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        try {
            this.navValue = Double.parseDouble(valuation.get("nav").toString());
            this.marketValue = Double.parseDouble(valuation.get("value").toString());
            this.navDate = formatter.parse((String) valuation.get("date"));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public Scheme calculateInvestment() {
        if (transactions.isEmpty()) {
            this.investment = 0.0;
            return this;
        }
        double investment =0.0;
        double unitBalance = 0.0;

        for(FundTransaction transaction : transactions) {
            if (transaction.isInvestment()) {
                investment += transaction.getAmount();
                transaction.setInvestment(investment);
                unitBalance += transaction.getUnits();
                double marketValue = transaction.getNav() * transaction.getRunningUnitBalance();
                transaction.setProfit(marketValue - investment);
            }
            if (transaction.isRedemption()) {
                double redemptionAmount = transaction.getAmount();
                double marketValue = unitBalance * transaction.getNav();
                double reductionInInvestment = (redemptionAmount * investment) /marketValue;
                investment+=reductionInInvestment;
                unitBalance += transaction.getUnits();
                marketValue = transaction.getNav() * unitBalance;
                transaction.setProfit(marketValue - investment);
            }

        }

        this.investment = investment;

        return this;

    }

}
