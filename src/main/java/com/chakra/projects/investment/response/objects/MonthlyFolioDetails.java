package com.chakra.projects.investment.response.objects;

import com.chakra.projects.investment.Domain.MutualFund.Scheme;
import lombok.Data;

import java.util.HashMap;

@Data
public class MonthlyFolioDetails {
    private String year;
    private String month;
    private Scheme scheme;

    private HashMap<String,Double> folioDetails;

}
