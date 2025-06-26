package com.fintech.billspayment.service;

import com.fintech.billspayment.enums.BillerCategory;
import com.fintech.billspayment.request.BillPaymentRequest;
import com.fintech.billspayment.response.BaseResponse;

import java.util.List;

public interface BillPaymentService {
    BaseResponse<List<String>> getBillersByCategory(BillerCategory category);
    BaseResponse<List<String>> getAllCategories();
    BaseResponse<List<String>> getProductsByBiller(String biller);
    BaseResponse<?> makePayment(BillPaymentRequest request);
}
