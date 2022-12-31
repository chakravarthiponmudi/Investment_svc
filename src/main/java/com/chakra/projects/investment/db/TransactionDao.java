package com.chakra.projects.investment.db;

import com.chakra.projects.investment.Domain.MutualFund.FundTransaction;
import com.chakra.projects.investment.Domain.MutualFund.Scheme;
import com.chakra.projects.investment.db.mapper.TransactionMapper;
import org.jdbi.v3.sqlobject.config.RegisterRowMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import java.util.List;

public interface TransactionDao {

    @SqlQuery("select * from TRANSACTIONS where ISIN = :isin")
    @RegisterRowMapper(TransactionMapper.class)
    List<FundTransaction> findBySchemeIsin(@Bind("isin") String isin);

    @SqlQuery("select * from TRANSACTIONS")
    @RegisterRowMapper(TransactionMapper.class)
    List<FundTransaction> findAll();

    @SqlUpdate("INSERT INTO TRANSACTIONS (ISIN, TRANSACTION_DATE, DESCRIPTION, TYPE, AMOUNT, UNITS,NAV, BALANCE,DIVIDEND_RATE) VALUES(:isin, :t.date, :t.description, :t.type, :t.amount, :t.units, :t.nav, :t.runningUnitBalance, :t.dividendRate)")
    Number save(@Bind("isin") String isin, @BindBean("t") FundTransaction t);
}
