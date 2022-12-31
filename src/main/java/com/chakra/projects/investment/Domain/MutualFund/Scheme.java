package com.chakra.projects.investment.Domain.MutualFund;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class Scheme {

    @JsonProperty("scheme")
    private String schemeName;

    private SchemeType type;

    private String advisor;

    private String isin;
//    private List<FundTransaction> transactions;
}
