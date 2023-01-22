package com.chakra.projects.investment.service.funds;

import com.chakra.projects.investment.Domain.MutualFund.FundTransaction;
import com.chakra.projects.investment.Domain.MutualFund.Scheme;
import com.chakra.projects.investment.db.SchemeDao;
import com.chakra.projects.investment.db.TransactionDao;
import com.chakra.projects.investment.service.amfi.AMFI;
import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.Jdbi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SchemeService {

    @Autowired
    public AMFI amfiSvc;

    public Scheme getScheme(Jdbi jdbi, String isin) {
        try(Handle h = jdbi.open()) {
            SchemeDao sDao = h.attach(SchemeDao.class);
            return sDao.findByIsin(isin);
        }
    }

    public Iterable<Scheme> getAllSchemes(Jdbi jdbi) {
        try (Handle h = jdbi.open()) {
            SchemeDao dao = h.attach(SchemeDao.class);
            return dao.findAll();
        }

    }

    public List<FundTransaction> getTransactionsForScheme(Jdbi jdbi, String isin) {
        try (Handle h = jdbi.open()) {
            TransactionDao tDao = h.attach(TransactionDao.class);
            return tDao.findBySchemeIsin(isin);
        }
    }

    public List<Scheme> getSchemeForFolio(Jdbi jdbi, Integer folioId, boolean withClosedFolios) {
        try (Handle h = jdbi.open()) {
            SchemeDao sDao = h.attach(SchemeDao.class);
            return withClosedFolios?sDao.findByFolioId(folioId):sDao.findOpenSchemesByFolioId(folioId);
        }
    }

    public double getMarketPrice(Jdbi jdbi, String isin) {
        Scheme scheme = getScheme(jdbi, isin);
        double navPrice = amfiSvc.getNav(scheme.getIsin());
        double marketValue = scheme.getMarketValue();
        if (navPrice != 0) {
            marketValue = navPrice * scheme.getCloseNavUnits();
        }
        return marketValue;
    }

    public Scheme calculateInvestment(Jdbi jdbi, String isin) {
        Scheme scheme = getScheme(jdbi, isin);
        List<FundTransaction> transactions = getTransactionsForScheme(jdbi, scheme.getIsin());
        scheme.setTransactions(transactions);


        return scheme.calculateInvestment();


    }


}
