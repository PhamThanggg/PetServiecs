package com.project.petService.controllers;

import com.project.petService.dtos.requests.invoices.InvoiceRequest;
import com.project.petService.dtos.response.ApiResponse;
import com.project.petService.dtos.response.PageResponse;
import com.project.petService.dtos.response.invoices.InvoiceResponse;
import com.project.petService.services.invoices.InvoiceService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/invoice")
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@RequiredArgsConstructor
public class InvoiceController {
    InvoiceService invoiceService;

    @PostMapping("")
    public ApiResponse<InvoiceResponse> create(
            @RequestBody @Valid InvoiceRequest request
    ){
        return ApiResponse.<InvoiceResponse>builder()
                .message("Create successfully")
                .result(invoiceService.createInvoice(request))
                .build();
    }

    @GetMapping("")
    public ApiResponse<List<InvoiceResponse>> getAllInvoice(
    ){
        return ApiResponse.<List<InvoiceResponse>>builder()
                .result(invoiceService.getInvoiceALl())
                .build();
    }

    @GetMapping("/search")
    public PageResponse<List<InvoiceResponse>> searchAll(@RequestParam("page") int page,
                                                         @RequestParam(name = "payment", required = false) String payment,
                                                         @RequestParam(name = "status", required = false) String status,
                                                         @RequestParam("limit") int limit) {
        Page<InvoiceResponse> invoiceResponses = invoiceService.getSearchInvoice(page, limit, payment, status);
        return PageResponse.<List<InvoiceResponse>>builder()
                .currentPage(invoiceResponses.getNumber())
                .totalPages(invoiceResponses.getTotalPages())
                .totalElements(invoiceResponses.getTotalElements())
                .pageSize(invoiceResponses.getSize())
                .result(invoiceResponses.getContent())
                .build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<String> deleteInvoiceById(@PathVariable("id") Long id){
        invoiceService.deleteInvoice(id);
        return ApiResponse.<String>builder()
                .result("Invoice has been deleted")
                .build();
    }
}
