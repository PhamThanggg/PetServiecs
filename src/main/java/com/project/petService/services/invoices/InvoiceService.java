package com.project.petService.services.invoices;


import com.project.petService.dtos.requests.invoices.InvoiceRequest;
import com.project.petService.dtos.requests.invoices.InvoiceUpdateRequest;
import com.project.petService.dtos.response.invoices.InvoiceResponse;
import com.project.petService.entities.AttributeSize;
import com.project.petService.entities.Invoice;
import com.project.petService.entities.Order;
import com.project.petService.entities.OrderDetail;
import com.project.petService.exceptions.AppException;
import com.project.petService.exceptions.ErrorCode;
import com.project.petService.mappers.InvoiceMapper;
import com.project.petService.repositories.AttributeRepository;
import com.project.petService.repositories.AttributeSizeRepository;
import com.project.petService.repositories.InvoiceRepository;
import com.project.petService.repositories.OrderRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class InvoiceService implements IInvoiceService {
    InvoiceRepository invoiceRepository;
    OrderRepository orderRepository;
    InvoiceMapper invoiceMapper;
    AttributeSizeRepository attributeSizeRepository;
    @Override
    public InvoiceResponse createInvoice(InvoiceRequest request) {
        Order order = orderRepository.findById(request.getOrderId())
                .orElseThrow(() -> new RuntimeException("Order k tồn tại"));

        LocalDateTime localDateTime = LocalDateTime.now();
        Invoice invoice = invoiceMapper.toInvoice(request);
        invoice.setOrder(order);
        invoice.setCreatedDate(localDateTime);
        invoice.setStatus("CHUA_THANH_TOAN");
        return invoiceMapper.toInvoiceResponse(invoiceRepository.save(invoice));
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public List<InvoiceResponse> getInvoiceALl() {
        return invoiceRepository.findAll().stream().map(invoiceMapper::toInvoiceResponse).toList();
    }

    @Override
    @PreAuthorize("hasRole('ADMIN') or @securityService.isOrderOwner(#id, authentication)")
    public InvoiceResponse updateInvoice(Long orderId,LocalDateTime paymentTime,Double totalPrice, String status) {
        Invoice invoice = invoiceRepository.findByOrderId(orderId)
                .orElseThrow(() -> new AppException(ErrorCode.INVOICE_NOT_EXISTS));

        invoice.setPaymentDate(paymentTime);
        invoice.setPrice(totalPrice);
        invoice.setStatus(status);
        return invoiceMapper.toInvoiceResponse(invoiceRepository.save(invoice));
    }

    @PreAuthorize("hasRole('ADMIN') or @securityService.isOrderOwner(#id, authentication)")
    public void updateInvoiceFail(Long orderId) {
        Invoice invoice = invoiceRepository.findByOrderId(orderId)
                .orElseThrow(() -> new AppException(ErrorCode.INVOICE_NOT_EXISTS));

        invoice.setStatus("DA_HUY");
        invoiceRepository.save(invoice);

        List<OrderDetail> orderDetails = invoice.getOrder().getOrderDetails();
        Set<Long> attributeSizeIds = new HashSet<>();
        for (OrderDetail orderDetail : orderDetails){
            Long attributeSizeId = orderDetail.getAttributeSize().getId();
            attributeSizeIds.add(attributeSizeId);
        }
        Set<AttributeSize> attributeSizes = attributeSizeRepository.findByIdIn(attributeSizeIds);
        Map<Long, AttributeSize> attributeSizeMap = attributeSizes.stream()
                .collect(Collectors.toMap(AttributeSize::getId, attributeSize -> attributeSize));

        for (OrderDetail orderDetail : orderDetails) {
            AttributeSize attributeSize = attributeSizeMap.get(orderDetail.getAttributeSize().getId());
            if (attributeSize != null) {
                attributeSize.setQuantity(attributeSize.getQuantity() + orderDetail.getQuantity());
                attributeSize.setQuantitySold(attributeSize.getQuantitySold() - orderDetail.getQuantity());
            }
        }
        attributeSizeRepository.saveAll(attributeSizes);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteInvoice(Long id) {
        invoiceRepository.deleteById(id);
    }
}
