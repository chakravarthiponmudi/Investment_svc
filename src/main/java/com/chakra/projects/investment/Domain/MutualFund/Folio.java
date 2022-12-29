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
public class Folio {
    @Id
    private Integer id;
//    private List<Scheme> schemes;
    @JsonProperty("folio")
    private String folioNo;
    private String amc;

}
