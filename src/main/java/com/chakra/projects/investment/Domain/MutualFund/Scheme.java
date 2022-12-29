package com.chakra.projects.investment.Domain.MutualFund;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
@Entity
public class Scheme {

    @JsonProperty("scheme")
    private String schemeName;

    private SchemeType type;

    private String advisor;

    @Id
    private String isin;
//    private List<FundTransaction> transactions;
}
