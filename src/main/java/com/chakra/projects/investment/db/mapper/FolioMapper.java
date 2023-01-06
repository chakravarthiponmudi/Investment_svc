package com.chakra.projects.investment.db.mapper;

import com.chakra.projects.investment.Domain.MutualFund.Folio;
import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class FolioMapper implements RowMapper<Folio> {

    public FolioMapper() {

    }
    @Override
    public Folio map(ResultSet rs, StatementContext ctx) throws SQLException {
        Folio obj = new Folio();
        obj.setFolioNo(rs.getString("folio_no"));
        obj.setId(rs.getInt("folio_id"));
        obj.setAmc(rs.getString("amc"));
        return obj;
    }
}
