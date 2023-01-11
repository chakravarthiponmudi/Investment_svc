package com.chakra.projects.investment.db;

import com.chakra.projects.investment.Domain.MutualFund.Scheme;
import com.chakra.projects.investment.db.mapper.FolioMapper;
import com.chakra.projects.investment.db.mapper.SchemeMapper;
import org.jdbi.v3.sqlobject.config.RegisterRowMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import java.util.Date;
import java.util.List;

public interface SchemeDao {


    @SqlQuery("select * from scheme")
    @RegisterRowMapper(SchemeMapper.class)
    List<Scheme> findAll();

    @SqlQuery("select * from SCHEME where FOLIO_ID = :folio_id")
    @RegisterRowMapper(SchemeMapper.class)
    List<Scheme> findByFolioId(@Bind("folio_id") Integer folio_id );

    @SqlQuery("select * from SCHEME where ISIN = :isin")
    @RegisterRowMapper(SchemeMapper.class)
    Scheme findByIsin(@Bind("isin") String isin );



    @SqlQuery("select * from SCHEME where FOLIO_ID = :folio_id and scheme_close_date is null")
    @RegisterRowMapper(SchemeMapper.class)
    List<Scheme> findOpenSchemesByFolioId(@Bind("folio_id") Integer folio_id );


    @SqlQuery("select * from SCHEME where FOLIO_ID = :folio_id and ISIN = :isin")
    @RegisterRowMapper(SchemeMapper.class)
    Scheme findByFolioIdAndIsin(@Bind("folio_id") Integer folio_id, @Bind("isin") String isin);

    @SqlUpdate("insert into SCHEME ("
            + "FOLIO_ID, NAME, TYPE,ADVISOR, ISIN, AMFI, OPEN_NAV_UNITS, CLOSE_NAV_UNITS, CLOSE_CALCULATED, NAV_VALUE, NAV_DATE, MARKET_VALUE,STATEMENT_START_DATE, STATEMENT_END_DATE) "
            + "VALUES(:folio_id, :scheme.schemeName, :scheme.type,:scheme.advisor, :scheme.isin, :scheme.amfi, :scheme.openNavUnits, :scheme.closeNavUnits, :scheme.closeCalculated, :scheme.navValue, :scheme.navDate, :scheme.marketValue,"
            + " :scheme.statementStartDate, :scheme.statementEndDate)")
    Number save(@Bind("folio_id") Integer folio_id, @BindBean("scheme") Scheme scheme);

    @SqlUpdate("UPDATE SCHEME SET OPEN_NAV_UNITS = :scheme.openNavUnits,"
            + " CLOSE_NAV_UNITS = :scheme.closeNavUnits,"
            + " STATEMENT_START_DATE = :scheme.statementStartDate,"
            + " STATEMENT_END_DATE = :scheme.statementEndDate,"
            + " CLOSE_CALCULATED = :scheme.closeCalculated,"
            + " NAV_VALUE = :scheme.navValue,"
            + " NAV_DATE = :scheme.navDate,"
            + " MARKET_VALUE = :scheme.marketValue"
            + " where isin = :scheme.isin and folio_id = :scheme.folioId")
    void updateScheme(@BindBean("scheme") Scheme scheme);

    @SqlUpdate("UPDATE SCHEME SET  "
            + " SCHEME_CLOSE_DATE = :date"
            + " WHERE isin = :isin")
    int closeFolio(@Bind("isin") String isin, @Bind("date") Date date);
}
