package com.fintech.billspayment.controller;


import com.fintech.billspayment.enums.BillerCategory;
import com.fintech.billspayment.request.BillPaymentRequest;
import com.fintech.billspayment.response.BaseResponse;
import com.fintech.billspayment.service.BillPaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bills")
@RequiredArgsConstructor
public class BillsPaymentController {

    private final BillPaymentService billerService;

    @Value("${app.secret-key}")
    private String validSecretKey;

    @GetMapping("/categories")
    public BaseResponse<List<String>> getAllCategories(@RequestHeader("X-API-KEY") String apiKey) {
        if (!validSecretKey.equals(apiKey)) {
            return BaseResponse.<List<String>>builder()
                    .code("03")
                    .flag(false)
                    .message("Unauthorized")
                    .build();
        }
        return billerService.getAllCategories();
    }

    @GetMapping("/category/{category}")
    public BaseResponse<List<String>> getBillersByCategory(@RequestHeader("X-API-KEY") String apiKey, @PathVariable String category) {
        if (!validSecretKey.equals(apiKey)) {
            return BaseResponse.<List<String>>builder()
                    .code("03")
                    .flag(false)
                    .message("Unauthorized")
                    .build();
        }
        BillerCategory billerCategory = BillerCategory.valueOf(category.toUpperCase());
        return billerService.getBillersByCategory(billerCategory);
    }

    @GetMapping("/{biller}/products")
    public BaseResponse<List<String>> getProductsByBiller(@RequestHeader("X-API-KEY") String apiKey, @PathVariable String biller) {
        if (!validSecretKey.equals(apiKey)) {
            return BaseResponse.<List<String>>builder()
                    .code("03")
                    .flag(false)
                    .message("Unauthorized")
                    .build();
        }
        return billerService.getProductsByBiller(biller);
    }

    @PostMapping("/make-payment")
    public BaseResponse<?> makePayment(@RequestHeader("X-API-KEY") String apiKey, @RequestBody BillPaymentRequest request) {
        if (!validSecretKey.equals(apiKey)) {
            return BaseResponse.builder()
                    .code("03")
                    .flag(false)
                    .message("Unauthorized")
                    .build();
        }

        return billerService.makePayment(request);
    }
}
