package com.chakra.projects.investment.Domain.cams;

import com.chakra.projects.investment.Domain.MutualFund.Folio;
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
public class CAS {
    @JsonProperty("cas_type")
    private CASType type;
    private Date fromPeriod;
    private Date toPeriod;
    private List<Folio> folios;

    @JsonProperty("statement_period")
    public void periodDeserializer(Map<String,Object> period) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
        try {
            this.fromPeriod = formatter.parse((String) period.get("from"));
            this.toPeriod = formatter.parse((String) period.get("to"));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }


}
