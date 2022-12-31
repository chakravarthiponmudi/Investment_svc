package com.chakra.projects.investment.service.funds;

import com.chakra.projects.investment.Domain.MutualFund.Folio;
import com.chakra.projects.investment.Domain.MutualFund.Scheme;
import com.chakra.projects.investment.db.FolioDao;
import com.chakra.projects.investment.db.SchemeDao;
import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.Jdbi;
import org.springframework.stereotype.Service;

@Service
public class FundManagerSvc {
    public Number addFolio(Jdbi jdbi, Folio folio) {

        try (Handle h = jdbi.open()) {
            FolioDao f = h.attach(FolioDao.class);
            SchemeDao sDao = h.attach(SchemeDao.class);

            h.useTransaction(handle -> {
                f.save(folio);
                folio.getSchemes().forEach(scheme -> {

                    Folio updateFolio = f.getByNo(folio.getFolioNo());
                    sDao.save(updateFolio.getId(),scheme);
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


    public Iterable<Scheme> getAllSchemes(Jdbi jdbi) {
        try (Handle h = jdbi.open()) {
            SchemeDao dao = h.attach(SchemeDao.class);
            return dao.findAll();
        }

    }
}
