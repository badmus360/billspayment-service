package com.fintech.billspayment.entity;

import com.fintech.billspayment.enums.Channel;
import com.fintech.billspayment.enums.Status;
import com.fintech.billspayment.enums.TrxCategory;
import com.fintech.billspayment.enums.TrxType;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String reference;
    private String sessionId;
    private String processorStatus;
    private String processorCode;
    private String processorMessage;
    private BigDecimal amount;
    private BigDecimal charges;
    private String beneficiaryName;
    private String beneficiaryAccountNo;
    private String beneficiaryBankCode;
    private String beneficiaryBankName;
    private String sourceAccountNo;
    private String sourceAccountName;
    private String sourceBankName;
    private String sourceBankCode;
    private String description;
    private String narration;
    @Enumerated(EnumType.STRING)
    private TrxType type;
    @Enumerated(EnumType.STRING)
    private TrxCategory category;
    @Enumerated(EnumType.STRING)
    private Channel channel;
    @Enumerated(EnumType.STRING)
    private Status status;
    private LocalDateTime transactionTime;
    private LocalDateTime createdAt;
}
