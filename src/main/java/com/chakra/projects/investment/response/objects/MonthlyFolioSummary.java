package com.chakra.projects.investment.response.objects;

import lombok.Data;

@Data
public class MonthlyFolioSummary {
    private String year;
    private String month;
    private String amc;
    private Integer folio_id;
    private Double totalInvestment;

    public void addInvestment(Double amount) {
        this.totalInvestment += amount;
    }

}
