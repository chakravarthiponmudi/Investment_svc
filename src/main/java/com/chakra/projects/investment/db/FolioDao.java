package com.chakra.projects.investment.db;

import com.chakra.projects.investment.Domain.MutualFund.Folio;
import com.chakra.projects.investment.db.mapper.FolioMapper;
import org.jdbi.v3.sqlobject.config.RegisterRowMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import java.util.Date;
import java.util.List;
public interface FolioDao {


//    @SqlQuery("CREATE TABLE FOLIO(\n" +
//            "    FOLIO_NO VARCHAR(100) NOT NULL,\n" +
//            "    AMC VARCHAR(150) NOT NULL,\n" +
//            "    FOLIO_ID INTEGER NOT NULL,\n" +
//            "--    FOLIO_ID INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),\n" +
//            "    PRIMARY KEY (FOLIO_ID)\n" +
//            ");")
//    void createTable();

    @SqlQuery("select * from FOLIO")
    @RegisterRowMapper(FolioMapper.class)
    List<Folio> findAll();



    @SqlUpdate("insert into FOLIO (FOLIO_NO, AMC) values(:folioNo, :amc)")
    Number save(@BindBean Folio folio);


    @SqlQuery("select * from FOLIO where FOLIO_NO = :folio_no")
    @RegisterRowMapper(FolioMapper.class)
    Folio getByNo(@Bind("folio_no") String folio_no);



}
