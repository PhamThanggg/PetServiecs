package com.project.petService.repositories;

import com.project.petService.entities.Invoice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
    Optional<Invoice> findByOrderId(Long orderId);

    @Query("SELECT p FROM Invoice p WHERE " +
            "(:status IS NULL OR p.status LIKE %:status%) AND" +
            "(:payment IS NULL OR p.paymentMethod LIKE %:payment%)")
    Page<Invoice> findByPaymentAndSTT(String payment, String status, Pageable pageable);
}
