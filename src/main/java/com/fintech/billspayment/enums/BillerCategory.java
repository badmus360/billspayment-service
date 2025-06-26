package com.fintech.billspayment.enums;

import lombok.Getter;

@Getter
public enum BillerCategory {
    ELECTRICITY("Electricity", "ELECTRICITY"),
    CABLE_TV("Cable", "CABLE_TV"),
    INTERNET("Internet", "INTERNET"),
    EXAMS_AND_RESULTS("Exams", "EXAMS_AND_RESULTS"),
    BETTING("Betting", "BETTING"),
    AIRTIME("Airtime", "AIRTIME"),
    DATA("Data", "DATA");

    private final String name;
    private final String code;

    BillerCategory(String name, String code) {
        this.name = name;
        this.code = code;
    }
}
