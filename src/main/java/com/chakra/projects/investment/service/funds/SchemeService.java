package com.chakra.projects.investment.service.funds;

import com.chakra.projects.investment.Domain.MutualFund.Scheme;
import com.chakra.projects.investment.service.amfi.AMFI;
import org.jdbi.v3.core.Jdbi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class SchemeService {

    @Autowired
    public FundManagerSvc fundManagerSvc;

    @Autowired
    public AMFI amfiSvc;

    public double getMarketPrice(Jdbi jdbi, String isin) {
        Scheme scheme = fundManagerSvc.getScheme(jdbi, isin);
        double navPrice = amfiSvc.getNav(scheme.getIsin());
        double marketValue = scheme.getMarketValue();
        if (navPrice != 0) {
            marketValue = navPrice * scheme.getCloseNavUnits();
        }
        return marketValue;
    }
}
