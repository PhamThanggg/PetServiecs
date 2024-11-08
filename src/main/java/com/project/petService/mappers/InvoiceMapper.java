package com.project.petService.mappers;


import com.project.petService.dtos.requests.invoices.InvoiceRequest;
import com.project.petService.dtos.requests.invoices.InvoiceUpdateRequest;
import com.project.petService.dtos.response.invoices.InvoiceResponse;
import com.project.petService.entities.Invoice;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface InvoiceMapper {
    Invoice toInvoice(InvoiceRequest request);

    InvoiceResponse toInvoiceResponse(Invoice invoice);

    void updateInvoice(@MappingTarget Invoice invoice, InvoiceUpdateRequest request);
}
