package com.fintech.billspayment.service;

import com.fintech.billspayment.entity.Transaction;
import com.fintech.billspayment.enums.BillerCategory;
import com.fintech.billspayment.enums.Status;
import com.fintech.billspayment.enums.TrxCategory;
import com.fintech.billspayment.enums.TrxType;
import com.fintech.billspayment.repository.TransactionRepository;
import com.fintech.billspayment.request.BillPaymentRequest;
import com.fintech.billspayment.response.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BillPaymentServiceImpl implements BillPaymentService {

    private final TransactionRepository transactionRepository;

    @Override
    public BaseResponse<List<String>> getBillersByCategory(BillerCategory category) {
        return BaseResponse.<List<String>>builder()
                .code("00")
                .flag(true)
                .result(getBillers(category))
                .build();
    }

    @Override
    public BaseResponse<List<String>> getAllCategories() {
       return BaseResponse.<List<String>>builder()
               .code("00")
               .flag(true)
               .message("Successful")
               .result(Arrays.stream(BillerCategory.values())
                        .map(BillerCategory::getName).toList())
                .build();
    }

    @Override
    public BaseResponse<List<String>> getProductsByBiller(String biller) {
        return BaseResponse.<List<String>>builder()
                .flag(true)
                .code("00")
                .result(getProducts(biller))
                .build();
    }

    @Override
    public BaseResponse<?> makePayment(BillPaymentRequest request) {
        String reference = UUID.randomUUID().toString();
        LocalDateTime now = LocalDateTime.now();

        Transaction transaction = new Transaction();
        transaction.setReference(reference);
        transaction.setSessionId(UUID.randomUUID().toString());
        transaction.setProcessorStatus("SUCCESS");
        transaction.setProcessorCode("00");
        transaction.setProcessorMessage("Payment successful");
        transaction.setAmount(request.getAmount());
        transaction.setCharges(BigDecimal.ZERO);
        transaction.setBeneficiaryName(request.getBiller());
        transaction.setBeneficiaryAccountNo("N/A");
        transaction.setBeneficiaryBankCode("N/A");
        transaction.setBeneficiaryBankName("N/A");
        transaction.setSourceAccountNo(request.getSourceAccountNo());
        transaction.setSourceAccountName(request.getSourceAccountName());
        transaction.setSourceBankName("Fintech Wallet");
        transaction.setSourceBankCode("999");
        transaction.setDescription(request.getDescription());
        transaction.setNarration("Payment to " + request.getBiller() + " for " + request.getProduct());
        transaction.setType(TrxType.DEBIT);
        transaction.setCategory(resolveTrxCategory(request.getBiller()));
        transaction.setChannel(request.getChannel());
        transaction.setStatus(Status.SUCCESS);
        transaction.setTransactionTime(now);
        transaction.setCreatedAt(now);

        transactionRepository.save(transaction);

        return BaseResponse.builder()
                .code("00")
                .flag(true)
                .message("Payment successful")
                .result(reference)
                .build();
    }

    private TrxCategory resolveTrxCategory(String biller) {
        String upperBiller = biller.toUpperCase();

        if (List.of("IKEDC", "EKEDC", "AEDC", "KEDCO").contains(upperBiller)) return TrxCategory.ELECTRICITY;
        if (List.of("DSTV", "GOTV", "STARTIMES").contains(upperBiller)) return TrxCategory.CABLE_TV;
        if (List.of("SPECTRANET", "SMILE", "NTEL").contains(upperBiller)) return TrxCategory.INTERNET;
        if (List.of("WAEC", "NECO", "JAMB").contains(upperBiller)) return TrxCategory.EXAMS_AND_RESULTS;
        if (List.of("BET9JA", "SPORTYBET", "NAIRABET").contains(upperBiller)) return TrxCategory.BETTING;
        if (List.of("MTN", "AIRTEL", "GLO", "9MOBILE").contains(upperBiller)) return TrxCategory.AIRTIME;
        if (upperBiller.contains("DATA")) return TrxCategory.DATA;

        return TrxCategory.TRANSFER;
    }

    private List<String> getBillers(BillerCategory billerCategory) {
        return switch (billerCategory.getCode()) {
            case "ELECTRICITY" -> Arrays.asList("IKEDC", "EKEDC", "AEDC", "KEDCO");
            case "CABLE_TV" -> Arrays.asList("DSTV", "GOTV", "Startimes");
            case "INTERNET" -> Arrays.asList("Spectranet", "Smile", "NTEL");
            case "EXAMS_AND_RESULTS" -> Arrays.asList("WAEC", "NECO", "JAMB");
            case "BETTING" -> Arrays.asList("Bet9ja", "SportyBet", "NairaBet");
            case "AIRTIME" -> Arrays.asList("MTN", "Airtel", "Glo", "9Mobile");
            case "DATA" -> Arrays.asList("MTN Data", "Airtel Data", "Glo Data", "9Mobile Data");
            default -> List.of();
        };
    }

    private List<String> getProducts(String biller) {
        return switch (biller.toUpperCase()) {
            case "IKEDC" -> Arrays.asList("Prepaid Token", "Postpaid Bill");
            case "EKEDC" -> Arrays.asList("Prepaid Recharge", "Meter Recharge");
            case "AEDC" -> Arrays.asList("Prepaid", "Postpaid");
            case "KEDCO" -> Arrays.asList("Electricity Bill", "Smart Meter");

            case "DSTV" -> Arrays.asList("Compact", "Premium", "Yanga", "Padi");
            case "GOTV" -> Arrays.asList("Max", "Jolli", "Smallie");
            case "STARTIMES" -> Arrays.asList("Basic", "Smart", "Nova");

            case "SPECTRANET" -> Arrays.asList("Weekly Plan", "Monthly Plan", "Unlimited Night");
            case "SMILE" -> Arrays.asList("SmileVoice", "Data Bundle", "Unlimited Platinum");
            case "NTEL" -> Arrays.asList("Unlimited Monthly", "Mini Plan", "Mega Plan");

            case "WAEC" -> Arrays.asList("Scratch Card", "Result Check");
            case "NECO" -> Arrays.asList("Token", "Result Check");
            case "JAMB" -> Arrays.asList("ePIN", "Result Print");

            case "BET9JA" -> Arrays.asList("Fund Account", "Withdraw Funds");
            case "SPORTYBET" -> Arrays.asList("Recharge", "Payout");
            case "NAIRABET" -> Arrays.asList("Account Top-up", "Bonus Credit");

            case "MTN" -> Arrays.asList("100 Airtime", "500 Airtime", "1000 Airtime");
            case "AIRTEL" -> Arrays.asList("100 Airtime", "500 Airtime", "1000 Airtime");
            case "GLO" -> Arrays.asList("100 Airtime", "500 Airtime", "1000 Airtime");
            case "9MOBILE" -> Arrays.asList("100 Airtime", "500 Airtime", "1000 Airtime");

            case "MTN DATA" -> Arrays.asList("500MB", "1.5GB", "5GB", "10GB");
            case "AIRTEL DATA" -> Arrays.asList("1GB", "2GB", "4.5GB", "11GB");
            case "GLO DATA" -> Arrays.asList("2.9GB", "5.8GB", "10.8GB");
            case "9MOBILE DATA" -> Arrays.asList("1.5GB", "3GB", "7GB");

            default -> List.of();
        };
    }
}
