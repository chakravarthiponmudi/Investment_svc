package com.chakra.projects.investment.service.funds;

import com.chakra.projects.investment.Domain.MutualFund.Folio;
import com.chakra.projects.investment.Domain.MutualFund.FundTransaction;
import com.chakra.projects.investment.Domain.MutualFund.Scheme;
import com.chakra.projects.investment.db.FolioDao;
import com.chakra.projects.investment.db.SchemeDao;
import com.chakra.projects.investment.db.TransactionDao;
import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.Jdbi;
import org.springframework.stereotype.Service;

import java.net.http.HttpResponse;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FundManagerSvc {
    public Number addFolio(Jdbi jdbi, Folio folio) {

        try (Handle h = jdbi.open()) {
            FolioDao f = h.attach(FolioDao.class);
            SchemeDao sDao = h.attach(SchemeDao.class);
            TransactionDao tDao = h.attach(TransactionDao.class);

            h.useTransaction(handle -> {
                f.save(folio);
                folio.getSchemes().forEach(scheme -> {

                    Folio updateFolio = f.getByNo(folio.getFolioNo());
                    sDao.save(updateFolio.getId(),scheme);

                    scheme.getTransactions().forEach(transaction -> {
                        tDao.save(scheme.getIsin(), transaction);
                    });
                });
            });
            return f.save(folio);
        }
    }

    public Iterable<Folio> getAllFolio(Jdbi jdbi) {


        try (Handle h = jdbi.open()) {
            FolioDao f = h.attach(FolioDao.class);
            return f.findAll();
        }


    }

    public Folio getFolio(Jdbi jdbi, String folio_no) {
        try (Handle h = jdbi.open()) {
            FolioDao fDao = h.attach(FolioDao.class);
            SchemeDao sDao = h.attach(SchemeDao.class);
            TransactionDao tDao = h.attach(TransactionDao.class);
            Folio folioObj=  fDao.findByNo(folio_no);
            if (folioObj == null) {
                return null;
            }
            List<Scheme> schemes = sDao.findByFolioId(folioObj.getId());
            schemes = schemes.stream().map(scheme -> {
                List<FundTransaction> transactions = tDao.findBySchemeIsin(scheme.getIsin());
                scheme.setTransactions(transactions);
                return scheme;
            }).collect(Collectors.toList());
            folioObj.setSchemes(schemes);
            return folioObj;
        }
    }

    public Iterable<Scheme> getAllSchemes(Jdbi jdbi) {
        try (Handle h = jdbi.open()) {
            SchemeDao dao = h.attach(SchemeDao.class);
            return dao.findAll();
        }

    }

    public Iterable<FundTransaction> getAllTransactions(Jdbi jdbi) {
        try (Handle h = jdbi.open()) {
            TransactionDao dao = h.attach(TransactionDao.class);
            return dao.findAll();
        }

    }
}
