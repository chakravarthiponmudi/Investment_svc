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

    private Integer folioId;

    @JsonProperty("valuation")
    public void periodDeserializer(Map<String,Object> valuation) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        try {
            this.navValue = Double.parseDouble((String) valuation.get("nav"));
            this.marketValue = Double.parseDouble((String) valuation.get("value"));
            this.navDate = formatter.parse((String) valuation.get("date"));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
