package com.chakra.projects.investment.response.objects;

import lombok.Data;

@Data
public class MonthWiseSummary {
    private String yearMonth;
    private double totalInvestment;
}
