package com.fintech.billspayment.controller;


import com.fintech.billspayment.enums.BillerCategory;
import com.fintech.billspayment.request.BillPaymentRequest;
import com.fintech.billspayment.response.BaseResponse;
import com.fintech.billspayment.service.BillPaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bills")
@RequiredArgsConstructor
public class BillsPaymentController {

    private final BillPaymentService billerService;

    @GetMapping("/categories")
    public BaseResponse<List<String>> getAllCategories() {
        return billerService.getAllCategories();
    }

    @GetMapping("/category/{category}")
    public BaseResponse<List<String>> getBillersByCategory(@PathVariable String category) {
            BillerCategory billerCategory = BillerCategory.valueOf(category.toUpperCase());
            return billerService.getBillersByCategory(billerCategory);
    }

    @GetMapping("/{biller}/products")
    public BaseResponse<List<String>> getProductsByBiller(@PathVariable String biller) {
        return billerService.getProductsByBiller(biller);
    }

    @PostMapping("/make-payment")
    public BaseResponse<?> makePayment(@RequestBody BillPaymentRequest request) {
        return billerService.makePayment(request);
    }
}
