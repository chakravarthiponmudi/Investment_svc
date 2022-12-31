package com.chakra.projects.investment.Domain.MutualFund;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public enum SchemeType {
    EQUITY,
    DEBT;

    public static SchemeType getSchemeType(String type) {
        if( type.equals("EQUITY")) {
            return SchemeType.EQUITY;
        } else if ( type.equals("DEBT")) {
            return SchemeType.DEBT;
        }

        log.error("Invalid type" +  type);
        return null;
    }
}
