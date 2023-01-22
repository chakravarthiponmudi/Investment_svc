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
import java.util.Optional;

public interface TransactionDao {

    @SqlQuery("select * from TRANSACTIONS where ISIN = :isin order by TRANSACTION_DATE")
    @RegisterRowMapper(TransactionMapper.class)
    List<FundTransaction> findBySchemeIsin(@Bind("isin") String isin);

    @SqlQuery("select 1 from TRANSACTIONS WHERE DESCRIPTION = :t.description"
            + " AND TRANSACTION_DATE = :t.date "
            + " AND TYPE = :t.type"
            + " AND AMOUNT = :t.amount"
            + " AND UNITS = :t.units"
            + " AND NAV = :t.nav"
            + " AND BALANCE = :t.runningUnitBalance"
            + " AND BATCH_ID != :t.batchId")
    List<Integer> findDuplicateTransaction(@BindBean("t") FundTransaction transaction);

    @SqlQuery("select * from TRANSACTIONS")
    @RegisterRowMapper(TransactionMapper.class)
    List<FundTransaction> findAll();

    @SqlUpdate("INSERT INTO TRANSACTIONS (ISIN, TRANSACTION_DATE, DESCRIPTION, TYPE, AMOUNT, UNITS,NAV, BALANCE,DIVIDEND_RATE, BATCH_ID) "
            + "VALUES(:isin, :t.date, :t.description, :t.type, :t.amount, :t.units, :t.nav, :t.runningUnitBalance, :t.dividendRate, :t.batchId)")
    Number save(@Bind("isin") String isin, @BindBean("t") FundTransaction t);
}
