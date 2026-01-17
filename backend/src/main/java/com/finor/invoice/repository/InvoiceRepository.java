package com.finor.invoice.repository;

import com.finor.invoice.entity.Invoice;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
    long countByStatus(String status);
    

    @Query("SELECT i.category, SUM(i.totalAmount), COUNT(i) FROM Invoice i WHERE i.category IS NOT NULL GROUP BY i.category")
    List<Object[]> categorySummary();

    @Query("SELECT i.vendorName, SUM(i.totalAmount), COUNT(i) FROM Invoice i WHERE i.vendorName IS NOT NULL GROUP BY i.vendorName")
    List<Object[]> vendorSummary();

    @Query("SELECT FUNCTION('DATE_FORMAT', i.createdAt, '%Y-%m'), SUM(i.totalAmount), COUNT(i) FROM Invoice i GROUP BY FUNCTION('DATE_FORMAT', i.createdAt, '%Y-%m')")
    List<Object[]> monthlySummary();
}
