package com.financeapp.repository;

import com.financeapp.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> findByCategory(String category);

    // ✅ NEW FILTER
    List<Transaction> findByType(String type);
}