package com.project.petService.services.invoices;

import com.project.petService.dtos.requests.invoices.InvoiceRequest;
import com.project.petService.dtos.requests.invoices.InvoiceUpdateRequest;
import com.project.petService.dtos.response.invoices.InvoiceResponse;

import java.time.LocalDateTime;
import java.util.List;

public interface IInvoiceService {
    InvoiceResponse createInvoice(InvoiceRequest request);

    List<InvoiceResponse> getInvoiceALl();

    InvoiceResponse updateInvoice(Long orderId, LocalDateTime paymentTime, Double totalPrice, String status);

    void deleteInvoice(Long id);
}
