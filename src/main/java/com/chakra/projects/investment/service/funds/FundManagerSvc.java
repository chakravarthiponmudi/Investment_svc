package com.chakra.projects.investment.service.funds;

import com.chakra.projects.investment.Domain.MutualFund.Folio;
import com.chakra.projects.investment.db.FolioDao;
import com.chakra.projects.investment.db.mapper.FolioMapper;
import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FundManagerSvc {



//    private FolioDao folioDao;

//    public FundManagerSvc(FolioDao folioDao) {
//        this.folioDao = folioDao;
//    }

    public Number addFolio(Jdbi jdbi, Folio folio) {

        try (Handle h = jdbi.open()) {
            FolioDao f = h.attach(FolioDao.class);
            return f.save(folio);
        }
    }

    public Iterable<Folio> getAllFolio(Jdbi jdbi) {


        try (Handle h = jdbi.open()) {
            FolioDao f = h.attach(FolioDao.class);
            return f.findAll();
        }


    }
}
