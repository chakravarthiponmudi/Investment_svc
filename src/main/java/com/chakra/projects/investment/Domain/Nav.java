package com.chakra.projects.investment.Domain;

import com.chakra.projects.investment.utils.amfi.AmfiField;
import lombok.Getter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Getter
public class Nav {
    private String schemeCode;
    private String isin;
    private String schemeName;
    private Double price;
    private Date date;
    private boolean isGrowth = false;
    private boolean isDiv = false;
    private Nav() {

    }

    public static class NavBuilder {
        private String schemeCode;
        private String isin;
        private boolean isGrowth = false;
        private boolean isDiv = false;
        private String schemeName;
        private Double price;
        private Date date;
        public NavBuilder() {

        }

        public NavBuilder withKey(AmfiField key, String value) {
            switch(key) {
                case SCHEME_CODE:
                    this.schemeCode =  value;
                    break;
                case ISIN_DIV:
                    if (!value.equals("-")) {
                        this.isin = value;
                        this.isDiv = true;
                    }
                    break;
                case ISIN_GROWTH:
                    if (!value.equals("-")) {
                        this.isin = value;
                        this.isGrowth = true;
                    }
                    break;
                case NAV_DATE:
                    SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
                    try {
                        this.date = formatter.parse(value);
                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }
                    break;
                case NAV_PRICE:
                    try {
                        this.price =  Double.parseDouble(value);
                    } catch (Exception e) {
//                        System.err.println("Unable to  parse the nav price:" + value);
                        this.price= Double.valueOf(0);
                    }
                    break;
                case SCHEME_NAME:
                    this.schemeName = value;
                    break;
                default:
                    System.err.println("Key not supported: " + key);
            }
            return this;
        }

        public Nav build() {
            Nav obj =  new Nav();
            obj.date = this.date;

            if (this.isin == null) {
                return null;
            }
            obj.isin = this.isin;
            obj.price = this.price;
            obj.isDiv = this.isDiv;
            obj.isGrowth = this.isGrowth;
            obj.schemeName = this.schemeName;
            obj.schemeCode = this.schemeCode;
            return obj;
        }
    }
}
