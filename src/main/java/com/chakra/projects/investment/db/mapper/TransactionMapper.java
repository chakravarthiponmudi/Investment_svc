package com.chakra.projects.investment.db.mapper;

import com.chakra.projects.investment.Domain.MutualFund.FundTransaction;
import com.chakra.projects.investment.Domain.MutualFund.TransactionType;
import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TransactionMapper implements RowMapper<FundTransaction> {
    @Override
    public FundTransaction map(ResultSet rs, StatementContext ctx) throws SQLException {
        FundTransaction obj = new FundTransaction();
        obj.setId(rs.getInt("transaction_id"));
        obj.setDate(rs.getDate("transaction_date"));
        obj.setDescription(rs.getString("description"));
        obj.setAmount(rs.getDouble("amount"));
        obj.setUnits(rs.getDouble("units"));
        obj.setNav(rs.getDouble("nav"));
        obj.setType(TransactionType.forValues(rs.getString("type")));
        obj.setRunningUnitBalance(rs.getDouble("balance"));
        obj.setDividendRate(rs.getDouble("dividend_rate"));
        obj.setIsin(rs.getString("isin"));
        obj.setBatchId(rs.getString("batch_id"));
        return obj;
    }
}
