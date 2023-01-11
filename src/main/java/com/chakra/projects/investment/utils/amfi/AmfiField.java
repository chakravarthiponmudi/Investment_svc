package com.chakra.projects.investment.utils.amfi;

public enum AmfiField {


        SCHEME_CODE,
        ISIN_GROWTH,
        ISIN_DIV,
        SCHEME_NAME,
        NAV_PRICE,
        NAV_DATE;

        public static AmfiField getEnum(String value) {
            switch(value) {
                case "Scheme Code":
                    return AmfiField.SCHEME_CODE;
                case  "ISIN Div Payout/ ISIN Growth":
                    return AmfiField.ISIN_GROWTH;
                case "ISIN Div Reinvestment":
                    return AmfiField.ISIN_DIV;
                case  "Scheme Name":
                    return AmfiField.SCHEME_NAME;
                case  "Net Asset Value":
                    return AmfiField.NAV_PRICE;
                case  "Date":
                    return AmfiField.NAV_DATE;
            }
            return null;
        }
}