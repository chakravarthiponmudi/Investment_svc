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

    public void addFolio(Folio folio) {
//        folioRepo.save(folio);
    }

    public Iterable<Folio> getAllFolio(Jdbi jdbi) {

//        jdbi.installPlugin(new SqlObjectPlugin());

        try (Handle h = jdbi.open()) {
            FolioDao f = h.attach(FolioDao.class);
            return f.findAll();
        }




//        return jdbi.withExtension(FolioDao.class, dao -> dao.findAll());
//        return jdbi.withHandle(handle -> {
//
////            handle.execute("CREATE TABLE FOLIO(\n" +
////                    "    FOLIO_NO VARCHAR(100) NOT NULL,\n" +
////                    "    AMC VARCHAR(150) NOT NULL,\n" +
////                    "    FOLIO_ID INTEGER NOT NULL,\n" +
////                    "--    FOLIO_ID INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),\n" +
////                    "    PRIMARY KEY (FOLIO_ID)\n" +
////                    ")");
//
//
//
//            return handle.createQuery("SELECT * FROM \"FOLIO\" ")
//                    .map(new FolioMapper())
//                    .list();
//        });


    }
}
