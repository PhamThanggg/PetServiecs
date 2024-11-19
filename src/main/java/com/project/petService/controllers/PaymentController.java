package com.project.petService.controllers;

import com.project.petService.dtos.requests.vnpay.VNPayRequest;
import com.project.petService.dtos.response.ApiResponse;
import com.project.petService.dtos.response.invoices.InvoiceResponse;
import com.project.petService.services.invoices.InvoiceService;
import com.project.petService.services.util.VNPayService;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("${api.prefix}/payment")
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@RequiredArgsConstructor
public class PaymentController {
    VNPayService vnPayService;
    InvoiceService invoiceService;
    @PostMapping("/create")
    public ResponseEntity<?> createPayment(@RequestBody VNPayRequest request
    ) {
        var paymentUrl = vnPayService.createPayment(
                request.getOrderId(), request.getAmount(),
                request.getOrderInfo(), request.getReturnUrl()
        );
        return ResponseEntity.ok(paymentUrl);
    }

    @PostMapping("vnpay-callback")
    @Transactional
    public ApiResponse<String> paymentCompleted(@RequestBody Map<String, String> requestData){
        int paymentStatus = vnPayService.orderReturn(requestData);

        Long orderId = Long.parseLong(requestData.get("vnp_TxnRef"));
        LocalDateTime paymentTime = LocalDateTime.now();
        String transactionId = requestData.get("vnp_TransactionNo");
        Double totalPrice = Double.parseDouble(requestData.get("vnp_Amount")) / 100;
        String status = paymentStatus == 1 ? "1" : "0";
        if(paymentStatus == 1){
           invoiceService.updateInvoice(orderId, paymentTime, totalPrice, "DA_THANH_TOAN");
        }else{
            invoiceService.updateInvoiceFail(orderId);
        }

        return ApiResponse.<String>builder()
                .result(status)
                .build();
    }

}
