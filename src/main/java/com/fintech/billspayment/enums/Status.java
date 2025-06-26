package com.fintech.billspayment.enums;

import lombok.Getter;

@Getter
public enum Status {
    SUCCESS,
    PENDING,
    FAILED,
    REVERSED;
}
