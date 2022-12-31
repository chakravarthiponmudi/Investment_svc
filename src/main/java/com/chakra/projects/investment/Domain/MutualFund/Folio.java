package com.chakra.projects.investment.Domain.MutualFund;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;


@Getter
@Setter
@ToString
public class Folio {
    private Integer id;
    private List<Scheme> schemes;
    @JsonProperty("folio")
    private String folioNo;
    private String amc;

}
