package com.fintech.billspayment.request;

import com.fintech.billspayment.enums.Channel;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BillPaymentRequest {
    private String biller;
    private String product;
    private BigDecimal amount;
    private String sourceAccountNo;
    private String sourceAccountName;
    private String description;
    private Channel channel;
}
