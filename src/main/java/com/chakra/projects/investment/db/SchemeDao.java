package com.chakra.projects.investment.db;

import com.chakra.projects.investment.Domain.MutualFund.Folio;
import com.chakra.projects.investment.Domain.MutualFund.Scheme;
import com.chakra.projects.investment.db.mapper.FolioMapper;
import com.chakra.projects.investment.db.mapper.SchemeMapper;
import org.jdbi.v3.sqlobject.config.RegisterRowMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import java.util.List;

public interface SchemeDao {


    @SqlQuery("select * from SCHEME")
    @RegisterRowMapper(SchemeMapper.class)
    List<Scheme> findAll();

    @SqlUpdate("insert into SCHEME (FOLIO_ID, NAME, TYPE,ADVISOR, ISIN) VALUES(:folio_id, :scheme.schemeName, :scheme.type,:scheme.advisor, :scheme.isin)")
    Number save(@Bind("folio_id") Integer folio_id, @BindBean("scheme") Scheme scheme);

}
