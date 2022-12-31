package com.chakra.projects.investment.db.mapper;

import com.chakra.projects.investment.Domain.MutualFund.Scheme;
import com.chakra.projects.investment.Domain.MutualFund.SchemeType;
import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;

import javax.swing.tree.TreePath;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SchemeMapper implements RowMapper<Scheme> {
    @Override
    public Scheme map(ResultSet rs, StatementContext ctx) throws SQLException {
        Scheme obj = new Scheme();
        obj.setAdvisor(rs.getString("advisor"));
        obj.setSchemeName(rs.getString("name"));
        obj.setType(SchemeType.getSchemeType(rs.getString("type")));
        obj.setIsin(rs.getString("isin"));
        obj.setFolio_id(rs.getInt("folio_id"));
        return obj;
    }

}