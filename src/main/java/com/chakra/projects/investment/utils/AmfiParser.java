package com.chakra.projects.investment.utils;

import com.chakra.projects.investment.Domain.Nav;
import com.chakra.projects.investment.Domain.Nav.NavBuilder;
import com.chakra.projects.investment.utils.amfi.AmfiField;

import java.util.ArrayList;
import java.util.List;


public class AmfiParser {

    private static boolean headerBuilt = false;
    private static List<AmfiField> columns;
    public static Nav parseAmfiResponse(String csvData) throws Exception {
        if (!isDataLine(csvData)) {
            throw new Exception("Invalid line for parsing");
        }

        String[] tokens = csvData.split(";");

        if (!headerBuilt) {
            if (isHeader(tokens[0])) {
                buildHeader(tokens);
                headerBuilt = true;
            }
            return null;
        }

        if (!headerBuilt) {
            throw new Exception("Header is required for building but received : " + csvData);
        }

        return buildNav(tokens);
    }

    private static Nav buildNav(String[] tokens) {
        NavBuilder builder = new NavBuilder();
        for (int i=0;i< tokens.length;i++) {
            AmfiField field = columns.get(i);
            builder.withKey(field,tokens[i]);
        }
        return builder.build();
    }



    private static void buildHeader(String[] tokens) {
        columns = new ArrayList<>();
        for(String token : tokens) {
            columns.add(AmfiField.getEnum(token));
        }
    }
    private static boolean isHeader(String token) {
        return AmfiField.getEnum(token) == AmfiField.SCHEME_CODE;
    }

    public static boolean isDataLine(String csvData) {
        return csvData.contains(";");
    }

}
