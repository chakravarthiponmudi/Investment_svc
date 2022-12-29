package com.chakra.projects.investment.Domain.MutualFund;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
@Entity
public class FundTransaction {

    @Id
    private Integer id;
    private Date date;
    private String description;
    private TransactionType type;
    private double amount;
    private double units;
    private double nav;
    @JsonProperty("balance")
    private double runningUnitBalance;
    private double dividend_rate;

}
