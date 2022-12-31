package com.chakra.projects.investment.Domain.MutualFund;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public enum TransactionType {

    PURCHASE("Purchase"),
    PURCHASE_SIP("Systematic Investment"),
    STAMP_DUTY_TAX("***Stamp Duty***"),
    REDEMPTION("REDEMPTION");

    private String description;

    TransactionType(String desc) {
        this.description = desc;
    }

    public String getDescription() {
        return this.description;
    }

    @JsonCreator
    public static  TransactionType forValues(String type) {
        for(TransactionType transaction : TransactionType.values()) {
            if (transaction.name().startsWith(type)) {
                return transaction;
            }
        }

        return null;
    }
}