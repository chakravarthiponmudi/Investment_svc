package com.chakra.projects.investment.db;

import com.chakra.projects.investment.Domain.MutualFund.Folio;
import com.chakra.projects.investment.db.mapper.FolioMapper;
import org.jdbi.v3.sqlobject.config.RegisterRowMapper;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import java.util.List;
public interface FolioDao {

    @SqlQuery("select * from FOLIO")
    @RegisterRowMapper(FolioMapper.class)
    List<Folio> findAll();

    @SqlUpdate("insert into FOLIO (FOLIO_NO, AMC) values(:folioNo, :amc)")
    Number save(@BindBean Folio folio);

}
